package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.resources.StringResource;



public class Main {


    private static  String[] strRes = {"2  12 12 14 200 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
            "3 100", "-2  1000", "400 600 45 434 23 32 32 32 23 1   4",
            "2 1 10000 23 44    54 ", "1100000000"};




    public static void main(String[] args) {
        go(strRes.length);
    }

    private static void go(int n) {


        Thread[] threadPool = new Thread[n];

        for (int i=0; i<n; i++) {
            threadPool[i] = new Thread(new ResourceHandler(new StringResource(strRes[i])));
            threadPool[i].start();
        }


    }

}