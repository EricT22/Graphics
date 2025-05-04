package Final;

import Color.Color;
import Scene.SceneObject;
import Vector.*;

public class projectPart2 {
    public static void main(String[] args) {
        int[][][] framebuffer;
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        SceneObject s = new SceneObject();

        s.deserialize("Graphics/res/cube.so");
    }
}
