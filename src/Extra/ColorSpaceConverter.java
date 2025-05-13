package Extra;

public class ColorSpaceConverter {

	/**
	 * RGB to YPbPr converter
	 * @param _rgb RGB in range [0..255]
	 * @return YPbPr in range [0..255]
	 */
	public static int[][][] RGB2YPbPr (int[][][] _rgb)
	{
		int ypbpr[][][] = new int[3][_rgb[0].length][_rgb[0][0].length];
		
		double matrix[][] = {{ 0.299,  0.587,  0.114}, 
							 {-0.168, -0.331,  0.500},
							 { 0.500, -0.418, -0.081}
		};

		for (int i = 0; i < _rgb[0].length; ++i) {
			for (int j = 0; j < _rgb[0][i].length; ++j) {
				double y  = matrix[0][0] * _rgb[0][i][j] + matrix[0][1] * _rgb[1][i][j] + matrix[0][2] * _rgb[2][i][j];
				double pb = matrix[1][0] * _rgb[0][i][j] + matrix[1][1] * _rgb[1][i][j] + matrix[1][2] * _rgb[2][i][j];
				double pr = matrix[2][0] * _rgb[0][i][j] + matrix[2][1] * _rgb[1][i][j] + matrix[2][2] * _rgb[2][i][j];
				ypbpr[0][i][j] = (int)y;
				ypbpr[1][i][j] = (int)(pb + 128);
				ypbpr[2][i][j] = (int)(pr + 128);
			}
		}
		return ypbpr;		
	}

	public static int[][][] YPbPr2RGB (int[][][] _ypbpr)
	{
		int rgb[][][] = new int[3][_ypbpr[0].length][_ypbpr[0][0].length];
		
		double matrix[][] = {{ 1.000,  0.000,  1.402}, 
							 { 1.000, -0.344, -0.714},
							 { 1.000,  1.772,  0.000}
		};

		for (int i = 0; i < _ypbpr[0].length; ++i) {
			for (int j = 0; j < _ypbpr[0][i].length; ++j) {
				int pb = _ypbpr[1][i][j] - 128;
				int pr = _ypbpr[2][i][j] - 128;
				double r = matrix[0][0] * _ypbpr[0][i][j] + matrix[0][1] * pb + matrix[0][2] * pr;
				double g = matrix[1][0] * _ypbpr[0][i][j] + matrix[1][1] * pb + matrix[1][2] * pr;
				double b = matrix[2][0] * _ypbpr[0][i][j] + matrix[2][1] * pb + matrix[2][2] * pr;
				rgb[0][i][j] = (int)r;
				rgb[1][i][j] = (int)g;
				rgb[2][i][j] = (int)b;
			}
		}

		return rgb;		
	}

}
