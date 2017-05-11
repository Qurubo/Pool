import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Created by qurub on 01.05.2017.
 */
public class Worker implements Runnable {
    BufferedImage inputImg, outputImg;
    int[][] pixelMatrix = new int[3][3];
    String name,name1;

    public Worker(int name) {
        this.name = "D:/img/"+name+".jpg";
        this.name1 = "D:/img_1/"+name+".jpg";
    }

    @Override
    public void run() {
        try {

            inputImg = ImageIO.read(new File(name));
            outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(), TYPE_INT_RGB);

            for (int i = 1; i < inputImg.getWidth() - 1; i++) {
                for (int j = 1; j < inputImg.getHeight() - 1; j++) {
                    pixelMatrix[0][0] = new Color(inputImg.getRGB(i - 1, j - 1)).getRed();
                    pixelMatrix[0][1] = new Color(inputImg.getRGB(i - 1, j)).getRed();
                    pixelMatrix[0][2] = new Color(inputImg.getRGB(i - 1, j + 1)).getRed();
                    pixelMatrix[1][0] = new Color(inputImg.getRGB(i, j - 1)).getRed();
                    pixelMatrix[1][2] = new Color(inputImg.getRGB(i, j + 1)).getRed();
                    pixelMatrix[2][0] = new Color(inputImg.getRGB(i + 1, j - 1)).getRed();
                    pixelMatrix[2][1] = new Color(inputImg.getRGB(i + 1, j)).getRed();
                    pixelMatrix[2][2] = new Color(inputImg.getRGB(i + 1, j + 1)).getRed();

                    int edge = (int) convolution(pixelMatrix);
                    outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                }
            }

            File outputfile = new File(name1);
            ImageIO.write(outputImg, "jpg", outputfile);

        } catch (IOException ex) {
            System.err.println("Image width:height=" + inputImg.getWidth() + ":" + inputImg.getHeight());
        }
    }
    private  double convolution(int[][] pixelMatrix) {

        int gy = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * -2) + (pixelMatrix[0][2] * -1) + (pixelMatrix[2][0]) + (pixelMatrix[2][1] * 2) + (pixelMatrix[2][2] * 1);
        int gx = (pixelMatrix[0][0]) + (pixelMatrix[0][2] * -1) + (pixelMatrix[1][0] * 2) + (pixelMatrix[1][2] * -2) + (pixelMatrix[2][0]) + (pixelMatrix[2][2] * -1);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }
}
