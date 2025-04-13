package Curve;

import java.util.ArrayList;

import Color.*;
import Line.ScanConvertLine;
import Vector.*;

public abstract class Curve {

    private double PRECISION = 0.001;

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

        point[0] = (c[3][0] * Math.pow(u, 3)) + (c[2][0] * Math.pow(u, 2)) + (c[1][0] * u) + (c[0][0]);
        point[1] = (c[3][1] * Math.pow(u, 3)) + (c[2][1] * Math.pow(u, 2)) + (c[1][1] * u) + (c[0][1]);
        point[2] = (c[3][2] * Math.pow(u, 3)) + (c[2][2] * Math.pow(u, 2)) + (c[1][2] * u) + (c[0][2]);

        return point;
    }

    public void render(ArrayList<VectorAbstract> points, int[][][] framebuffer){
        ScanConvertLine s = new ScanConvertLine();

        for (int i = 1; i < points.size(); i++){
            s.bresenham((int)points.get(i - 1).getX(), (int)points.get(i - 1).getY(),
                        (int)points.get(i).getX(), (int)points.get(i).getY(), 
                        points.get(i - 1).getColor(), points.get(i).getColor(), 
                        framebuffer);
        }
    }

    public void plot(ColorAbstract c, ArrayList<VectorAbstract> points){
        ScanConvertLine s = new ScanConvertLine();
        double[] oldPoint = {p0.getX(), p0.getY(), p0.getZ()};

        for (double u = 0.0; u < 1.0 + PRECISION; u += 0.025){
            double[] newPoint = generatePoint(u);

            s.bresenham((int)oldPoint[0], (int)oldPoint[1], (int)newPoint[0], (int)newPoint[1], c, c, points);
            oldPoint = newPoint;
        }
    }
}
