package edu.uni.resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


// держит коннекш с файлом выдавая каждый раз по одной строке из файла
// при окончании файла - акрывает ресурсы
// если не может открыть файл по имени - выбрасываем исключение
public class ReadManager {

    private final String fileName;
    private BufferedReader br;


    public ReadManager(String filename) throws FileNotFoundException {
        this.fileName = filename;
        this.br = new BufferedReader(new FileReader(fileName));
    }


    public String readLine()  {

        String current;

        try {
            if ((current = br.readLine()) == null)
                br.close();

            return current;

        } catch (IOException e) {
            try {
                br.close();
            } catch (IOException e1) {
                e.printStackTrace();
                System.out.println("Couldn't close a file reader. ");
            }
            // останавливаем работу
            System.out.println("Can't read a file: " + fileName);
        }

        return null;

    }


}
