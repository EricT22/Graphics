package ProceduralGraphics;

import java.io.IOException;

import Common.ReadWriteImage;
import ComplexNumber.*;

public class Mandelbrot {
    public static void mandelbrotGrayscale(int[][][] framebuffer){
        
        ComplexNumber c = new ComplexNumber(-2.5, -2.0);
        double crGap = 4.0/framebuffer[0].length; // -2.5 to 1.5
        double ciGap = 4.0/framebuffer[0][0].length; // -2.0 to 2.0
        
        
        for (int i = 0; i < framebuffer[0].length; i++){
            for (int j = 0; j < framebuffer[0][0].length; j++){
                ComplexNumber z = new ComplexNumber(0, 0);
                int count = 0;

                while (z.mag() < 2.0 && count < 255){
                    z = z.mult(z).add(c);
                    count++;
                }
                framebuffer[0][i][j] = count;
                framebuffer[1][i][j] = count;
                framebuffer[2][i][j] = count;

                c.setImag(c.getImag() + ciGap);;
            }

            c.setReal(c.getReal() + crGap);
            c.setImag(-2.0);
        }
    }

    public static void main(String[] args) {
        int[][][] fb = new int[3][256][256];

        Mandelbrot.mandelbrotGrayscale(fb);

        try{
            ReadWriteImage.writeImage(fb, "mandelbrotGrayscale.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
