package Matrix;

public class MatrixTest {
    public static void main(String[] args) {
		double m[][] = {{3, 6, 12, 9}, {2, 3, 5, 7}, {1, 7, 2, 3}, {4, 9, 1, 2}}; 
		MatrixAbstract mat = new Matrix(m);
		System.out.println(mat);
		
		MatrixAbstract inv = mat.invert();
		System.out.println(inv);
		
		MatrixAbstract ident = mat.mult(inv);
		System.out.println(ident);
		
		MatrixAbstract trans = mat.transpose();
		System.out.println(trans);
		
		MatrixAbstract scale = mat.scale(10);
		System.out.println(scale);
	}
}
