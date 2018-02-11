package edu.uni;

import edu.uni.handler.ResourceHandler;
import edu.uni.loader.MyLoader;
import edu.uni.resources.FileResourceA;

import edu.uni.resources.IResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {


    private static String[] fileNames = {"files/1.csv", "files/2.csv", "files/4.csv","files/3.csv"};
    // у ресурхандлера есть поле ресурс, который имплементит IResource
    private static List<ResourceHandler> pool = new ArrayList<>(fileNames.length);

    private static Class<?> newFileResource;


    public static void main(String[] args) {

        // создаем трэды с ресурсхэндлерами и добавляем в пулл
        Arrays.stream(fileNames)
                .map(FileResourceA::new)
                .map(ResourceHandler::new)
                .forEach(pool::add);

        // заускаем треды
        pool.stream()
                .map(Thread::new)
                .forEach(Thread::start);

        // новый трэд за мониторингом условия смены логики суммирования
        new Thread(Main::monitor).start();




    }

    private static void monitor() {
        while (true) {
            // при добавлении суммы синхронизириемся также по Sum.class
            // (см.  классы имплементирующие IResource)
            synchronized (Sum.class) {
                // здесь условие смены Iresource в классе ResourceHandler
                if (Sum.value != 0 && Sum.value % 2 == 0) {
                    if (!change())
                        System.out.println("Couldn't change classes in runtime");
                    else
                        System.out.println("*************** смена логики суммирования ***************");
                    break;
                }
            }
        }
    }


    private static boolean change() {
        newFileResource= null;
        try {
            newFileResource = new MyLoader().loadClass("edu.uni.resources.FileResourceB");
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found...");
            return false;
        }

        List<IResource> list = pool.stream()
                .map(s -> initLoadedResource())
                .collect(Collectors.toList());

        for (int i = 0; i<pool.size(); i++) {
            IResource tmp = list.get(i);
            exchangeFields(pool.get(i).getRes(), tmp);
            pool.get(i).setRes(tmp);
        }


        return true;

    }

    private static IResource initLoadedResource() {
        IResource newRes;
        try {
            newRes = (IResource) newFileResource.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("Couldn't initialize a class");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Это я хз вообще как обрабатывать");
            return null;
        }
        return newRes;
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