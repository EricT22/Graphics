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
        int[][][] copy = copyFrameBuffer(framebuffer);
        int[][][] fireworksBuffer = initFireworksBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);
        try {
            ReadWriteImage.writeImage(framebuffer, filename + "00.PNG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int counter = 1;
        for (int i = 1; i <= 200; i++){

            for (Particle p : particles){
                p.update();
            }
            
            if (i % 4 == 0){
                fireworksBuffer = initFireworksBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);
                framebuffer = copyFrameBuffer(copy);
                for (Particle p : particles){
                    p.render(fireworksBuffer, viewpoint);
                }
                fireworksBufferToFrameBuffer(fireworksBuffer, framebuffer);

                try {
                    ReadWriteImage.writeImage(framebuffer, Final.project.getFilename(counter, "spread") + ".PNG");
                    counter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        framebuffer = copyFrameBuffer(copy);
        try {
            ReadWriteImage.writeImage(framebuffer, Final.project.getFilename(counter, "spread") + ".PNG");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][][] initFireworksBuffer(int layers, int rows, int cols){
        int[][][] shadeBuffer = new int[layers][rows][cols];

        for (int i = 0; i < layers; i++){
            for (int j = 0; j < rows; j++){
                for (int k = 0; k < cols; k++){
                    shadeBuffer[i][j][k] = -1;
                }
            }
        }

        return shadeBuffer;
    }

    private void fireworksBufferToFrameBuffer(int[][][] fireworks, int[][][] frame){
        for (int i = 0; i < fireworks.length; i++){
            for (int j = 0; j < fireworks[0].length; j++){
                for (int k = 0; k < fireworks[0].length; k++){
                    if (fireworks[i][j][k] != -1) {
                        frame[i][j][k] = fireworks[i][j][k];
                    }
                }
            }
        }
    } 

    private int[][][] copyFrameBuffer(int[][][] framebuffer){
        int[][][] copy = new int[framebuffer.length][framebuffer[0].length][framebuffer[0][0].length];

        for (int i = 0; i < framebuffer.length; i++){
            for (int j = 0; j < framebuffer[0].length; j++){
                for (int k = 0; k < framebuffer[0].length; k++){
                        copy[i][j][k] = framebuffer[i][j][k];
                }
            }
        }

        return copy;
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
