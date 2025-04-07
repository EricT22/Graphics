package Curve;


import Vector.VectorAbstract;

public class Bezier extends Curve {

    public Bezier(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3){
        setCoefs(p0, p1, p2, p3);
    }

    @Override
    public void setCoefs(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        c[0][0] = p0.getX();
        c[0][1] = p0.getY();
        c[0][2] = p0.getZ();

        c[1][0] = (-3 * p0.getX()) + (3 * p1.getX());
        c[1][1] = (-3 * p0.getY()) + (3 * p1.getY());
        c[1][2] = (-3 * p0.getZ()) + (3 * p1.getZ());

        c[2][0] = (3 * p0.getX()) + (-6 * p1.getX()) + (3 * p2.getX());
        c[2][1] = (3 * p0.getY()) + (-6 * p1.getY()) + (3 * p2.getY());
        c[2][2] = (3 * p0.getZ()) + (-6 * p1.getZ()) + (3 * p2.getZ());

        c[3][0] = (-1 * p0.getX()) + (3 * p1.getX()) + (-3 * p2.getX()) + (1 * p3.getX());
        c[3][1] = (-1 * p0.getY()) + (3 * p1.getY()) + (-3 * p2.getY()) + (1 * p3.getY());
        c[3][2] = (-1 * p0.getZ()) + (3 * p1.getZ()) + (-3 * p2.getZ()) + (1 * p3.getZ());
    }
    
}
