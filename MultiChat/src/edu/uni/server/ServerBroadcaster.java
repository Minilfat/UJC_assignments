package edu.uni.server;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerBroadcaster {


    private static BlockingQueue<ClientHandler> clients = new LinkedBlockingQueue<>();


    // active client -  тот, от кого пришло сообщение
    public static void sendAll(ClientHandler activeClient, String message) {
        for (ClientHandler client : clients)
            if (!client.equals(activeClient))
                client.send(message);
    }


    public static  void addClient(ClientHandler client) {
        clients.add(client);
    }

    public static void remove(ClientHandler active) {
        clients.remove(active);
    }

    public static BlockingQueue<ClientHandler> getClients() {
        return clients;
    }


    // здесь костыль для тестинга на одной рабочей машине
    // так как ремоут адреса у всех одинаковые - то себя банить как-то не очень
    // вторым аргументом передаем объектом, от которого получили команду на бан
    // и его исключаем из поиска
    public static ClientHandler getClientByIp(String ip, ClientHandler activeClient) {
        for (ClientHandler client : clients) {
            if (!client.equals(activeClient) &&
                    client.getConnection().getInetAddress().getHostAddress().equals(ip))
                return client;
        }

        return null;
    }
}