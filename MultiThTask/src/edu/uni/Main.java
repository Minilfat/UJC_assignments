package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.resources.FileResource;


public class Main {


    private static String[] fileNames = {"filess/1.csv", "filess/2.csv", "files/3.csv", "files/4.csv"};


    public static void main(String[] args) {
        go(fileNames.length);
    }

    private static void go(int n) {


        Thread[] threadPool = new Thread[n];

        for (int i=0; i<n; i++) {
            threadPool[i] = new Thread(new ResourceHandler(new FileResource(fileNames[i])));
            threadPool[i].start();
        }


    }

}