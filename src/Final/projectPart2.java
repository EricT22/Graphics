package Final;

import java.io.IOException;
import java.util.ArrayList;

import Color.Color;
import Common.ReadWriteImage;
import Scene.SceneObject;
import Vector.*;
import Triangle.*;

public class projectPart2 {
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][1024][1024];
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        SceneObject s = new SceneObject();

        s.deserialize("Graphics/res/cube.so");

        
        // try{
        //     s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
        //     ReadWriteImage.writeImage(framebuffer,  "testing.PNG");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        ArrayList<TriangleAbstract> triangles = s.getTriangles();

        int count = 535;
        String filename = "project"; // = "spread";

        for (int k = 0; k < 2; k++){
            for (int j = 1; j <= 100; j++){
                for (int i = 0; i < triangles.size(); i++){
                    TriangleAbstract t = triangles.get(i);

                    if (!t.isVisible(viewpoint)){
                        t = t.rotateY(Math.toRadians(180), t.getCenter(), t);
                    }

                    VectorAbstract trans = t.getCenter().sub(s.getCenter()).mult(0.01);
                    triangles.set(i, t.translate(trans, t));
                }

                if (j % 4 == 0){
                    try{
                        framebuffer = new int[3][1024][1024];
                        s.rotate(new Vector(0.0, 0.0, 1.0, null), 7.2);
                        s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
                        ReadWriteImage.writeImage(framebuffer,  project.getFilename(count, filename) + ".PNG");
                        count++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int j = 1; j <= 100; j++){
                for (int i = 0; i < triangles.size(); i++){
                    TriangleAbstract t = triangles.get(i);

                    if (!t.isVisible(viewpoint)){
                        t = t.rotateY(Math.toRadians(180), t.getCenter(), t);
                    }

                    t = t.rotateZ(Math.toRadians(3.6), t.getCenter(), t);

                    VectorAbstract trans = s.getCenter().sub(t.getCenter()).mult(0.01);
                    
                    triangles.set(i, t.translate(trans, t));
                }

                if (j % 4 == 0){
                    try{
                        framebuffer = new int[3][1024][1024];
                        s.rotate(new Vector(0.0, 0.0, 1.0, null), 7.2);
                        s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
                        ReadWriteImage.writeImage(framebuffer,  project.getFilename(count, filename) + ".PNG");
                        count++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        s.serialize("Graphics/res/cubeP2.so");
    }
}