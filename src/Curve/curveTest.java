package Curve;

import Vector.*;

import java.io.IOException;
import java.util.ArrayList;

import Color.*;
import Common.ReadWriteImage;

public class curveTest {
    public static void main(String[] args) {
        VectorAbstract p0 = new Vector(100, 250, 100, new Color(0.0, 1.0, 1.0));
        VectorAbstract p1 = new Vector(125, 300, 100, new Color(0.0, 1.0, 1.0));
        VectorAbstract p2 = new Vector(350, 100, 100, new Color(0.0, 1.0, 1.0));
        VectorAbstract p3 = new Vector(400, 250, 100, new Color(0.0, 1.0, 1.0));
        VectorAbstract p0prime = new Vector(150, 300, 100, new Color(0.0, 1.0, 1.0));
        VectorAbstract p3prime = new Vector(450, 300, 100, new Color(0.0, 1.0, 1.0));


        // Bezier
        int[][][] framebuffer = new int[3][512][512];
        
        Bezier b = new Bezier(p0, p1, p2, p3);
        ArrayList<VectorAbstract> points = new ArrayList<>();

        b.plot(new Color(0.0, 1.0, 1.0), points);
        b.render(points, framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "bezier.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }

        // Hermite
        framebuffer = new int[3][512][512];

        Hermite h = new Hermite(p0, p3, p0prime, p3prime);
        points = new ArrayList<>();

        h.plot(new Color(1.0, 0.0, 1.0), points);
        h.render(points, framebuffer);
        
        try{
            ReadWriteImage.writeImage(framebuffer, "hermite.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }

        // Both
        p0 = new Vector(22, 89, 100, new Color(0.0, 1.0, 1.0));
        p1 = new Vector(277, 455, 100, new Color(0.0, 1.0, 1.0));
        p2 = new Vector(55, 501, 100, new Color(0.0, 1.0, 1.0));
        p3 = new Vector(405, 389, 100, new Color(0.0, 1.0, 1.0));
        p0prime = new Vector(40, 130, 100, new Color(0.0, 1.0, 1.0));
        p3prime = new Vector(450, 200, 100, new Color(0.0, 1.0, 1.0));

        framebuffer = new int[3][512][512];
        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(new Color(0.0, 1.0, 1.0), points);
        b.render(points, framebuffer);

        points = new ArrayList<>();

        h.setCoefs(p0, p3, p0prime, p3prime);
        h.plot(new Color(1.0, 0.0, 1.0), points);
        h.render(points, framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "bothCurves.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
