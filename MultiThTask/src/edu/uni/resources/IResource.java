package edu.uni.resources;

public interface IResource {

    boolean processNext();

    boolean isFinished();

    boolean isMatched(int value);
}
