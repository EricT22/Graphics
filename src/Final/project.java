package Final;

import java.io.IOException;
import java.util.ArrayList;

import Color.*;
import Common.ReadWriteImage;
import Curve.Bezier;
import Scene.SceneObject;
import Vector.*;

public class project {
    
    public static ArrayList<ArrayList<VectorAbstract>> paths = new ArrayList<>();

    static {
        // Setting up path curves
        ArrayList<VectorAbstract> pointsToMiddle1 = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToMiddle2 = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToBottomRight = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToTopRight1 = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToTopRight2 = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToTopLeft = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToBottomLeft = new ArrayList<>();
        ArrayList<VectorAbstract> pointsToMiddleAgain = new ArrayList<>();

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
        
        
        Bezier b = new Bezier(p0, p1, p2, p3);
        
        b.plot(toMiddle, pointsToMiddle1);
        
        
        p0 = new Vector(405, 389, 100, toMiddle);
        p1 = new Vector(125, 300, 100, toMiddle);
        p2 = new Vector(425, 100, 100, toMiddle);
        p3 = new Vector(512, 512, 100, toMiddle);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toMiddle, pointsToMiddle2);

        p0 = new Vector(512, 512, 100, toBottomRight);
        p1 = new Vector(600, 950, 100, toBottomRight);
        p2 = new Vector(750, 450, 100, toBottomRight);
        p3 = new Vector(900, 900, 100, toBottomRight);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toBottomRight, pointsToBottomRight);



        p0 = new Vector(900, 900, 100, toTopRight);
        p1 = new Vector(1000, 1150, 100, toTopRight);
        p2 = new Vector(600, 300, 100, toTopRight);
        p3 = new Vector(800, 200, 100, toTopRight);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopRight, pointsToTopRight1);


        p0 = new Vector(800, 200, 100, toTopRight);
        p1 = new Vector(1000, 300, 100, toTopRight);
        p2 = new Vector(1000, 0, 100, toTopRight);
        p3 = new Vector(800, 100, 100, toTopRight);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopRight, pointsToTopRight2);


        p0 = new Vector(800, 100, 100, toTopLeft);
        p1 = new Vector(500, 275, 100, toTopLeft);
        p2 = new Vector(400, -50, 100, toTopLeft);
        p3 = new Vector(150, 150, 100, toTopLeft);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toTopLeft, pointsToTopLeft);


        p0 = new Vector(150, 150, 100, toBottomLeft);
        p1 = new Vector(-50, 325, 100, toBottomLeft);
        p2 = new Vector(450, 600, 100, toBottomLeft);
        p3 = new Vector(325, 850, 100, toBottomLeft);


        b.setCoefs(p0, p1, p2, p3);
        b.plot(toBottomLeft, pointsToBottomLeft);


        p0 = new Vector(325, 850, 100, toMiddleAgain);
        p1 = new Vector(-200, 550, 100, toMiddleAgain);
        p2 = new Vector(500, 750, 100, toMiddleAgain);
        p3 = new Vector(512, 512, 100, toMiddleAgain);

        b.setCoefs(p0, p1, p2, p3);
        b.plot(toMiddleAgain, pointsToMiddleAgain);


        paths.add(pointsToMiddle1);
        paths.add(pointsToMiddle2);
        paths.add(pointsToBottomRight);
        paths.add(pointsToTopRight1);
        paths.add(pointsToTopRight2);
        paths.add(pointsToTopLeft);
        paths.add(pointsToBottomLeft);
        paths.add(pointsToMiddleAgain);
    }


    public static void main(String[] args) {
        boolean stop = false;
        boolean scaled = false;
        int counter = 0;

        int[][][] framebuffer;
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        SceneObject s = new SceneObject("Graphics/res/cube-ascii.stl");

        for (ArrayList<VectorAbstract> path : paths){
            if (!stop) {
                for (int i = 1; i < path.size(); i++){
                    Vector d = new Vector((path.get(i).getX() - path.get(i - 1).getX()), 
                                        (path.get(i).getY() - path.get(i - 1).getY()), 
                                        (path.get(i).getZ() - path.get(i - 1).getZ()),
                                        new Color(0, 0, 0));


                    Vector perp = new Vector(-d.getY(), d.getX(), d.getZ(), d.getColor());

                    if (!scaled) {
                        double scaleFactor = Math.pow(100, 1.0/path.size());
                        Vector sf = new Vector(scaleFactor, scaleFactor, scaleFactor, null);

                        s.scale(sf);
                    }
                    s.translate(path.get(i).sub(s.getCenter()));

                    // unit vector of (0,0,0) is undefined and rotating by it results in an object full of Vectors (NaN, NaN, NaN)
                    if (!perp.equals(new Vector(0.0, 0.0, 0.0, null))) {
                        s.rotate(perp, 10);
                    }
                    

                    if (i % 25 == 0){
                        try {
                            // only render when you need an image
                            framebuffer = new int[3][1024][1024];
                            s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
                            ReadWriteImage.writeImage(framebuffer, getFilename(counter, "project") + ".PNG");

                            counter++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                scaled = true;
                // stop = true; // this line is for debugging purposes only (only goes through the first bezier curve)
            }
        }

        s.serialize("Graphics/res/cube.so");
    }

    public static String getFilename(int counter, String filename){
        if (counter >= 1000){
            filename += counter;
        } else if (counter >= 100){
            filename += "0" + counter;
        } else if (counter >= 10) {
            filename += "00" + counter;
        } else {
            filename += "000" + counter;
        }

        return filename;
    }
}