package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.resources.FileResource;


public class Main {


    private static  String[] strings = {"2  12 12 14 200 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
            "3 100", "-2  1000", "400 600 45 434 23 32 32 32 23 1   4",
            "2 1 10000 23 44    54", "1100000000"};

    private static String[] fileNames = {"files/1.csv", "files/2.csv", "files/3.csv", "files/4.csv"};


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