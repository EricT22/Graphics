package Line;

/**
 * Line drawing into a frame buffer
 */
public abstract class LineAbstract {
	/**
	 * Draw a line segment using the two point formula
	 * @param x0 Starting x coordinate
	 * @param y0 Starting y coordinate
	 * @param x1 Ending x coordinate
	 * @param y1 Ending y coordinate
	 * @param framebuffer 3D frame buffer to receive the line
	 * @throws NullPointerException thrown if the frame buffer is null
	 * @throws ArrayIndexOutOfBoundsException Caught but never actually thrown (commented out)
	 */
	public abstract void twoPointForm(int x0, int y0, int x1, int y1, int framebuffer[][][]) throws NullPointerException, ArrayIndexOutOfBoundsException;

	/**
	 * Draw a line segment using the parametric formula
	 * @param x0 Starting x coordinate
	 * @param y0 Starting y coordinate
	 * @param x1 Ending x coordinate
	 * @param y1 Ending y coordinate
	 * @param framebuffer 3D frame buffer to receive the line
	 * @throws NullPointerException thrown if the frame buffer is null
	 * @throws ArrayIndexOutOfBoundsException Caught but never actually thrown (commented out)
	 */	public abstract void parametricForm(int x0, int y0, int x1, int y1, int framebuffer[][][]) throws NullPointerException, ArrayIndexOutOfBoundsException;


}
