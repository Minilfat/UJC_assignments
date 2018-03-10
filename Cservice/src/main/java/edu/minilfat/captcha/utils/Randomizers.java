package edu.minilfat.captcha.utils;

import java.awt.*;
import java.util.Random;

public class Randomizers {

    private static final String set  = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random rand = new Random();


    public static char[] getWord() {

        // длина слова от 5 до
        int length = 5 + Math.abs(rand.nextInt() % 3);
        char[] res = new char[length];
        for (int i=0; i<length; i++) {
            res[i] = set.charAt(rand.nextInt(set.length()));
        }

        return res;
    }

    public static Color getColor() {

        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        return new Color(r,g,b);
    }

}
