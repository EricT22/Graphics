package Triangle;

import Common.ReadWriteImage;
import Vector.*;

public class GraphicsPipelineTest {
	public static void main(String[] args) 	{
		int framebuffer[][][] = new int[3][256][256];
		VectorAbstract v0, v1, v2;
		TriangleAbstract t;
		try {
			int scalefactor = 100;
			VectorAbstract offset = new Vector(78, 78, 78);
			
			v0 = new Vector(0.0, 0.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 0.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 1.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);
			
			v0 = new Vector(1.0, 1.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 1.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 0.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(1.0, 0.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 0.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 1.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(1.0, 1.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 1.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 0.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);
			
			
			v0 = new Vector(1.0, 0.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 0.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 1.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(0.0, 1.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 1.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 0.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(0.0, 0.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 0.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 1.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(0.0, 1.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 1.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 0.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(0.0, 1.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 1.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 1.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(1.0, 1.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 1.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 1.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(1.0, 0.0, 1.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(0.0, 0.0, 1.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(1.0, 0.0, 0.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			v0 = new Vector(0.0, 0.0, 0.0);
			v0 = v0.mult(scalefactor);
			v0 = v0.add(offset);
			v1 = new Vector(1.0, 0.0, 0.0);
			v1 = v1.mult(scalefactor);
			v1 = v1.add(offset);
			v2 = new Vector(0.0, 0.0, 1.0);
			v2 = v2.mult(scalefactor);
			v2 = v2.add(offset);
			t = new Triangle(v0, v1, v2);
			t.render(framebuffer, true);

			ReadWriteImage.writeImage(framebuffer, "triangles.png");
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}

}
