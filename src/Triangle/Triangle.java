public class Triangle extends TriangleAbstract {

    public Triangle(VectorAbstract a, VectorAbstract b, VectorAbstract c){
        vertices = new VectorAbstract[3];
        vertices[0] = a;
        vertices[1] = b;
        vertices[2] = c;
    }


    @Override
    public VectorAbstract getCenter() {
        return new Vector((vertices[0].x + vertices[1].x + vertices[2].x) / 3,
                          (vertices[0].y + vertices[1].y + vertices[2].y) / 3,
                          (vertices[0].z + vertices[1].z + vertices[2].z) / 3);
    }


    @Override
    public double getPerimeter() {
        double perimeter = 0;

        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                perimeter += Math.sqrt(Math.pow(vertices[j].x - vertices[i].x, 2) + 
                                       Math.pow(vertices[j].y - vertices[i].y, 2) + 
                                       Math.pow(vertices[j].z - vertices[i].z, 2));                
            } catch (IndexOutOfBoundsException e) {
                perimeter += Math.sqrt(Math.pow(vertices[i].x - vertices[0].x, 2) + 
                                       Math.pow(vertices[i].y - vertices[0].y, 2) + 
                                       Math.pow(vertices[i].z - vertices[0].z, 2));
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

            sides[j] = new Vector(vertices[i].x - vertices[j].x, vertices[i].y - vertices[j].y, vertices[i].z - vertices[j].z); 
        }

        return sides[0].cross(sides[1]);
    }
    
}
