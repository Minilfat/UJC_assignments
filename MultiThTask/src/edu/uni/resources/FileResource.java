package edu.uni.resources;


import edu.uni.Common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileResource implements IResource{


    private int counter = 0;
    protected String[] values;
    public boolean finished = false;



    protected boolean add() {

        int value;
        try {
            value = Integer.parseInt(values[counter++]);
            if (Common.isMatched(value)) {
                synchronized (Common.class) {
                    Common.sum += value;
                    System.out.println(Common.sum);
                }
            }
            return true;

        } catch (NumberFormatException e) {
            System.out.println("Unexpected value in resource. Stopping...");
            finished = true;
            return false;
        }

    }


    private final String fileName;
    private Manager manager;

    public FileResource(String fileName) {
        this.fileName = fileName;
        this.manager = new Manager();

        try {
            manager.open();
            manager.readAndSplit();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            finished = true;
        }
    }



    @Override
    public boolean processNext() {

        if (counter >= values.length)
            // достаем новую строку для обработки
            manager.readAndSplit();


        if (manager.eof)
            return finished = true;

        return add();
    }




    private class Manager {

        private BufferedReader br;
        private boolean eof = false;

        private void open() throws FileNotFoundException {
            br = new BufferedReader(new FileReader(fileName));
        }


        private void readAndSplit()  {

            String current;

            try {
                if ((current = br.readLine()) == null) {

                    eof = true;
                    br.close();

                } else {

                    values = current.trim().split("\\s+");
                    counter = 0;

                }
            } catch (IOException e) {
                // останавливаем работу
                finished = true;
                System.out.println("Can't read a file: " + fileName);
            }

        }


    }
}
