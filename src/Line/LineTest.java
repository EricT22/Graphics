package Line;

import Common.ReadWriteImage;

public class LineTest {
	public static void main (String[] args) {
		LineAbstract lb = new Lines();
		int anglesteps = 32;
		
		{
			int framebuffer[][][] = new int[3][256][256];
			try {
				for (int x = 0; x < framebuffer[0][0].length; x += anglesteps) {
					lb.twoPointForm(x, 0, framebuffer[0][0].length - x - 1, framebuffer[0].length - 1, framebuffer);
				}
				for (int y = 0; y < framebuffer[0].length; y += anglesteps) {
					lb.twoPointForm(0, y, framebuffer[0][0].length - 1, framebuffer[0].length - y - 1, framebuffer);
				}
				ReadWriteImage.writeImage(framebuffer, "twopoint.png");
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
		{
			int framebuffer[][][] = new int[3][256][256];
			try {
				for (int x = 0; x < framebuffer[0][0].length; x += anglesteps) {
					lb.parametricForm(x, 0, framebuffer[0][0].length - x - 1, framebuffer[0].length - 1, framebuffer);
				}
				for (int y = 0; y < framebuffer[0].length; y += anglesteps) {
					lb.parametricForm(0, y, framebuffer[0][0].length - 1, framebuffer[0].length - y - 1, framebuffer);
				}
				ReadWriteImage.writeImage(framebuffer, "parametric.png");
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}		
	}

}
