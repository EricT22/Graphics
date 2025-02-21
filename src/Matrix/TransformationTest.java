package Matrix;

public class TransformationTest {
    public static void main(String[] args) {
        double[][] test = {{1, 2, 3},
                           {4, 7, 6},
                           {7, 10, 9}};

        Matrix a = new Matrix(test);

        System.out.println(a.scale(10));
        System.out.println(a.invert());

        double x = 5;
        double y = 6;
        double z = 7;
        double[][] pt = {{1, 2, 0}, {2, 4, 0}, {3, 6, 0}, {1.0, 1.0, 1.0}};
        double[][] translation = {{1,0,0,x}, {0,1,0,y}, {0,0,1,z}, {0,0,0,1}};
        double[][] scale = {{2,0,0,0}, {0,2,0,0}, {0,0,2,0}, {0,0,0,1}};
        Matrix m = new Matrix(pt);
        Matrix translate = new Matrix(translation);
        Matrix mscale = new Matrix(scale);
        System.out.println(translate.mult(m).transpose());
        System.out.println(mscale.mult(m).transpose());
    }
}
