package Extra;

import java.io.IOException;

import Common.ReadWriteImage;
import Triangle.*;
import Vector.*;
import Shader.*;
import Color.*;

public class BarycentricMapper {

	private static final int BACKGROUND = -1;

	private static int p0X, p0Y, p0Z;
	private static int p1X, p1Y, p1Z;
	private static int p2X, p2Y, p2Z;
	
	private static int wd, ht;

	private static int[][][] texturebuffer;

	public static void setup(TriangleAbstract ta, int[][][] img, int[][][]mapped) throws IOException {
	
			
			// -- set triangle coordinates
			VectorAbstract[] verts = ta.getVertices();
			p0X = (int)verts[0].getX(); p0Y = (int)verts[0].getY(); p0Z = (int)verts[0].getZ();
			p1X = (int)verts[1].getX(); p1Y = (int)verts[1].getY(); p1Z = (int)verts[1].getZ();
			p2X = (int)verts[2].getX(); p2Y = (int)verts[2].getY(); p2Z = (int)verts[2].getZ();
			
			texturebuffer = new int[3][mapped[0].length][mapped[0][0].length];
			for (int i = 0; i < texturebuffer.length; ++i) {
				for (int j = 0; j < texturebuffer[i].length; ++j) {
					for (int k = 0; k < texturebuffer[i][j].length; ++k) {
						texturebuffer[i][j][k] = BACKGROUND;
					}
				}
			}
			
			ta.render(texturebuffer, false, ShaderAbstract.FILLSTYLE.FILL, new Vector(0, 0, -1, null));
			wd = texturebuffer[0][0].length;
			ht = texturebuffer[0].length;


	}
	
	private static int[] Cross(int x0, int y0, int z0, int x1, int y1, int z1) {
		int x = (y0 * z1) - (y1 * z0);
		int y = (z0 * x1) - (z1 * x0);
		int z = (x0 * y1) - (x1 * y0);
		int[] result = { x, y, z };
		return result;
	}


	/***
	 * 
	 * @param _texture the image to be used as the texture map
	 * @param uuvv3 p3 of the triangle within the texture map
	 * @param uuvv1 p1 of the triangle within the texture map
	 * @param uuvv2 p2 of the triangle within the texture map
	 * @param framebuffer
	 */
	public static void map(int[][][] _texture, VectorAbstract uuvv3, VectorAbstract uuvv1, VectorAbstract uuvv2, 
			               int[][][]framebuffer) {

		int uv3 = (int)uuvv3.getX();
		int vv3 = (int)uuvv3.getY();
		int uv1 = (int)uuvv1.getX();
		int vv1 = (int)uuvv1.getY();
		int uv2 = (int)uuvv2.getX();
		int vv2 = (int)uuvv2.getY();

		int _width = _texture[0][0].length;
		int _height = _texture[0].length;
		int uX, uY, uZ;
		int vX, vY, vZ;
		int wX, wY, wZ;

		// -- set u vector
		uX = p1X - p0X;
		uY = p1Y - p0Y;
		uZ = p1Z - p0Z;

		// -- set v vector
		vX = p2X - p0X;
		vY = p2Y - p0Y;
		vZ = p2Z - p0Z;

		// -- loop through all triangle points calculating barycentric coordinates for each point
		for (int i = 0; i < texturebuffer[0].length; ++i) {
			for (int j = 0; j < texturebuffer[0][0].length; ++j) {

				// -- point is in the triangle (which has been scan converted/filled to the texture buffer)
				if (texturebuffer[0][i][j] != BACKGROUND) { 

					// -- set w vector (from P0 to point in triangle)
					wX = j - p0X;
					wY = i - p0Y;
					wZ = 0 - p0Z;

					// -- map to s, t
					int vwX, vwY, vwZ;
					int[] result;
					result = Cross(vX, vY, vZ, wX, wY, wZ);
					vwX = result[0];
					vwY = result[1];
					vwZ = result[2];

					int uvX, uvY, uvZ;
					result = Cross(uX, uY, uZ, vX, vY, vZ);
					uvX = result[0];
					uvY = result[1];
					uvZ = result[2];

					double s = Math.sqrt((double) ((vwX * vwX) + (vwY * vwY) + (vwZ * vwZ)))
							/ Math.sqrt((double) ((uvX * uvX) + (uvY * uvY) + (uvZ * uvZ)));
					s = (s < 0) ? 0 : (s > 1) ? 1 : s; // -- roundoff may make s go slightly out of range

					int uwX, uwY, uwZ;
					result = Cross(uX, uY, uZ, wX, wY, wZ);
					uwX = result[0];
					uwY = result[1];
					uwZ = result[2];
					double t = Math.sqrt((double) ((uwX * uwX) + (uwY * uwY) + (uwZ * uwZ)))
							/ Math.sqrt((double) ((uvX * uvX) + (uvY * uvY) + (uvZ * uvZ)));
					t = (t < 0) ? 0 : (t > 1) ? 1 : t; // -- roundoff may make t go slightly out of range

					// -- map s, t -> u, v
					double u = (double) (uv1 - uv3) * s + (double) (uv2 - uv3) * t + uv3;
					double v = (double) (vv1 - vv3) * s + (double) (vv2 - vv3) * t + vv3;

					// -- clamp u, v
					int nU = (u < 0) ? 0 : (u > _width - 1) ? _width - 1 : (int) u;
					int nV = (v < 0) ? 0 : (v > _height - 1) ? _height - 1 : (int) v;
					
					// -- map the texture to the frame buffer
					framebuffer[0][i][j] = _texture[0][nV][nU];
					framebuffer[1][i][j] = _texture[1][nV][nU];
					framebuffer[2][i][j] = _texture[2][nV][nU];
					
				}
			}
		}
	}

	
	public static void main(String[] args) {

		try {
			// -- this is the texture map
			int[][][] img = ReadWriteImage.readImage("TREVOR.png");
			
			int[][][] framebuffer = new int[3][img[0].length][img[0][0].length];
			
			VectorAbstract v0 = new Vector(16, 16, 0, new Color(1, 1, 1));
			VectorAbstract v1 = new Vector(116, 96, 0, new Color(1, 1, 1));
			VectorAbstract v2 = new Vector(16, 192, 0, new Color(1, 1, 1));
			TriangleAbstract t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, ht - 1, 0, null),
					new Vector(0, ht - 1, 0, null), framebuffer);

			v0 = new Vector(16, 16, 0, new Color(1, 1, 1));
			v1 = new Vector(116, 48, 0, new Color(1, 1, 1));
			v2 = new Vector(116, 96, 0, new Color(1, 1, 1));
			t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, 0, 0, null),
					new Vector(wd - 1, ht - 1, 0, null), framebuffer);

			v0 = new Vector(140, 48, 0, new Color(1, 1, 1));
			v1 = new Vector(240, 192, 0, new Color(1, 1, 1));
			v2 = new Vector(140, 96, 0, new Color(1, 1, 1));
			t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, ht - 1, 0, null),
					new Vector(0, ht - 1, 0, null), framebuffer);

			v0 = new Vector(140, 48, 0, new Color(1, 1, 1));
			v1 = new Vector(240, 16, 0, new Color(1, 1, 1));
			v2 = new Vector(240, 192, 0, new Color(1, 1, 1));
			t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, 0, 0, null),
					new Vector(wd - 1, ht - 1, 0, null), framebuffer);

			v0 = new Vector(116, 48, 0, new Color(1, 1, 1));
			v1 = new Vector(140, 96, 0, new Color(1, 1, 1));
			v2 = new Vector(116, 96, 0, new Color(1, 1, 1));
			t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, ht - 1, 0, null),
					new Vector(0, ht - 1, 0, null), framebuffer);

			v0 = new Vector(116, 48, 0, new Color(1, 1, 1));
			v1 = new Vector(140, 48, 0, new Color(1, 1, 1));
			v2 = new Vector(140, 96, 0, new Color(1, 1, 1));
			t = new Triangle(v0, v1, v2);
			BarycentricMapper.setup(t, img, framebuffer);
			BarycentricMapper.map(img, new Vector(0, 0, 0, null), new Vector(wd - 1, 0, 0, null),
					new Vector(wd - 1, ht - 1, 0, null), framebuffer);

			ReadWriteImage.writeImage(framebuffer, "barycentric.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
