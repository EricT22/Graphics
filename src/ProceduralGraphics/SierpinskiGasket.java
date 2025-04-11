package ProceduralGraphics;

import Vector.*;

import java.io.IOException;

import Color.Color;
import Common.ReadWriteImage;
import Triangle.*;
import Shader.*;

public class SierpinskiGasket {
    public void sierpinski(TriangleAbstract t, int limit, int[][][] framebuffer){
        if (t.getArea() < limit){
            t.render(framebuffer, false, Shader.FILLSTYLE.NONE, new Vector(0, 0, -1, new Color(0, 0, 0)));
            return;
        }
        
        VectorAbstract[] verts = t.getVertices();

        VectorAbstract p0 = verts[0];
        VectorAbstract p1 = verts[1];
        VectorAbstract p2 = verts[2];

        TriangleAbstract t0 = new Triangle(p0, getMidpoint(p0, p1), getMidpoint(p0, p2));
        TriangleAbstract t1 = new Triangle(p1, getMidpoint(p1, p0), getMidpoint(p1, p2));
        TriangleAbstract t2 = new Triangle(p2, getMidpoint(p1, p2), getMidpoint(p0, p2));

        sierpinski(t0, limit, framebuffer);
        sierpinski(t1, limit, framebuffer);
        sierpinski(t2, limit, framebuffer);
    }

    public VectorAbstract getMidpoint(VectorAbstract v0, VectorAbstract v1){
        return new Vector((v0.getX() + v1.getX()) / 2.0, (v0.getY() + v1.getY()) / 2.0, (v0.getZ() + v1.getZ()) / 2.0, v0.getColor());
    }
    
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][256][256];
        VectorAbstract v0, v1, v2;
		TriangleAbstract t;

        int scalefactor = 100;
        VectorAbstract offset = new Vector(78, 78, 78, new Color(1.0, 0.0, 0.0));


        v0 = new Vector(0.0, 0.0, 1.0, new Color(1.0, 0.0, 0.0));
        v0 = v0.mult(scalefactor);
        v0 = v0.add(offset);
        v1 = new Vector(1.0, 0.0, 1.0, new Color(1.0, 0.0, 0.0));
        v1 = v1.mult(scalefactor);
        v1 = v1.add(offset);
        v2 = new Vector(0.0, 1.0, 1.0, new Color(1.0, 0.0, 0.0));
        v2 = v2.mult(scalefactor);
        v2 = v2.add(offset);
        t = new Triangle(v0, v1, v2);

        SierpinskiGasket s = new SierpinskiGasket();
        s.sierpinski(t, 1, framebuffer);

        try{
            ReadWriteImage.writeImage(framebuffer, "sierpinski.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
