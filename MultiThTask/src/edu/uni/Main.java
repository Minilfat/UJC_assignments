package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.loader.MyLoader;
import edu.uni.resources.FileResourceA;

import edu.uni.resources.IResource;


public class Main {


    private static String[] fileNames = {"files/1.csv", "files/2.csv", "files/4.csv","files/3.csv"};
    // у ресурхандлера есть поле ресурс, который имплементит IResource
    private static ResourceHandler[] pool = new ResourceHandler[fileNames.length];


    // мы создаем и запускаем потоки
    // которые с файловым ресурсом А
    // в рантайме хотим поменять его на файловым ресурс FileResourceB
    private static void go() {
        for (int i=0; i<pool.length; i++) {
            pool[i] = new ResourceHandler(new FileResourceA(fileNames[i]));
            new Thread(pool[i]).start();
        }
    }


    public static void main(String[] args) {



        // старутем трэды по подсчету суммы
        go();

        // новый трэд за мониторингом условия смены логики суммирования
        new Thread(() -> {
            while (true) {
                // при добавлении суммы синхронизириемся также по Sum.class
                // (см.  классы имплементирующие IResource)
                synchronized (Sum.class) {
                    // здесь условие смены Iresource в классе ResourceHandler
                    if (Sum.value !=0 && Sum.value % 2 == 0) {
                        if (!change())
                            System.out.println("Couldn't change classes in runtime");
                        break;
                    }
                }
            }
        }).start();




    }



    private static boolean change() {
        Class<?> clazz = null;
        try {
            clazz = new MyLoader().loadClass("edu.uni.resources.FileResourceB");
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found...");
            return false;
        }

        for (int i=0; i<pool.length; i++) {
            IResource newRes = null;
            try {
                newRes = (IResource) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.out.println("Couldn't initialize a class");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Это я хз вообще как обрабатывать");
            }

            if (newRes == null)
                return false;


            exchangeFields(pool[i].getRes(), newRes);
            pool[i].setRes(newRes);

        }
        return true;

    }



    // Resource содержит много полей
    // мы берем эти поля у старых ресурсов - вставляем в новые
    // но логика суммирования у нового ресурса - другая

    private static void exchangeFields(IResource oldRes, IResource newRes) {
        newRes.setCounter(oldRes.getCounter());
        newRes.setValues(oldRes.getValues());
        newRes.setManager(oldRes.getManager());
    }

}