package edu.uni.resources;


import edu.uni.Common;

public abstract class Resource {

    protected int counter = 0;
    protected String[] values;
    public boolean finished = false;



    // всегда возвращает true
    // если ошибка формата входных данных - возвращает false
    public abstract boolean processNext();


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

}
