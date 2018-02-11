package edu.uni.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final Socket socket;
    private final BufferedWriter socketWriter;
    private final BufferedReader socketReader;
    private final Scanner userInput;
    private final String name;
    private boolean banned = false;




    public Client(String name) throws IOException {
        socket = new Socket("0.0.0.0", 9099);
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        socketWriter.write("name: " + name);
        socketWriter.write('\n');
        socketWriter.flush();

        userInput = new Scanner(System.in);
        this.name = name;
        new Thread(new Receiver()).start();
    }


    public void run() {
        String in;
        while (!socket.isClosed()) {

            in = userInput.nextLine();
            try {

                if (in != null && in.startsWith("ban")) {
                    ban(in);
                    continue;
                }


                socketWriter.write("[" + name + "]: " + in);
                socketWriter.write('\n');
                socketWriter.flush();

                if ("::exit".equals(in) || in == null) {
                    // служебное слово уже отправили на сервер (строка 94
                    // - мои предположения почему так нужно делать)
                    // поток чтения с сокета завершаем break'ом
                    // на выходе из метода закрываем ресурсы
                    break;
                }


            } catch (IOException e) {

                System.out.println("Couldn't send a message");
                break;
            }
        }
        // здесь происходит закрытие
        close();
    }

    private void ban(String message) throws IOException {
        if (message.trim().split("\\s++").length <= 1) {
            System.out.println("Ban usage: ban <ip-address>");
        } else {
            socketWriter.write(message);
            socketWriter.write('\n');
            socketWriter.flush();
        }
    }


    private synchronized void close() {
        if (!socket.isClosed()) {
            try {
                userInput.close();
                socketReader.close();
                socketWriter.close();
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Couldn't close connection");
                System.exit(-1);
            }
        }
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing line argument...");
            System.out.println("Usage: java -cp edu/uni/client edu/uni/client/Client " + "[Your name]");
            System.exit(0);
        }
        try {
            new Client(args[0]).run();
        } catch (IOException e) {
            System.out.println("Unable to connect. Server not running?");
            System.exit(-1);
        }
    }

    private class Receiver implements Runnable {

        @Override
        public void run() {
            String line;

            while (true) {
                try {

                    line = socketReader.readLine();

                    // значит, что сервер закрыл сокет со своей стороны
                    if (line == null)
                        break;

                    if ("You were banned".equals(line))
                        banned = true;

                    System.out.println(line);

                } catch (IOException e) {
                    System.out.println("Connection lost");
                    break;
                }
            }

            if (banned)
                System.out.println("type '::exit' to exit");
            else
                System.out.println("Leaving a chat");
        }
    }
}
