package edu.uni.resources;


public abstract class Resource {

    protected int counter = 0;

    public boolean finished = false;



    // всегда возвращает true
    // если ошибка формата входных данных - возвращает false
    public abstract boolean processNext();

}
