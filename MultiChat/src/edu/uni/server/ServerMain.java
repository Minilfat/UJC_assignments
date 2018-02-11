package edu.uni.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {


    private ServerSocket serverSocket;
    private static final int PORT = 9099;


    private ServerMain() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server socket IP: " + serverSocket.getInetAddress().getHostAddress());

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
                ServerBroadcaster.addClient(clientHandler);
            } catch (IOException e) {
                System.out.println("Couldn't get writer/reader for socket " + connection.getInetAddress().getHostAddress());
                break;
            }

        }

        shutDownServer();

    }





    private synchronized void shutDownServer() {

        // обрабатываем список рабочих коннектов, закрываем каждый
        for (ClientHandler s : ServerBroadcaster.getClients()) {
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






    public static void main(String[] args) {
        try {
            new ServerMain().run();
        } catch (IOException e) {
            System.out.println("Could not listen on port" + PORT);
            System.exit(-1);
        }
    }
}


