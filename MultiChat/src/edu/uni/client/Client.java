package edu.uni.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final Socket socket;
    private final BufferedReader socketReader;
    private final BufferedWriter socketWriter;
    private final Scanner userInput;



    public Client() throws IOException {
        socket = new Socket("0.0.0.0", 9099);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        userInput = new Scanner(System.in);
        new Thread(new AsyncReceiver()).start();
    }


    public void run() {
        String in;
        while (true) {
            in = userInput.nextLine();
            if ("::exit".equals(in))
                System.exit(0);

            try {
                socketWriter.write(in);
                socketWriter.write('\n');
                socketWriter.flush();
            } catch (IOException e) {
                break;
            }
        }
    }


    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            System.out.println("Unable to connect. Server not running?");
            System.exit(0);
        }
    }


    private class AsyncReceiver implements Runnable {


        @Override
        public void run() {
            String line;

            try {
                line = socketReader.readLine();

                if (line == null) {
                    System.out.println("Server shut down. Shutting down client");
                    System.exit(0);
                }

                System.out.println(line);

            } catch (IOException e) {
                System.out.println("Connection lost");
            }
        }


    }
}
