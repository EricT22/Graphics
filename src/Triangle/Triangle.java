package Triangle;

import Vector.*;
import Line.*;
import Matrix.*;
import Color.*;
import Affine.*;

public class Triangle extends TriangleAbstract {

    public Triangle(VectorAbstract a, VectorAbstract b, VectorAbstract c){
        super();

        Color white = new Color(1.0, 1.0, 1.0);

        if (a.getColor() == null) {
            a.setColor(white);
        }

        if (b.getColor() == null) {
            b.setColor(white);
        }

        if (c.getColor() == null) {
            c.setColor(white);
        }
        
        vertices = new VectorAbstract[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
    }


    @Override
    public VectorAbstract getCenter() {
        return new Vector((vertices[0].getX() + vertices[1].getX() + vertices[2].getX()) / 3,
                          (vertices[0].getY() + vertices[1].getY() + vertices[2].getY()) / 3,
                          (vertices[0].getZ() + vertices[1].getZ() + vertices[2].getZ()) / 3,
                          null);
    }


    @Override
    public double getPerimeter() {
        double perimeter = 0;

        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                perimeter += Math.sqrt(Math.pow(vertices[j].getX() - vertices[i].getX(), 2) + 
                                       Math.pow(vertices[j].getY() - vertices[i].getY(), 2) + 
                                       Math.pow(vertices[j].getZ() - vertices[i].getZ(), 2));                
            } catch (IndexOutOfBoundsException e) {
                perimeter += Math.sqrt(Math.pow(vertices[i].getX() - vertices[0].getX(), 2) + 
                                       Math.pow(vertices[i].getY() - vertices[0].getY(), 2) + 
                                       Math.pow(vertices[i].getZ() - vertices[0].getZ(), 2));
            }
        }

        return perimeter;
    }


    @Override
    public double getArea() {
        return this.getNormal().length() / 2;
    }


    @Override
    public VectorAbstract getNormal() {
        VectorAbstract[] sides = new VectorAbstract[2];

        for (int i = 1; i < vertices.length; i++){
            int j = i - 1;

            sides[j] = new Vector(vertices[i].getX() - vertices[j].getX(), vertices[i].getY() - vertices[j].getY(), vertices[i].getZ() - vertices[j].getZ(), null); 
        }

        return sides[0].cross(sides[1]);
    }

    @Override
    public TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract negFixedPoint = new Vector(-fixedpoint.getX(), -fixedpoint.getY(), -fixedpoint.getZ(), null);
        VectorAbstract origin = new Vector(0.0, 0.0, 0.0, null);

        VectorAbstract[] vertices = data.getVertices();

        double[][] points = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mpoints = new Matrix(points);

        MatrixAbstract toOrigin = a.translate(negFixedPoint, mpoints);
        MatrixAbstract rotateX = a.rotateX(theta, origin, toOrigin);
        MatrixAbstract toFP = a.translate(fixedpoint, rotateX);

        double[][] result = toFP.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }


    @Override
    public TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract negFixedPoint = new Vector(-fixedpoint.getX(), -fixedpoint.getY(), -fixedpoint.getZ(), null);
        VectorAbstract origin = new Vector(0.0, 0.0, 0.0, null);

        VectorAbstract[] vertices = data.getVertices();

        double[][] points = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mpoints = new Matrix(points);

        MatrixAbstract toOrigin = a.translate(negFixedPoint, mpoints);
        MatrixAbstract rotateY = a.rotateY(theta, origin, toOrigin);
        MatrixAbstract toFP = a.translate(fixedpoint, rotateY);
        
        double[][] result = toFP.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }


    @Override
    public TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract negFixedPoint = new Vector(-fixedpoint.getX(), -fixedpoint.getY(), -fixedpoint.getZ(), null);
        VectorAbstract origin = new Vector(0.0, 0.0, 0.0, null);

        VectorAbstract[] vertices = data.getVertices();

        double[][] points = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mpoints = new Matrix(points);

        MatrixAbstract toOrigin = a.translate(negFixedPoint, mpoints);
        MatrixAbstract rotateZ = a.rotateZ(theta, origin, toOrigin);
        MatrixAbstract toFP = a.translate(fixedpoint, rotateZ);

        double[][] result = toFP.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }


    @Override
    public TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract[] vertices = data.getVertices();

        double[][] points = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mpoints = new Matrix(points);

        MatrixAbstract mtranslation = a.translate(transvec, mpoints);

        double[][] result = mtranslation.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }


    @Override
    public TriangleAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformationAbstract a = new AffineTransformation();

        VectorAbstract negFixedPoint = new Vector(-fixedpoint.getX(), -fixedpoint.getY(), -fixedpoint.getZ(), null);
        VectorAbstract origin = new Vector(0.0, 0.0, 0.0, null);

        VectorAbstract[] vertices = data.getVertices().clone();

        double[][] points = {{vertices[0].getX(), vertices[0].getY(), vertices[0].getZ(), 1.0},
                             {vertices[1].getX(), vertices[1].getY(), vertices[1].getZ(), 1.0},
                             {vertices[2].getX(), vertices[2].getY(), vertices[2].getZ(), 1.0}
                            };

        MatrixAbstract mpoints = new Matrix(points);

        MatrixAbstract toOrigin = a.translate(negFixedPoint, mpoints);
        MatrixAbstract scale = a.scale(factor, origin, toOrigin);
        MatrixAbstract toFP = a.translate(fixedpoint, scale);

        double[][] result = toFP.getMatrix();

        for (int i = 0; i < vertices.length; i++){
            vertices[i].setX(result[i][0]);
            vertices[i].setY(result[i][1]);
            vertices[i].setZ(result[i][2]);
        }
        
        Triangle p = new Triangle(vertices[0], vertices[1], vertices[2]);

        return p;
    }

    @Override
    public void render(int[][][] framebuffer, boolean shownormal) {
        ScanConvertAbstract sc = new ScanConvertLine();
        Color white = new Color(1.0, 1.0, 1.0);

        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                             (int)vertices[j].getX(), (int)vertices[j].getY(),
                             vertices[i].getColor(), vertices[j].getColor(), 
                             framebuffer);        
            } catch (IndexOutOfBoundsException e) {
                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                             (int)vertices[0].getX(), (int)vertices[0].getY(),
                             vertices[i].getColor(), vertices[0].getColor(), 
                             framebuffer);    
            }
        }

        if (shownormal) {
            VectorAbstract normal = getNormal().unit().mult(20);
            VectorAbstract center = getCenter();

            normal = normal.add(center);
            
            // Color of normal and center vectors are null, so we set color to white as default
            sc.bresenham((int)center.getX(), (int)center.getY(), 
                         (int)normal.getX(), (int)normal.getY(),
                         white, white, 
                         framebuffer);
        }
    }
    
}
