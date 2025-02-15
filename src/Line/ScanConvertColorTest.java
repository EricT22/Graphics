package Line;

import Common.ReadWriteImage;
import Color.*;

public class ScanConvertColorTest {
	public static void main (String[] args) {
		ScanConvertAbstract sc = new ScanConvertLine();
		ColorAbstract c0 = new Color(1.0, 0.0, 0.0);
		ColorAbstract c1 = new Color(0.0, 1.0, 0.0);
		int anglesteps = 32;

		{
			int framebuffer[][][] = new int[3][256][256];
			try {
				for (int x = 0; x < framebuffer[0][0].length; x += anglesteps) {
					sc.bresenham(x, 0, framebuffer[0][0].length - x - 1, framebuffer[0].length - 1, c0, c1, framebuffer);
				}
				for (int y = 0; y < framebuffer[0].length; y += anglesteps) {
					sc.bresenham(0, y, framebuffer[0][0].length - 1, framebuffer[0].length - y - 1, c0, c1, framebuffer);
				}
				ReadWriteImage.writeImage(framebuffer, "bresenhamcolor.png");
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}	
	}

}
