public class VectorTriangleTest {
	/**
	 * Test various methods of the TriangleAbstract and VectorAbstract classes
	 * @param v0 VectorAbstract representing first leg of the triangle
	 * @param v1 VectorAbstract representing second leg of the triangle
	 * @param v2 VectorAbstract representing third leg of the triangle
	 */
	public static void printData(VectorAbstract v0, VectorAbstract v1, VectorAbstract v2) {
		TriangleAbstract t = new Triangle(v0, v1, v2);
		System.out.println("===========================");
		System.out.println("Triangle: " + t);
		System.out.println("Normal: " + t.getNormal() + "(" + t.getNormal().unit().length() + ")");
		System.out.println("Area: " + t.getArea() + " " + "Perimeter: " + t.getPerimeter());
		System.out.println("Center: " + t.getCenter());
		System.out.println("---------------------------");
		System.out.println("Scale: " + v0.mult(1) + " " + v1.mult(2) + " " + v2.mult(3));
		System.out.println("Dot: " + v0.dot(v1) + " " + v1.dot(v2) + " " + v2.dot(v0));
		System.out.println("Angles: " + Math.toDegrees(v1.sub(v0).angleBetween(v1.sub(v2))) + " " + 
				                        Math.toDegrees(v2.sub(v1).angleBetween(v2.sub(v0))) + " " + 
				                        Math.toDegrees(v0.sub(v2).angleBetween(v0.sub(v1))));
		
		System.out.println();
	}
	
	/**
	 * Entry point for the VectorAbstract and TriangleAbstract class tests
	 * @param args unused at this time
	 */
	public static void main(String[] args) {
		VectorAbstract v0, v1, v2;
		v0 = new Vector(0.0, 0.0, 10.0);
		v1 = new Vector(10.0, 0.0, 10.0);
		v2 = new Vector(0.0, 10.0, 10.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 10.0, 10.0);
		v1 = new Vector(0.0, 10.0, 10.0);
		v2 = new Vector(10.0, 0.0, 10.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 0.0, 10.0);
		v1 = new Vector(10.0, 0.0, 0.0);
		v2 = new Vector(10.0, 10.0, 10.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 10.0, 0.0);
		v1 = new Vector(10.0, 10.0, 10.0);
		v2 = new Vector(10.0, 0.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 0.0, 0.0);
		v1 = new Vector(0.0, 0.0, 0.0);
		v2 = new Vector(10.0, 10.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(0.0, 10.0, 0.0);
		v1 = new Vector(10.0, 10.0, 0.0);
		v2 = new Vector(0.0, 0.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(0.0, 0.0, 0.0);
		v1 = new Vector(0.0, 0.0, 10.0);
		v2 = new Vector(0.0, 10.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(0.0, 10.0, 10.0);
		v1 = new Vector(0.0, 10.0, 0.0);
		v2 = new Vector(0.0, 0.0, 10.0);
		printData(v0, v1, v2);
		v0 = new Vector(0.0, 10.0, 10.0);
		v1 = new Vector(10.0, 10.0, 10.0);
		v2 = new Vector(0.0, 10.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 10.0, 0.0);
		v1 = new Vector(0.0, 10.0, 0.0);
		v2 = new Vector(10.0, 10.0, 10.0);
		printData(v0, v1, v2);
		v0 = new Vector(10.0, 0.0, 10.0);
		v1 = new Vector(0.0, 0.0, 10.0);
		v2 = new Vector(10.0, 0.0, 0.0);
		printData(v0, v1, v2);
		v0 = new Vector(0.0, 0.0, 0.0);
		v1 = new Vector(10.0, 0.0, 0.0);
		v2 = new Vector(0.0, 0.0, 10.0);
		printData(v0, v1, v2);

	}

}
