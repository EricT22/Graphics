package Affine;

import Matrix.Matrix;
import Matrix.MatrixAbstract;
import Vector.VectorAbstract;

public class AffineTransformation extends AffineTransformationAbstract{

    // NOTE: All functions assume that data comes in as an array of points in the following form:
    //      {
    //       {x1, y1, z1},
    //       {x2, y2, z3},
    //       ...
    //      }

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

        return t.mult(data.transpose());
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

        return t.mult(data.transpose());
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

        return t.mult(data.transpose());
    }

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        double[][] tarr = {{1, 0, 0, transvec.getX()},
                           {0, 1, 0, transvec.getY()},
                           {0, 0, 1, transvec.getZ()},
                           {0, 0, 0, 1}};

        Matrix t = new Matrix(tarr);

        return t.mult(data.transpose());
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

        return t.mult(data.transpose());
    }
    
}
