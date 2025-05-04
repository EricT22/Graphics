package ProceduralGraphics;

import Color.*;
import Triangle.*;
import Vector.*;

public class Particle {
    
    private VectorAbstract x;
    private VectorAbstract v;
    private TriangleAbstract t;

    public Particle(VectorAbstract x, VectorAbstract v, ColorAbstract c){
        this.x = x;
        this.v = v;

        x.setColor((Color)c);

        t = new Triangle(x.add(v), 
                         x.add(new Vector(-v.getY(), v.getX(), v.getZ(), null)),
                         x.add(new Vector(v.getY(), -v.getX(), v.getZ(), null)));


        // t = t.scale(new Vector(10, 10, 10, null), t.getCenter(), t);
    }

    public void update(){
        x = x.add(v);

        t = new Triangle(x.add(v), 
                         x.add(new Vector(-v.getY(), v.getX(), v.getZ(), null)),
                         x.add(new Vector(v.getY(), -v.getX(), v.getZ(), null)));

        // t = t.scale(new Vector(10, 10, 10, null), t.getCenter(), t);
    }

    public VectorAbstract getPosition(){
        return ((Vector)x).copy();
    }

    public VectorAbstract getVelocity(){
        return ((Vector)v).copy();
    }

    public void render(int[][][] framebuffer, VectorAbstract viewpoint){
        t.render(framebuffer, false, Shader.Shader.FILLSTYLE.FILL, viewpoint);
    }
}
