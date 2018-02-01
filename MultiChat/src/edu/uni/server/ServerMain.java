package edu.uni.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {


    private ServerSocket serverSocket;
    private static final int PORT = 9099;
    private static ServerBroadcaster broadcaster;


    private ServerMain() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server socket IP: " + serverSocket.getInetAddress().getHostAddress());
        broadcaster = new ServerBroadcaster(this);
        // заупск потока рассылки сообщений
        new Thread(broadcaster).start();
    }

    private Socket listen() {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (IOException e) {
            shutDownServer();
        }
        return s;
    }

    private void run() {

        Socket connection;

        while (true) {

            // ждем нового подключения
            connection = listen();
            if (connection == null) break;
            System.out.println("New client connected: " + connection.getInetAddress().getHostAddress());


            // запускаем обработчик клиента на сервере
            final ClientHandler clientHandler;
            try {
                clientHandler = new ClientHandler(connection);
                final Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                broadcaster.addClient(clientHandler);
            } catch (IOException e) {
                System.out.println("Couldn't get writer/reader for socket " + connection.getInetAddress().getHostAddress());
                break;
            }

        }

        shutDownServer();

    }





    synchronized void shutDownServer() {

        // обрабатываем список рабочих коннектов, закрываем каждый
        for (ClientHandler s : broadcaster.clients) {
            s.close();
        }
        if (!serverSocket.isClosed()) {
            try {
                // устанавливаем флаг на закрытие
                serverSocket.close();
                //System.exit(0);
            } catch (IOException ignored) {
                System.out.println("Couldn't close server socket");
                System.exit(-1);
            }
        }
    }


    class ClientHandler implements Runnable {


        private final Socket connection;
        private BufferedReader socketReader;
        private BufferedWriter socketWriter;


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

                // если null или пришло служебное слово ::exit
                if (message == null) break;
                if (message.split("]: ")[1].startsWith("::exit")) {

                    String str = "Client \"" +
                            message.split("]: ")[0].replace("[", "") +
                            "\" has left a chat";
                    System.out.println(str);
                    broadcaster.sendAllClients(this, str);
                    // завершаем трэд
                    break;

                }

                System.out.println(message);
                broadcaster.sendAllClients(this, message);

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


        private synchronized void close() {
            broadcaster.clients.remove(this);
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

    }




    public static void main(String[] args) {
        try {
            new ServerMain().run();
        } catch (IOException e) {
            System.out.println("Could not listen on port" + PORT);
            System.exit(-1);
        }
    }
}


