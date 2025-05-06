package Final;

import java.io.IOException;
import java.util.ArrayList;

import Color.Color;
import Common.ReadWriteImage;
import ProceduralGraphics.Fireworks;
import Scene.SceneObject;
import Triangle.TriangleAbstract;
import Vector.*;

public class projectPart3 {
    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][1024][1024];
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        SceneObject s = new SceneObject();

        s.deserialize("Graphics/res/cubeP2.so");

        ArrayList<TriangleAbstract> triangles = s.getTriangles();

        // for (TriangleAbstract t : triangles){
        //     System.out.println(t.getVertices()[0].getColor());
        // }

        int count = 635;
        String filename = "project";

        // try{
        //     s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
        //     ReadWriteImage.writeImage(framebuffer,  "a.PNG");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        int t1 = 0;
        int t2 = 11;

        for (int j = 1; j <= 100; j++){
                TriangleAbstract t = triangles.get(t1);

                if (!t.isVisible(viewpoint)){
                    t = t.rotateY(Math.toRadians(180), t.getCenter(), t);
                }

            VectorAbstract trans = t.getCenter().sub(s.getCenter()).mult(0.02);
            triangles.set(t1, t.translate(trans, t));

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

        Fireworks f1 = new Fireworks(triangles.get(t1).getCenter(), 100);
        try{
            framebuffer = new int[3][1024][1024];
            TriangleAbstract t = triangles.get(t1);
            t = t.rotateY(Math.toRadians(180), t.getCenter(), t);

            triangles.set(t1, t);
            s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
            ReadWriteImage.writeImage(framebuffer,  project.getFilename(count, filename) + ".PNG");
            count++;

            count = f1.launch(framebuffer, 0, viewpoint, filename, t.getVertices()[0].getColor(), count);
        } catch (IOException e) {
            e.printStackTrace();
        }


        
        for (int j = 1; j <= 100; j++){
            TriangleAbstract t = triangles.get(t2);

            if (!t.isVisible(viewpoint)){
                t = t.rotateY(Math.toRadians(180), t.getCenter(), t);
            }

            VectorAbstract trans = t.getCenter().sub(s.getCenter()).mult(0.02);
            triangles.set(t2, t.translate(trans, t));

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

        Fireworks f2 = new Fireworks(triangles.get(t2).getCenter(), 100);
        try{
            framebuffer = new int[3][1024][1024];
            TriangleAbstract t = triangles.get(t2);
            t = t.rotateY(Math.toRadians(180), t.getCenter(), t);

            triangles.set(t2, t);
            s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
            ReadWriteImage.writeImage(framebuffer,  project.getFilename(count, filename) + ".PNG");
            count++;

            count = f2.launch(framebuffer, 0, viewpoint, filename, t.getVertices()[0].getColor(), count);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Fireworks f3 = new Fireworks(s.getCenter(), 200);
        try{
            framebuffer = new int[3][1024][1024];
            
            for (int i = 0; i < triangles.size(); i++){
                if (i != t1 && i != t2){
                    TriangleAbstract t = triangles.get(i);
                    t = t.rotateY(Math.toRadians(180), t.getCenter(), t);

                    triangles.set(i, t);
                }
            }
            
            s.render(framebuffer, false, Shader.ShaderAbstract.FILLSTYLE.FILL, viewpoint);
            ReadWriteImage.writeImage(framebuffer,  project.getFilename(count, filename) + ".PNG");
            count++;

            count = f3.launch(framebuffer, 0, viewpoint, filename, new Color(235/255.0, 64/255.0, 52/255.0), count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
