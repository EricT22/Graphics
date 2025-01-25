import Vector.*;

public class Triangle extends TriangleAbstract {

    public Triangle(VectorAbstract a, VectorAbstract b, VectorAbstract c){
        super();
        
        vertices = new VectorAbstract[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
    }


    @Override
    public VectorAbstract getCenter() {
        return new Vector((vertices[0].getX() + vertices[1].getX() + vertices[2].getX()) / 3,
                          (vertices[0].getY() + vertices[1].getY() + vertices[2].getY()) / 3,
                          (vertices[0].getZ() + vertices[1].getZ() + vertices[2].getZ()) / 3);
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

            sides[j] = new Vector(vertices[i].getX() - vertices[j].getX(), vertices[i].getY() - vertices[j].getY(), vertices[i].getZ() - vertices[j].getZ()); 
        }

        return sides[0].cross(sides[1]);
    }
    
}
