package ProceduralGraphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Color.*;
import Common.ReadWriteImage;
import Line.ScanConvertLine;
import Vector.*;

public class RandomWalk {
    public void walk(ArrayList<VectorAbstract> path, int scale, int steps){
        Random rn = new Random();
        int currentx = 0;
        int currenty = 0;
        int currentz = 0;
        path.add(new Vector(currentx, currenty, currentz, null));

        for (int i = 0; i < steps; ++i){
            int xdir = rn.nextInt(3) - 1;
            int ydir = rn.nextInt(3) - 1;
            int zdir = rn.nextInt(3) - 1;
            currentx += xdir * scale;
            currenty += ydir * scale;
            currentz += zdir * scale;
            VectorAbstract va = new Vector(currentx, currenty, currentz, null);
            path.add(va);
        }
    }

    public void render(ArrayList<VectorAbstract> path, ColorAbstract color, int[][][] framebuffer){
        ScanConvertLine s = new ScanConvertLine();
        
        try{
            int centerx = framebuffer[0][0].length/2;
            int centery = framebuffer[0].length/2;
            
            this.walk(path, 5, 5000);

            VectorAbstract start = path.get(0);

            for (int i = 0; i < path.size(); i++){
                VectorAbstract end = path.get(i);

                s.bresenham((int)start.getX() + centerx, (int)start.getY() + centery, (int)end.getX() + centerx, (int)end.getY() + centery, color, color, framebuffer);                    
                start = end;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){}
    }

    public static void main(String[] args) {
        RandomWalk rw = new RandomWalk();
        int[][][] framebuffer = new int[3][512][512];
        ArrayList<VectorAbstract> path = new ArrayList<VectorAbstract>();
        rw.render(path, new Color(0, 1, 0), framebuffer);
        try{
            ReadWriteImage.writeImage(framebuffer, "randomwalk.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}