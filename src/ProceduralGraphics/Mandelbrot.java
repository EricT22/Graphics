package ProceduralGraphics;

import java.io.IOException;
import java.util.ArrayList;

import Common.ReadWriteImage;
import ComplexNumber.*;
import Curve.*;
import Vector.*;
import Color.*;

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


    public static void mandelbrotRandomColor(int[][][] framebuffer) {
        ReadWriteImage.setLUT(); // instantiates LUT w/ random color

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
                framebuffer[0][i][j] = ReadWriteImage.LUT[count][0];
                framebuffer[1][i][j] = ReadWriteImage.LUT[count][1];
                framebuffer[2][i][j] = ReadWriteImage.LUT[count][2];

                c.setImag(c.getImag() + ciGap);;
            }

            c.setReal(c.getReal() + crGap);
            c.setImag(-2.0);
        }
    }


    public static void mandelbrotLUT(int[][][] framebuffer, int[][] LUT){
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
                framebuffer[0][i][j] = LUT[count][0];
                framebuffer[1][i][j] = LUT[count][1];
                framebuffer[2][i][j] = LUT[count][2];

                c.setImag(c.getImag() + ciGap);;
            }

            c.setReal(c.getReal() + crGap);
            c.setImag(-2.0);
        }
    }


    public static int[][] bezierLUT(){
        int[][] LUT = new int[256][3];

        Bezier curve = new Bezier(new Vector(0, 0, 0, new Color(0, 0, 0)), 
                              new Vector(75, 255, 0, new Color(0, 0, 0)), 
                              new Vector(175, 255, 0, new Color(0, 0, 0)), 
                              new Vector(255, 0, 0, new Color(0, 0, 0)));

        ArrayList<VectorAbstract> r = new ArrayList<>();
        curve.plot(new Color(0, 0, 0), r);

        curve.setCoefs(new Vector(0, 0, 0, new Color(0, 0, 0)), 
                       new Vector(75, 255, 0, new Color(0, 0, 0)), 
                       new Vector(175, 75, 0, new Color(0, 0, 0)), 
                       new Vector(255, 255, 0, new Color(0, 0, 0)));

        ArrayList<VectorAbstract> g = new ArrayList<>();
        curve.plot(new Color(0, 0, 0), g);

        curve.setCoefs(new Vector(0, 0, 0, new Color(0, 0, 0)), 
                       new Vector(75, 100, 0, new Color(0, 0, 0)), 
                       new Vector(175, 100, 0, new Color(0, 0, 0)), 
                       new Vector(255, 0, 0, new Color(0, 0, 0)));
        
        ArrayList<VectorAbstract> b = new ArrayList<>();
        curve.plot(new Color(0, 0, 0), b);


        for (VectorAbstract v : r){
            try {
                LUT[(int)v.getX()][0] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }

        for (VectorAbstract v : g){
            try {
                LUT[(int)v.getX()][1] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }

        for (VectorAbstract v : b){
            try {
                LUT[(int)v.getX()][2] = (int) v.getY();
            } catch (IndexOutOfBoundsException e){}
        }

        return LUT;
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


        // fb = new int[3][256][256];

        // Mandelbrot.mandelbrotRandomColor(fb);

        // try{
        //     ReadWriteImage.writeImage(fb, "mandelbrotRandom.PNG");
        // }
        // catch (IOException e) {
        //     System.out.println(e);
        // }

        int[][] lut = Mandelbrot.bezierLUT();

        // for (int i = 0; i < lut.length; i++){
        //     System.out.println("R:" + lut[i][0] + "\tG:" + lut[i][1] + "\tB:" + lut[i][2]);
        // }

        fb = new int[3][256][256];

        Mandelbrot.mandelbrotLUT(fb, lut);
        
        try{
            ReadWriteImage.writeImage(fb, "mandelbrotLUT.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
