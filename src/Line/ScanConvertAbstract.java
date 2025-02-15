package Line;

import Color.*;

public abstract class ScanConvertAbstract {
	/**
	 * Scan convert a line segment using the Digital Differential Analyzer algorithm
	 * @param x0 starting x coordinate
	 * @param y0 starting y coordinate
	 * @param x1 ending x coordinate
	 * @param y1 ending y coordinate
	 * @param framebuffer frame buffer to receive the scan conversion
	 * @throws NullPointerException thrown if the frame buffer is null
	 * @throws ArrayIndexOutOfBoundsException thrown if a coordinate goes out of range of the frame buffer
	 */
	public abstract void digitaldifferentialanalyzer(int x0, int y0, int x1, int y1, int framebuffer[][][]) throws NullPointerException, ArrayIndexOutOfBoundsException;
		
	/**
	 * Scan convert a line segment using Bresenham's algorithm 
	 * with start to end color interpolation
	 * @param x0 starting x coordinate
	 * @param y0 starting y coordinate
	 * @param x1 ending x coordinate
	 * @param y1 ending y coordinate
	 * @param c0 starting color
	 * @param c1 ending color
	 * @param framebuffer frame buffer to receive the scan conversion
	 * @throws NullPointerException thrown if the frame buffer is null
	 * @throws ArrayIndexOutOfBoundsException thrown if a coordinate goes out of range of the frame buffer
	 */	public abstract void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int framebuffer[][][])  throws NullPointerException, ArrayIndexOutOfBoundsException;


}
