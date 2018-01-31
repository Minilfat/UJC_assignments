package edu.uni.server;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements Runnable {


    private final Socket connection;
    private BufferedReader socketReader; // буферизировнный читатель сокета
    private BufferedWriter socketWriter; // буферизированный писатель в сокет


    public ClientHandler(Socket socket) throws IOException {
        connection = socket;
        socketReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
    }


    public void run() {
        while (!connection.isClosed()) {
            String message;
            try {
                message = socketReader.readLine();

//                if ("::exit".equals(line)) {
//                    socketWriter.write("Closing connection.");
//                    connection.close();
//                }
                writeToConsole(message);
                //broadcast(line);

            } catch (IOException e) {
                System.out.println("Shiiit");
            }

        }
    }

//
//    public void sendMessage(String message) {
//        try {
//            socketWriter.write(message);
//            socketWriter.flush();
//        } catch (IOException e) {
//            System.out.println("Could't send to client: " + connection.getInetAddress().getHostAddress());
//        }
//    }

    private synchronized void writeToConsole(String message) {
        System.out.println(message);
    }
}


//    private void broadcast(String message) {
//        for (ClientHandler client : ServerMain.clients) {
//            //if (!client.equals(this))
//            client.sendMessage(message);
//        }
//    }
//
//    public BufferedReader getBr() {
//        return socketReader;
//    }
//
//    public BufferedWriter getBw() {
//        return socketWriter;
//    }
