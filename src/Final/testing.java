package Final;

import Vector.*;

import java.io.IOException;
import java.util.ArrayList;

import Color.*;
import Curve.*;
import Common.ReadWriteImage;

public class testing {

    public static void main(String[] args) {
        Color toMiddle = new Color(0.0, 1.0, 1.0);
        Color toBottomRight = new Color(1.0, 0.0, 0.0);
        Color toTopRight = new Color(1.0, 1.0, 0.0);
        Color toTopLeft = new Color(0.0, 1.0, 0.0);
        Color toBottomLeft = new Color(1.0, 0.0, 1.0);
        Color toMiddleAgain = new Color(0.0, 0.0, 1.0);
        
        VectorAbstract p0 = new Vector(0, 0, 100, toMiddle);
        VectorAbstract p1 = new Vector(277, 455, 100, toMiddle);
        VectorAbstract p2 = new Vector(55, 501, 100, toMiddle);
        VectorAbstract p3 = new Vector(405, 389, 100, toMiddle);
        
        int[][][] framebuffer = new int[3][1024][1024];
        
        Bezier b = new Bezier(p0, p1, p2, p3);
        ArrayList<VectorAbstract> points = new ArrayList<>();
        
        b.plot(toMiddle, points);
        b.render(points, framebuffer);
        
        
        p0 = new Vector(405, 389, 100, toMiddle);
        p1 = new Vector(125, 300, 100, toMiddle);
        p2 = new Vector(425, 100, 100, toMiddle);
        p3 = new Vector(512, 512, 100, toMiddle);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toMiddle, points);
        b.render(points, framebuffer);

        p0 = new Vector(512, 512, 100, toBottomRight);
        p1 = new Vector(600, 950, 100, toBottomRight);
        p2 = new Vector(750, 450, 100, toBottomRight);
        p3 = new Vector(900, 900, 100, toBottomRight);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toBottomRight, points);
        b.render(points, framebuffer);



        p0 = new Vector(900, 900, 100, toTopRight);
        p1 = new Vector(1000, 1150, 100, toTopRight);
        p2 = new Vector(600, 300, 100, toTopRight);
        p3 = new Vector(800, 200, 100, toTopRight);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopRight, points);
        b.render(points, framebuffer);


        p0 = new Vector(800, 200, 100, toTopRight);
        p1 = new Vector(1000, 300, 100, toTopRight);
        p2 = new Vector(1000, 0, 100, toTopRight);
        p3 = new Vector(800, 100, 100, toTopRight);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopRight, points);
        b.render(points, framebuffer);


        p0 = new Vector(800, 100, 100, toTopLeft);
        p1 = new Vector(500, 275, 100, toTopLeft);
        p2 = new Vector(400, -50, 100, toTopLeft);
        p3 = new Vector(150, 150, 100, toTopLeft);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopLeft, points);
        b.render(points, framebuffer);


        p0 = new Vector(150, 150, 100, toBottomLeft);
        p1 = new Vector(-50, 325, 100, toBottomLeft);
        p2 = new Vector(450, 600, 100, toBottomLeft);
        p3 = new Vector(325, 850, 100, toBottomLeft);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toBottomLeft, points);
        b.render(points, framebuffer);


        p0 = new Vector(325, 850, 100, toMiddleAgain);
        p1 = new Vector(-200, 550, 100, toMiddleAgain);
        p2 = new Vector(500, 750, 100, toMiddleAgain);
        p3 = new Vector(512, 512, 100, toMiddleAgain);

        points = new ArrayList<>();

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toMiddleAgain, points);
        b.render(points, framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "bothCurves.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}
