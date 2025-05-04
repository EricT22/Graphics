package ProceduralGraphics;

import java.io.IOException;
import java.util.Random;

import Color.Color;
import Color.ColorAbstract;
import Common.ReadWriteImage;
import Vector.*;

public class Fireworks {
    
    private VectorAbstract explodingPoint;
    private Particle[] particles;

    public Fireworks(VectorAbstract explodingPoint, int numParticles){
        this.particles = new Particle[numParticles];

        this.explodingPoint = explodingPoint;
    }

    public void launch(int[][][] framebuffer, long seed, VectorAbstract viewpoint, String filename, ColorAbstract c){
        Random r = new Random(seed);

        for (int i = 0; i < particles.length; i++){
            Vector v = new Vector((r.nextDouble() - 0.5) * 10,
                                  (r.nextDouble() - 0.5) * 10, 
                                  (r.nextDouble() - 0.5) * 10, 
                                  explodingPoint.getColor());

            particles[i] = new Particle(((Vector)explodingPoint).copy(), v, c);
        }

        generateImages(framebuffer, viewpoint, filename);
    }

    private void generateImages(int[][][] framebuffer, VectorAbstract viewpoint, String filename) {
        framebuffer = new int[framebuffer.length][framebuffer[0].length][framebuffer[0][0].length];
        try {
            ReadWriteImage.writeImage(framebuffer, filename + "00.PNG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int counter = 1;
        for (int i = 1; i <= 200; i++){

            framebuffer = new int[framebuffer.length][framebuffer[0].length][framebuffer[0][0].length];

            for (Particle p : particles){
                p.update();
                p.render(framebuffer, viewpoint);
            }
            if (i % 4 == 0){
                try {
                    if (counter < 10){
                        ReadWriteImage.writeImage(framebuffer, filename + "0" + counter + ".PNG");
                    } else {
                        ReadWriteImage.writeImage(framebuffer, filename + counter + ".PNG");
                    }
                    counter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        int[][][] framebuffer = new int[3][1024][1024];
        VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
        Color c = new Color(1, 0, 0);

        VectorAbstract ep = new Vector(512, 512, 512, c);

        Fireworks f = new Fireworks(ep, 100);
        f.launch(framebuffer, 0, viewpoint, "fireworks", c);
    }
}
