package edu.uni.resources;

public interface IResource {

    boolean processNext();

    boolean isFinished();

    boolean isMatched(int value);

    int getCounter();
    void setCounter(int counter);

    String[] getValues();
    void setValues(String[] values);


    ReadManager getManager();
    void setManager(ReadManager manager);


}
