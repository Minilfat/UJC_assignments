package edu.minilfat.captcha.model;

import java.awt.image.BufferedImage;

public class Captcha {

    private long id;
    private String word;
    private BufferedImage image;

    public Captcha(long id, String word) {
        this.id = id;
        this.word = word;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
