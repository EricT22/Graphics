package Affine;

import Matrix.Matrix;
import Matrix.MatrixAbstract;
import Vector.VectorAbstract;

public class AffineTransformation extends AffineTransformationAbstract{

    public static final double PRECISION = 0.000001;

    // NOTE: All functions assume that data comes in as an array of points in the following form:
    //      {
    //       {x1, y1, z1},
    //       {x2, y2, z3},
    //       ...
    //      }
    //      And will also return the data in this form

    @Override
    public MatrixAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads, MatrixAbstract data){
        axis = axis.unit();

        if (Math.abs(axis.getY()) < PRECISION && Math.abs(axis.getZ()) < PRECISION) {
            return rotateX(arads, fixedpoint, data);
        }

        double d = Math.sqrt(Math.pow(axis.getY(), 2) + Math.pow(axis.getZ(), 2));

        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()},
                                 {0, 1, 0, -fixedpoint.getY()},
                                 {0, 0, 1, -fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        double[][] rXp = {{1, 0, 0, 0},
                          {0, axis.getZ() / d, -axis.getY() / d, 0},
                          {0, axis.getY() / d, axis.getZ() / d, 0},
                          {0, 0, 0, 1}};

        double[][] rYp = {{d, 0, -axis.getX(), 0},
                          {0, 1, 0, 0},
                          {axis.getX(), 0, d, 0},
                          {0, 0, 0, 1}};

        double[][] rotateZ = {{Math.cos(arads), -Math.sin(arads), 0, 0},
                              {Math.sin(arads), Math.cos(arads), 0, 0},
                              {0, 0, 1, 0},
                              {0, 0, 0, 1}};

        double[][] rYn = {{d, 0, axis.getX(), 0},
                          {0, 1, 0, 0},
                          {-axis.getX(), 0, d, 0},
                          {0, 0, 0, 1}};

        double[][] rXn = {{1, 0, 0, 0},
                          {0, axis.getZ() / d, axis.getY() / d, 0},
                          {0, -axis.getY() / d, axis.getZ() / d, 0},
                          {0, 0, 0, 1}};


        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()},
                                 {0, 1, 0, fixedpoint.getY()},
                                 {0, 0, 1, fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        MatrixAbstract fpToOriginMatrix = new Matrix(fpToOrigin);
        MatrixAbstract rXpMatrix = new Matrix(rXp);
        MatrixAbstract rYpMatrix = new Matrix(rYp);
        MatrixAbstract rotateZMatrix = new Matrix(rotateZ);
        MatrixAbstract rYnMatrix = new Matrix(rYn);
        MatrixAbstract rXnMatrix = new Matrix(rXn);
        MatrixAbstract originToFpMatrix = new Matrix(originTofp);

        MatrixAbstract m = originToFpMatrix.mult(rXnMatrix.mult(rYnMatrix.mult(rotateZMatrix.mult(rYpMatrix.mult(rXpMatrix.mult(fpToOriginMatrix))))));
    
        return m.mult(data.transpose()).transpose();
    }

    @Override
    public MatrixAbstract rotateX(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()},
                                 {0, 1, 0, -fixedpoint.getY()},
                                 {0, 0, 1, -fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        double[][] rotateX = {{1, 0, 0, 0},
                              {0, Math.cos(theta), -Math.sin(theta), 0},
                              {0, Math.sin(theta), Math.cos(theta), 0},
                              {0, 0, 0, 1}};


        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()},
                                 {0, 1, 0, fixedpoint.getY()},
                                 {0, 0, 1, fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        MatrixAbstract fpToOriginMatrix = new Matrix(fpToOrigin);
        MatrixAbstract rotateXMatrix = new Matrix(rotateX);
        MatrixAbstract originToFpMatrix = new Matrix(originTofp);

        MatrixAbstract t = originToFpMatrix.mult(rotateXMatrix.mult(fpToOriginMatrix));

        return t.mult(data.transpose()).transpose();
    }

    @Override
    public MatrixAbstract rotateY(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()},
                                 {0, 1, 0, -fixedpoint.getY()},
                                 {0, 0, 1, -fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        double[][] rotateY = {{Math.cos(theta), 0, Math.sin(theta), 0},
                              {0, 1, 0, 0},
                              {-Math.sin(theta), 0, Math.cos(theta), 0},
                              {0, 0, 0, 1}};


        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()},
                                 {0, 1, 0, fixedpoint.getY()},
                                 {0, 0, 1, fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        MatrixAbstract fpToOriginMatrix = new Matrix(fpToOrigin);
        MatrixAbstract rotateYMatrix = new Matrix(rotateY);
        MatrixAbstract originToFpMatrix = new Matrix(originTofp);

        MatrixAbstract t = originToFpMatrix.mult(rotateYMatrix.mult(fpToOriginMatrix));

        return t.mult(data.transpose()).transpose();
    }

    @Override
    public MatrixAbstract rotateZ(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()},
                                 {0, 1, 0, -fixedpoint.getY()},
                                 {0, 0, 1, -fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        double[][] rotateZ = {{Math.cos(theta), -Math.sin(theta), 0, 0},
                              {Math.sin(theta), Math.cos(theta), 0, 0},
                              {0, 0, 1, 0},
                              {0, 0, 0, 1}};


        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()},
                                 {0, 1, 0, fixedpoint.getY()},
                                 {0, 0, 1, fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        MatrixAbstract fpToOriginMatrix = new Matrix(fpToOrigin);
        MatrixAbstract rotateZMatrix = new Matrix(rotateZ);
        MatrixAbstract originToFpMatrix = new Matrix(originTofp);

        MatrixAbstract t = originToFpMatrix.mult(rotateZMatrix.mult(fpToOriginMatrix));

        return t.mult(data.transpose()).transpose();
    }

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        double[][] tarr = {{1, 0, 0, transvec.getX()},
                           {0, 1, 0, transvec.getY()},
                           {0, 0, 1, transvec.getZ()},
                           {0, 0, 0, 1}};

        Matrix t = new Matrix(tarr);

        return t.mult(data.transpose()).transpose();
    }

    @Override
    public MatrixAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] fpToOrigin = {{1, 0, 0, -fixedpoint.getX()},
                                 {0, 1, 0, -fixedpoint.getY()},
                                 {0, 0, 1, -fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        double[][] scale = {{factor.getX(), 0, 0, 0},
                            {0, factor.getY(), 0, 0},
                            {0, 0, factor.getZ(), 0},
                            {0, 0, 0, 1}};


        double[][] originTofp = {{1, 0, 0, fixedpoint.getX()},
                                 {0, 1, 0, fixedpoint.getY()},
                                 {0, 0, 1, fixedpoint.getZ()},
                                 {0, 0, 0, 1}};

        MatrixAbstract fpToOriginMatrix = new Matrix(fpToOrigin);
        MatrixAbstract scaleMatrix = new Matrix(scale);
        MatrixAbstract originToFpMatrix = new Matrix(originTofp);

        MatrixAbstract t = originToFpMatrix.mult(scaleMatrix.mult(fpToOriginMatrix));

        return t.mult(data.transpose()).transpose();
    }
}
