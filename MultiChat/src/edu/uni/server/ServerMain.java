package edu.uni.server;

import edu.uni.client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ServerMain {

    private static List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private static final int PORT = 9099;

    public ServerMain() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server socket IP: " + serverSocket.getInetAddress().getHostAddress());
    }


    private Socket listen() throws IOException {
        Socket s = serverSocket.accept();
        System.out.println("A new client connected: " + s.getInetAddress().getHostAddress());
        return s;
    }

    private void run() {

        while (true) {
            Socket connection;
            try {
                // ждем нового подключения
                connection = listen();
            } catch (IOException e) {
                System.err.println("Accept for a socket failed");
                System.out.println("Waiting for other incoming connection");
                continue;
            }
            if (connection != null) {
                // запускаем обработчик клиента на сервере
                final ClientHandler clientHandler;
                try {
                    clientHandler = new ClientHandler(connection);
                    final Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                    clients.add(clientHandler);
                } catch (IOException e) {
                    System.out.println("Couldn't get writer/reader for socket " + connection.getInetAddress().getHostAddress());
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
