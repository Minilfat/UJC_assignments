package edu.uni.server;


import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {


    private final Socket connection;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String name;


    public ClientHandler(Socket socket) throws IOException {
        connection = socket;
        socketReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
    }


    @Override
    public void run() {
        while (true) {
            String message;
            try {
                message = socketReader.readLine();
            } catch (IOException e) {
                // не можем прочитать сообщение - разрываем соединение
                // клиенту отправляется null
                // и отваливаем его из сервера
                close();
                break;
            }

            // первое сообщение из сокета для чтения и записи имени
            // это сообщение имеет свой формат
            if (message != null && message.startsWith("name")) {
                this.name = message.split(":")[1];
                System.out.println("Name: " + this.name);
                continue;
            }

            // служебное сообщение бана
            // также имеет свой формат
            if (message != null && message.startsWith("ban")) {
                // get ip address from
                String ip = message.split(" ")[1];
                ClientHandler victim = ServerBroadcaster.getClientByIp(ip, this);

                if (victim == null) {
                    System.out.println(message);
                    send("No client with given ip: " + ip);
                } else {
                    victim.send("You were banned");
                    ServerBroadcaster.sendAll(victim, "Client " + victim.name + " was banned");
                    victim.close();
                }
                continue;
            }



            // клиент сам отключился
            // или служебное сообщение с ключевым словом ::exit
            if (message == null || message.split(": ")[1].startsWith("::exit")) {

                String tmp = "A client '" + name + "' has left a chat";
                System.out.println(tmp);
                ServerBroadcaster.sendAll(this, tmp);
                // завершаем трэд
                break;

            }


            // любое другое сообщение
            System.out.println(message);
            ServerBroadcaster.sendAll(this, message);

        }
        // закрываем ресурсы
        close();
    }

    public void send(String message) {
        try {
            socketWriter.write(message);
            socketWriter.write('\n');
            socketWriter.flush();
        } catch (IOException e) {
            System.out.println("Could't send to client: " + connection.getInetAddress().getHostAddress());
        }
    }


    public void close() {
       ServerBroadcaster.remove(this);
        if (!connection.isClosed()) {
            try {
                socketWriter.close();
                socketReader.close();
                connection.close(); // закрываем
            } catch (IOException e) {
                System.out.println("Couldn't close connection");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public Socket getConnection() {
        return connection;
    }
}