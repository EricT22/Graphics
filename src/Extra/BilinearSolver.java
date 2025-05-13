package Extra;

import java.awt.Point;
import java.io.IOException;

import Common.ReadWriteImage;
import Color.*;
import Line.*;

public class BilinearSolver {
	
	private static final int BACKGROUND = -1;

	double xXform[], yXform[]; // -- screen image to texture image mapping
								// matrices

	void TransformPoint(int _x, int _y, Point _prime) {
		double xPrime = xXform[0] * _x + xXform[1] * _y + xXform[2] * _x * _y  + xXform[3];
		double yPrime = yXform[0] * _x + yXform[1] * _y + yXform[2] * _x * _y+ yXform[3];

		_prime.x = (int) xPrime;
		_prime.y = (int) yPrime;
	}


	void ComputeTransform(int _from[][], int _to[][]) // _from[4][2] _to[4][2]
	{
		// -- allocate memory for matrix of equation coefficients
		double equations[][] = new double[4][4];

		// c1*x + c2*y + c3*x*y + c4
		// c5*x + c6*y + c7*x*y + c8
		// -- set up left hand side of equations
		equations[0][0] = _from[0][0];
		equations[0][1] = _from[0][1];
		equations[0][2] = _from[0][0] * _from[0][1];
		equations[0][3] = 1;

		equations[1][0] = _from[1][0];
		equations[1][1] = _from[1][1];
		equations[1][2] = _from[1][0] * _from[1][1];
		equations[1][3] = 1;

		equations[2][0] = _from[2][0];
		equations[2][1] = _from[2][1];
		equations[2][2] = _from[2][0] * _from[2][1];
		equations[2][3] = 1;

		equations[3][0] = _from[3][0];
		equations[3][1] = _from[3][1];
		equations[3][2] = _from[3][0] * _from[3][1];
		equations[3][3] = 1;

		// -- solve equations for x
		// -- set up right hand side of equations
		double xPrime[] = new double[4];
		xPrime[0] = _to[0][0];
		xPrime[1] = _to[1][0];
		xPrime[2] = _to[2][0];
		xPrime[3] = _to[3][0];

		// -- solve equations for c1, c2, c3, and c4 (X)
		xXform = LUDSolver(equations, xPrime, 4);

		// -- solve equations for y;
		// -- set up right hand side of equations
		double yPrime[] = new double[4];
		yPrime[0] = _to[0][1];
		yPrime[1] = _to[1][1];
		yPrime[2] = _to[2][1];
		yPrime[3] = _to[3][1];

		// -- solv equations for c5, c6, c7, and c8 (Y)
		yXform = LUDSolver(equations, yPrime, 4);

	}

	void LUDecomp(double a[][], int n, int indx[], double d[]) // d[1]
	{
		int i, imax = 0, j, k;
		double big, dum, sum, temp;
		double vv[];

		vv = new double[n];
		d[0] = 1.0;
		for (i = 0; i < n; i++) {
			big = 0.0;
			for (j = 0; j < n; j++) {
				if ((temp = Math.abs(a[i][j])) > big) {
					big = temp;
				}
			}
			if (big == 0.0) {
				System.out.println("Singular matrix in routine LUDecomp");
				return;
			}
			vv[i] = 1.0 / big;
		}
		for (j = 0; j < n; j++) {
			for (i = 0; i < j; i++) {
				sum = a[i][j];
				for (k = 0; k < i; k++) {
					sum -= a[i][k] * a[k][j];
				}
				a[i][j] = sum;
			}
			big = 0.0;
			for (i = j; i < n; i++) {
				sum = a[i][j];
				for (k = 0; k < j; k++)
					sum -= a[i][k] * a[k][j];
				a[i][j] = sum;
				if ((dum = vv[i] * Math.abs(sum)) >= big) {
					big = dum;
					imax = i;
				}
			}
			if (j != imax) {
				for (k = 0; k < n; k++) {
					dum = a[imax][k];
					a[imax][k] = a[j][k];
					a[j][k] = dum;
				}
				d[0] = -(d[0]);
				vv[imax] = vv[j];
			}
			indx[j] = imax;
			if (a[j][j] == 0.0) {
				a[j][j] = 1.0E-20;
			}
			if (j != n - 1) {
				dum = 1.0 / (a[j][j]);
				for (i = j + 1; i < n; i++) {
					a[i][j] *= dum;
				}
			}
		}
	}

	void LUBackSub(double a[][], int n, int indx[], double b[]) {
		int i, ii = -1, ip, j;
		double sum = 0;

		for (i = 0; i < n; i++) {
			ip = indx[i];
			sum = b[ip];
			b[ip] = b[i];
			if (ii != -1) {
				for (j = ii; j <= i - 1; j++) {
					sum -= a[i][j] * b[j];
				}
			} else if (Math.abs(sum) > 0.0001) {
				ii = i;
			}
			b[i] = sum;
		}
		for (i = n - 1; i >= 0; i--) {
			sum = b[i];
			for (j = i + 1; j < n; j++) {
				sum -= a[i][j] * b[j];
			}
			b[i] = sum / a[i][i];
		}
	}

	double[] LUDSolver(double _lhs[][], double _rhs[], int _size) 
	{
		int i;
		double p[] = new double[1];
		int indx[];
		double solution[];

		// -- assumes that _lhs is a square matrix and that _rhs
		// has as many rows as _lhs has columns

		// -- create copy of equations matrix so as
		// to not overwrite the original
		double temp[][] = new double[_size][_size];
		for (i = 0; i < _size; ++i) {
			for (int j = 0; j < _size; ++j) {
				temp[i][j] = _lhs[i][j];
			}
		}

		// -- allocate the solution vector (starts out
		// holding the right hand side of the equations
		// then is overwritten with the solution)
		solution = new double[_size];
		for (i = 0; i < _size; ++i) {
			solution[i] = _rhs[i];
		}

		indx = new int[_size];

		// Solve equations
		LUDecomp(temp, _size, indx, p);
		LUBackSub(temp, _size, indx, solution);

		return solution;
	}
	
	public static void map(int inputimg[][][], int src[][], int dst[][], int[][][] img)
	{		
		BilinearSolver bis = new BilinearSolver();
				
		bis.ComputeTransform(src, dst);
		
		for (int i = 0; i < src.length; ++i) {
			Point p = new Point(0, 0);
			bis.TransformPoint(src[i][0], src[i][1], p);
		}
		
		int[][][] texturebuffer = new int[3][inputimg[0].length][inputimg[0][0].length];
		for (int i = 0; i < texturebuffer.length; ++i) {
			for (int j = 0; j < texturebuffer[i].length; ++j) {
				for (int k = 0; k < texturebuffer[i][j].length; ++k) {
					texturebuffer[i][j][k] = BACKGROUND;
				}
			}
		}
		
		// -- scan convert the source (actually, the destination but this is a reverse mapping)
		//    quadrilateral into the texture buffer so the interior points can be easily identified
		ScanConvertAbstract scl = new ScanConvertLine();
		scl.bresenham(src[0][0], src[0][1], src[1][0], src[1][1], new Color(1, 1, 1), new Color(1, 1, 1), texturebuffer);
		scl.bresenham(src[1][0], src[1][1], src[2][0], src[2][1], new Color(1, 1, 1), new Color(1, 1, 1), texturebuffer);
		scl.bresenham(src[2][0], src[2][1], src[3][0], src[3][1], new Color(1, 1, 1), new Color(1, 1, 1), texturebuffer);
		scl.bresenham(src[3][0], src[3][1], src[0][0], src[0][1], new Color(1, 1, 1), new Color(1, 1, 1), texturebuffer);

		try {
			// -- save the scan converted quadrilateral for demonstration purposes
			for (int i = 0; i < texturebuffer.length; ++i) {
				for (int j = 0; j < texturebuffer[i].length; ++j) {
					for (int k = 0; k < texturebuffer[i][j].length; ++k) {
						texturebuffer[i][j][k] = texturebuffer[i][j][k] == BACKGROUND ? 0 : texturebuffer[i][j][k];
					}
				}
			}
			ReadWriteImage.writeImage(texturebuffer,  "texturebuffer.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// -- find all points in the scan converted source quadrilateral and reverse map to destination
		for (int i = 0; i < texturebuffer[0].length; ++i) {
			int jleft = 0;
			while (jleft < texturebuffer[0][i].length && texturebuffer[0][i][jleft] == BACKGROUND) {
				++jleft;
			}
			int jright = texturebuffer[0][i].length - 1;
			while (jright >= 0 && texturebuffer[0][i][jright] == BACKGROUND) {
				--jright;
			}
			for (int j = jleft; j <= jright; ++j) {
				Point p = new Point();
				bis.TransformPoint(j, i, p);

				if (p.x > 0 && p.x < inputimg[0][0].length - 1 && p.y >= 0 && p.y < inputimg[0].length - 1) {
					img[0][i][j] = inputimg[0][p.y][p.x];
					img[1][i][j] = inputimg[1][p.y][p.x];
					img[2][i][j] = inputimg[2][p.y][p.x];
				}

			}
		}


	}
	
	
	public static void main (String[] args)
	{
		
		int inputimg[][][] = null;
		try {
			inputimg = ReadWriteImage.readImage("rose.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BilinearSolver bis = new BilinearSolver();
		int src[][] = new int[4][2];
		int dst[][] = new int[4][2];
		
		int srcheight = inputimg[0].length;
		int srcwidth = inputimg[0][0].length;

		// -- destination shape. Called "src" because it's a reverse mapping
		//   column (x)                      row (y)
//		src[0][0] = 0;              src[0][1] = srcheight / 2;      // -- upper left
//		src[1][0] = srcwidth - 1;   src[1][1] = 0;                  // -- upper right
//		src[2][0] = srcwidth - 1;   src[2][1] = srcheight - 1;      // -- lower right
//		src[3][0] = 0;              src[3][1] = 3 * srcheight / 2;  // -- lower left
		src[0][0] = 0;              src[0][1] = 0;      // -- upper left
		src[1][0] = srcwidth - 1;   src[1][1] = 3 * (srcheight - 1) / 8;                  // -- upper right
		src[2][0] = srcwidth - 1;   src[2][1] = 5 * (srcheight - 1) / 8;      // -- lower right
		src[3][0] = 0;              src[3][1] = srcheight - 1;  // -- lower left

		dst[0][0] = 0;            dst[0][1] = 0;             // -- upper left
		dst[1][0] = srcwidth - 1; dst[1][1] = 0;             // -- upper right
		dst[2][0] = srcwidth - 1; dst[2][1] = srcheight - 1; // -- lower right
		dst[3][0] = 0;            dst[3][1] = srcheight - 1; // -- lower left
		
		int[][][] img = new int[3][inputimg[0].length][inputimg[0][0].length];

		BilinearSolver.map(inputimg, src, dst, img);
	
		try {
			ReadWriteImage.writeImage(img,  "bilinear.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 		
		
	}

}
