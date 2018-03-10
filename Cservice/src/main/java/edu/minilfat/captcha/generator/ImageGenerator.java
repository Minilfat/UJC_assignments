package edu.minilfat.captcha.generator;

import edu.minilfat.captcha.utils.Randomizers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageGenerator {

    private static Random rand = new Random();

    public static BufferedImage get(char[] captcha) {


        BufferedImage image = new BufferedImage(ImageProps.WIDTH, ImageProps.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("Georgia", Font.BOLD, ImageProps.FONT_SIZE);
        g2d.setFont(font);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0,
                                             Randomizers.getColor(),
                                             0, ImageProps.HEIGHT/3,
                                             Color.black, true);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, ImageProps.WIDTH, ImageProps.HEIGHT);


        g2d.setColor(Randomizers.getColor());

        int textLength = captcha.length;
        int x = ImageProps.WIDTH / textLength;
        int y = 0;
        for (int i = 0; i < captcha.length; i++) {
            x += 10 + (Math.abs(rand.nextInt()) % 15);
            y = ImageProps.HEIGHT/2 + Math.abs(rand.nextInt()) % 20;
            g2d.drawChars(captcha, i, 1, x, y);
        }
        g2d.dispose();


        return image;
    }

//
//    public static void main(String[] args) {
//        try {
//
//            ImageIO.write(get(Randomizers.getWord()), "png", new File("Output.png")); // Output Image
//            System.out.println("Captcha Generated Successfully!!!");
//        } catch (IOException e) {
//
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }


}
