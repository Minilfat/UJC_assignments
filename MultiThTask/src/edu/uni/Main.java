package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.loader.MyLoader;
import edu.uni.resources.FileResourceA;

import edu.uni.resources.IResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class Main {


    private static String[] fileNames = {"filess/1.csv", "filess/2.csv", "files/3.csv", "files/4.csv"};

    //edu.uni.resources.FileResourceB

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        MyLoader a = new MyLoader();

        try {
             IResource b = (IResource) a.loadClass("edu.uni.resources.FileResourceB").
                     getConstructor(String.class).
                     newInstance(fileNames[2]);
            System.out.println(b.isFinished());



            //System.out.println(b.isFinished());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //System.out.println(FileResourceA.class.getCanonicalName());
    }

    private static void go(int n) {


        Thread[] threadPool = new Thread[n];

        for (int i=0; i<n; i++) {
            threadPool[i] = new Thread(new ResourceHandler(new FileResourceA(fileNames[i])));
            threadPool[i].start();
        }


    }

}