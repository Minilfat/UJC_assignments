package edu.uni.server;


import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerBroadcaster implements Runnable {

    private Scanner serverIn;
    BlockingQueue<ServerMain.ClientHandler> clients;

    private ServerMain ss;

    ServerBroadcaster(ServerMain ss) {
        this.ss = ss;
        serverIn = new Scanner(System.in);
        this.clients = new LinkedBlockingQueue<>();
    }
    @Override
    public void run() {
        String mes;
        while (true) {
            mes = serverIn.nextLine();
            if ("::exit".equals(mes)) {

                // искусственно отправляем null
                // receiver'ы у клиентов закроют сокеты со своей стороны
                // и потоки receiver'ов завершатся
                sendAllClients(null, "Server shutting down");
                sendAllClients(null, "make fail keyboard input");
                //sendAllClients(null, null);
                // заканчиваем поток рассылки клиентам
                break;
            }

            sendAllClients(null, "[Server]: " + mes);

        }

        // закрываем ресурсы со стороны сервера
        serverIn.close();
        ss.shutDownServer();

        //sendAllClients(null, "Shit");
    }

    // active client -  тот, от кого пришло сообщение
    public synchronized void sendAllClients(ServerMain.ClientHandler activeClient, String message) {
        for (ServerMain.ClientHandler client : clients)
            if (!client.equals(activeClient))
                client.send(message);
    }


    public void addClient(ServerMain.ClientHandler client) {
        this.clients.add(client);
    }

}