package Curve;

import Vector.VectorAbstract;

public class Hermite extends Curve {

    public Hermite(VectorAbstract p0, VectorAbstract p3, VectorAbstract p0prime, VectorAbstract p3prime){
        setCoefs(p0, p3, p0prime, p3prime);
    }

    @Override
    public void setCoefs(VectorAbstract p0, VectorAbstract p3, VectorAbstract p0prime, VectorAbstract p3prime) {
        this.p0 = p0;
        this.p3 = p3;
        this.p0prime = p0prime;
        this.p3prime = p3prime;

        c[0][0] = p0.getX();
        c[0][1] = p0.getY();
        c[0][2] = p0.getZ();

        c[1][0] = p0prime.getX();
        c[1][1] = p0prime.getY();
        c[1][2] = p0prime.getZ();

        c[2][0] = (-3 * p0.getX()) + (3 * p3.getX()) + (-2 * p0prime.getX()) + (-1 * p3prime.getX());
        c[2][1] = (-3 * p0.getY()) + (3 * p3.getY()) + (-2 * p0prime.getY()) + (-1 * p3prime.getY());
        c[2][2] = (-3 * p0.getZ()) + (3 * p3.getZ()) + (-2 * p0prime.getZ()) + (-1 * p3prime.getZ());

        c[3][0] = (2 * p0.getX()) + (-2 * p3.getX()) + (1 * p0prime.getX()) + (1 * p3prime.getX());
        c[3][1] = (2 * p0.getY()) + (-2 * p3.getY()) + (1 * p0prime.getY()) + (1 * p3prime.getY());
        c[3][2] = (2 * p0.getZ()) + (-2 * p3.getZ()) + (1 * p0prime.getZ()) + (1 * p3prime.getZ());
    }
    
}
