// LICENSE FOR STL FILE
// This thing was created by Thingiverse user WiZE_3D, and is licensed under Creative Commons - Attribution - Share Alike
// TORUS HALF -TOROID by WiZE_3D on Thingiverse: https://www.thingiverse.com/thing:670825

package Scene;

import java.io.IOException;

import Color.Color;
import Common.ReadWriteImage;
import Vector.*;

public class testingWithTorus {
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][512][512];
		VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));

        SceneObject s = new SceneObject("Graphics/res/torus_half.stl");
        s.translate(new Vector(256, 256, 256, null));
        s.scale(new Vector(500, 500, 500, null));

        
        try {
            for (int i = 0; i < 360; i += 18){
                s.rotate(new Vector(0, 1, 1, null), i);    
                s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
                int counter = (int)(i / 180.0 % 10 * 10);

                if (counter < 10){
                    ReadWriteImage.writeImage(framebuffer, "torus0" + counter + ".PNG");
                } else {
                    ReadWriteImage.writeImage(framebuffer, "torus" + counter + ".PNG");
                }
                framebuffer = new int[3][512][512];
            }
		} catch (IOException e) {
			System.out.println(e);
		}
    }
}