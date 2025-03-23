package Shader;

import Triangle.*;
import Line.*;
import Vector.*;
import Color.*;

public class Shader extends ShaderAbstract{

    @Override
    public void solidFill(TriangleAbstract tri, int[][][] framebuffer) {
        ScanConvertLine sc = new ScanConvertLine();
        VectorAbstract[] vertices = tri.getVertices();
        Color fillColor = vertices[0].getColor();
        int[][][] sb = initShadeBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);

        // render triangle on shade buffer
        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[j].getX(), (int)vertices[j].getY(),
                            fillColor, fillColor, 
                            sb);        
            } catch (IndexOutOfBoundsException e) {
                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[0].getX(), (int)vertices[0].getY(),
                            fillColor, fillColor, 
                            sb);    
            }
        }

        // fill on shadebuffer
        for (int i = 0; i < sb[0].length; i++){
            int left = 0;
            int right = sb[0][0].length;

            try {
                while (sb[0][i][left] == -1){
                    left++;
                }

                while (sb[0][i][right] == -1){
                    right--;
                }
            } catch (IndexOutOfBoundsException e){
                continue;
            }

            if (left > right) { continue; }

            sc.bresenham(left, i, right, i, fillColor, fillColor, sb);
        }

        // move contents of shadebuffer over to framebuffer
        sbToFB(sb, framebuffer);
    }

    @Override
    public void shadeFill(TriangleAbstract tri, int[][][] framebuffer) {
        ScanConvertLine sc = new ScanConvertLine();
        VectorAbstract[] vertices = tri.getVertices();
        int[][][] sb = initShadeBuffer(framebuffer.length, framebuffer[0].length, framebuffer[0][0].length);

        // render triangle on shade buffer
        for (int i = 0; i < vertices.length; i++) {
            try {
                int j = i + 1;

                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[j].getX(), (int)vertices[j].getY(),
                            vertices[i].getColor(), vertices[j].getColor(), 
                            framebuffer);        
            } catch (IndexOutOfBoundsException e) {
                sc.bresenham((int)vertices[i].getX(), (int)vertices[i].getY(), 
                            (int)vertices[0].getX(), (int)vertices[0].getY(),
                            vertices[i].getColor(), vertices[0].getColor(), 
                            framebuffer);    
            }
        }

        // fill on shadebuffer
        for (int i = 0; i < sb[0].length; i++){
            int left = 0;
            int right = sb[0][0].length;

            try {
                while (sb[0][i][left] == -1){
                    left++;
                }

                while (sb[0][i][right] == -1){
                    right--;
                }
            } catch (IndexOutOfBoundsException e){
                continue;
            }

            if (left > right) { continue; }

            sc.bresenham(left, i, right, i, 
                        new Color(sb[0][i][left], sb[1][i][left], sb[2][i][left]), 
                        new Color(sb[0][i][right], sb[1][i][right], sb[2][i][right]), 
                        sb);
        }

        // move contents of shadebuffer over to framebuffer
        sbToFB(sb, framebuffer);
    }

    private int[][][] initShadeBuffer(int layers, int rows, int cols){
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

    private void sbToFB(int[][][] sb, int[][][] fb){
        for (int i = 0; i < sb.length; i++){
            for (int j = 0; j < sb[0].length; j++){
                for (int k = 0; k < sb[0].length; k++){
                    if (sb[i][j][k] != -1) {
                        fb[i][j][k] = sb[i][j][k];
                    }
                }
            }
        }
    } 
}
