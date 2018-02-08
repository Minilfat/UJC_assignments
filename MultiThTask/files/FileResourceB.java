package edu.uni.resources;


import edu.uni.Sum;

import java.io.FileNotFoundException;


public class FileResourceB implements IResource{


    private int counter;
    private String[] values;
    private boolean finished;
    private  ReadManager manager;



    public FileResourceB(String fileName) {
        this.finished = false;
        try {
            this.manager = new ReadManager(fileName);
            this.values = manager.readLine().trim().split("\\s+");
            this.counter = 0;

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't process a file: " + fileName + ". File not found");
            this.finished = true;
        }
    }



    @Override
    public boolean processNext() {

        // закончили обработку всех цифр из строки
        // достаем новую и продолжаем
        // если null, то завершаем обработку
        if (counter >= values.length) {
            // достаем новую строку для обработки
            String tmp = manager.readLine();
            if (tmp == null)
                return this.finished = true;

            // сплитим и запихиваем в values новые значения

            this.values =tmp.trim().split("\\s+");
            this.counter = 0;

        }

        // если все ок - также добавляем в общую сумму
        return add();
    }



    //  в случае добавления значения в общую сумму - возвращает true
    // если ошибка формата входных данных - возвращаем false
    private boolean add() {

        int value;
        try {
            value = Integer.parseInt(values[counter++]);
            if (isMatched(value)) {
                synchronized (Sum.class) {
                    Sum.value += value;
                    System.out.println(Sum.value);
                }
            }
            return true;

        } catch (NumberFormatException e) {
            System.out.println("Unexpected value in resource. Stopping...");
            finished = true;
            return false;
        }

    }


    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isMatched(int value) {
        return value < 0;
    }


}
