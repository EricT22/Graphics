package Final;

import java.io.IOException;

import Color.*;
import Common.ReadWriteImage;
import Scene.SceneObject;
import Vector.*;

public class stlTest {
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][1024][1024];
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        SceneObject s = new SceneObject("Graphics/res/cube-ascii.stl");
        
        s.translate(new Vector(512, 512, 100, null));
        s.scale(new Vector(100, 100, 100, null));

        s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
        
        try{
            ReadWriteImage.writeImage(framebuffer, "cube.PNG");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
