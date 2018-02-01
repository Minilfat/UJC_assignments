package edu.uni.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ServerBroadcaster implements Runnable{

    private final Scanner serverIn;
    private final List<ClientHandler> clients;

    public ServerBroadcaster(List<ClientHandler> clients) {
        this.serverIn = new Scanner(System.in);
        this.clients = clients;
    }

    @Override
    public void run() {

        String mes;
        while (true) {
            mes = serverIn.nextLine();

            if ("quit".equals(mes))
                System.exit(0);

            sendAllClients(mes);

        }
    }

    // active client -  тот, от кого пришло сообщение
    private synchronized void sendAllClients(String message) {
        for (ClientHandler client: clients)
            client.sendMessage(message);
    }

}
