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


    private volatile boolean simulateKeyboard = false;




    public Client(String name) throws IOException {
        socket = new Socket("0.0.0.0", 9099);
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userInput = new Scanner(System.in);
        this.name = name;
        new Thread(new Receiver()).start();
    }


    public void run() {
        String in;
        while (!socket.isClosed()) {


            /// здесь можем повиснуть
            // т.е. если на сервере ввели ::quit
            //  он отрубит все сокеты - поток нашего ресивера сообщений
            // завершится по условию line==null, закроет ресурсы
            // а здесь будет необходимо ждать

            // костыляем с помощью просьбы о нажатии enter
            // после этого окажемся в блоке catch -
            // т.к. сокеты уже закрыты
            in = userInput.nextLine();
            try {

                socketWriter.write("[" + name + "]: " + in);
                socketWriter.write('\n');
                socketWriter.flush();

                if ("::exit".equals(in)) {
                    // служебное слово уже отправили на сервер (строка 94
                    // - мои предположения почему так нужно делать)
                    // поток чтения с сокета завершаем break'ом
                    // на выходе из метода закрываем ресурсы
                    break;
                }


            } catch (IOException e) {

                if (!simulateKeyboard) {
                    System.out.println("Couldn't send a message");
                    System.exit(-1);
                } else {
                    System.exit(0);
                }
            }
        }
        // здесь происходит закрытие
        close();
    }


    private synchronized void close() {
        if (!socket.isClosed()) {
            try {
                userInput.close();
                socketReader.close();
                socketWriter.close();
                socket.close();
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
                    // при завершениии потока ввода (строка 55)
                    // данный поток уже ждет сообщение с сервера здесь.
                    // для того, чтобы сюда пришел null,
                    // надо на сервере закрыть сокет данного клиента
                    // именно поэтому служебное слово ::exit отправляется на сервер
                    // и там закрывается сокет на серверной стороне - сюда приходит null
                    // все ресурсы закрыты - проблем быть не должно
                    line = socketReader.readLine();

                    // значит, что сервер закрыл сокет со своей стороны
                    if (line == null)
                        break;
                    if (line.equals("make fail keyboard input")) {
                        simulateKeyboard = true;
                        continue;
                    }
                    System.out.println(line);

                } catch (IOException e) {
                    System.out.println("Connection lost");
                    close();
                }
            }
            // закрываем со своей стороны

            if (simulateKeyboard)
                System.out.print("Leave a chat typing \"Enter\" key!");
            else
                System.out.println("Leaving a chat");

            // метод синхронизирован, так что даже если поток писателя
            // уже закрыл сокет (а скорее всего уже закрыл, в данном методе предусмотрена проверка
            // на закрытый сокет), исключения никакого не вылетит. I hope

            // здесь закрытие необходимо оставить
            // например, если сервер отключается (::exit в консоли сервера подходит под этот случай)
            // поток этого ресивера завершается - все ресурсы нормально закрываем
            // но повисаем при ожидании пользовательского ввода

            close();
        }
    }
}
