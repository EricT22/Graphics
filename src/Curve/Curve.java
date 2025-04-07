package Curve;

import java.util.ArrayList;

import Color.ColorAbstract;
import Vector.*;

public abstract class Curve {

    protected double[][] c = new double[4][3];

    protected VectorAbstract p0 = null;
    protected VectorAbstract p1 = null;
    protected VectorAbstract p2 = null;
    protected VectorAbstract p3 = null;
    protected VectorAbstract p0prime = null;
    protected VectorAbstract p3prime = null;

    public abstract void setCoefs(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3);

    public double[] generatePoint(double u){
        double[] point = new double[3];

        point[0] = (c[3][0] * u) + (c[2][0] * u) + (c[1][0] * u) + (c[0][0]);
        point[1] = (c[3][1] * u) + (c[2][1] * u) + (c[1][1] * u) + (c[0][1]);
        point[2] = (c[3][2] * u) + (c[2][2] * u) + (c[1][2] * u) + (c[0][2]);

        return point;
    }

    public void render(ColorAbstract c, int[][][] framebuffer){
        // TODO
    }

    public void plot(ColorAbstract c, ArrayList<VectorAbstract> points){
        // TODO
    }
}
