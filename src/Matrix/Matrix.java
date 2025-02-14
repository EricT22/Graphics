package Matrix;

public class Matrix extends MatrixAbstract{

    public Matrix(double[][] arr){
        m = arr;
    }

    @Override
    public MatrixAbstract transpose(){
        double[][] t = new double[this.m[0].length][this.m.length];

        for (int i = 0; i < t.length; i++){
            for (int j = 0; j < t[0].length; j++){
                t[i][j] = this.m[j][i];
            }
        }

        return new Matrix(t);
    }

    @Override
    public MatrixAbstract scale(double scale){
        double[][] s = new double[this.m.length][this.m[0].length];

        for (int i = 0; i < s.length; i++){
            for (int j = 0; j < s[0].length; j++){
                s[i][j] = m[i][j] * scale;
            }
        }

        return new Matrix(s);
    }

    @Override
    public MatrixAbstract mult(MatrixAbstract b) throws IllegalArgumentException{
        int rowsa = this.m.length;
        int colsa = this.m[0].length;
        int rowsb = b.m.length;
        int colsb = b.m[0].length;

        if (colsa != rowsb){
            throw new IllegalArgumentException("Matricies are not compatible for multiplication.");
        }

        double[][] result = new double[rowsa][colsb];
        
        for (int i = 0; i < rowsa; i++){
            for (int j = 0; j < colsb; j++){
                for (int k = 0; k < colsa /* or rowsb*/; k++){
                    result[i][j] += this.m[i][k] * b.m[k][j];
                }
            }
        }

        return new Matrix(result);
    }

    @Override
    public MatrixAbstract invert() throws ArithmeticException, IllegalArgumentException{
        int N = this.m.length;

        if (N != this.m[0].length){
            throw new IllegalArgumentException("Must be a square matrix");
        }

        double[][] mat = copyMatrix();
        double[][] imat = generateImat();

        for (int i = 0; i < N; i++){
            boolean hitBreak = false;
            for (int j = i; j < N; j++){
                if (Math.abs(mat[j][i]) > (1.0 / PRECISION)){
                    double[] tempRow = mat[j];
                    mat[j] = mat[i];
                    mat[i] = tempRow;
                    hitBreak = true;
                    break;
                }
            }

            if (!hitBreak) {
                throw new ArithmeticException("This is a singluar matrix.");
            }

            double divisor = mat[i][i];
            for (int j = 0; j < N; j++){
                mat[i][j] /= divisor;

                imat[i][j] /= divisor;
            }

            for (int j = 0; j < N; j++){
                if (j != i){
                    double multiplier = mat[j][i];

                    for (int k = 0; k < N; k++){
                        mat[j][k] -= multiplier * mat[i][k];

                        imat[j][k] -= multiplier * imat[i][k];
                    }
                }
            }
        }

        return new Matrix(imat);
    }


    private double[][] generateImat(){
        double[][] imat = new double[this.m.length][this.m[0].length];

        for (int i = 0; i < imat.length; i++){
            for (int j = 0; j < imat[0].length; j++){
                if (i == j){
                    imat[i][j] = 1.0;
                } else {
                    imat[i][j] = 0.0;
                }
            }
        }

        return imat;
    }

    private double[][] copyMatrix(){
        double[][] copy = new double[this.m.length][this.m[0].length];

        for (int i = 0; i < this.m.length; i++){
            for (int j = 0; j < this.m[0].length; j++){
                copy[i][j] = this.m[i][j];
            }
        }

        return copy;
    }


    public static void main(String[] args) {
        double[][] test = {{1, 2, 3},
                           {4, 7, 6},
                           {7, 10, 9}};

        Matrix a = new Matrix(test);

        System.out.println(a.scale(10));
        System.out.println(a.invert());
    }
}
