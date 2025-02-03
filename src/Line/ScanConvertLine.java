package Line;

import java.awt.Color;

public class ScanConvertLine extends ScanConvertAbstract{

    @Override
    public void digitaldifferentialanalyzer(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }


        try {
            if (x1 - x0 == 0){
                if (y0 > y1) {
                    int tempX = x0;
                    int tempY = y0;
        
                    x0 = x1;
                    y0 = y1;
                    x1 = tempX;
                    y1 = tempY;
                }

                for (int y = y0; y <= y1; y++){
                    writePixel(x0, y, framebuffer, new Color(255, 255, 255));
                }
            } else {
                double m = (double)(y1 - y0) / (x1 - x0);
                
                if ((0 <= m && m <= 1) || (-1 <= m && m <= 0)){
                    if (x0 > x1) {
                        int tempX = x0;
                        int tempY = y0;
            
                        x0 = x1;
                        y0 = y1;
                        x1 = tempX;
                        y1 = tempY;
                    }

                    double y = y0;
                    for (int x = x0; x <= x1; x++){
                        writePixel(x, (int)Math.round(y), framebuffer, new Color(255, 255, 255));
                        y += m;
                    }
                } else if (m > 1 || m < -1){
                    if (y0 > y1) {
                        int tempX = x0;
                        int tempY = y0;
            
                        x0 = x1;
                        y0 = y1;
                        x1 = tempX;
                        y1 = tempY;
                    }
                    
                    double x = x0;
                    for (int y = y0; y <= y1; y++){
                        writePixel((int)Math.round(x), y, framebuffer, new Color(255, 255, 255));
                        x += (1/m); // have to put slope in terms of x
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }


        try {
            if (Math.abs(y1 - y0) < Math.abs(x1 - x0)){
                if (x0 > x1) {
                    bresenhamSlopeLT45(x1, y1, x0, y0, framebuffer);
                } else {
                    bresenhamSlopeLT45(x0, y0, x1, y1, framebuffer);
                }
            } else {
                if (y0 > y1) {
                    bresenhamSlopeGTE45(x1, y1, x0, y0, framebuffer);
                } else {
                    bresenhamSlopeGTE45(x0, y0, x1, y1, framebuffer);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
    }


    // -1 < m < 1
    private void bresenhamSlopeLT45(int x0, int y0, int x1, int y1, int[][][] framebuffer){
        int dx = x1 - x0;
        int dy = y1 - y0;
        int y = y0;
        
        int inc = 1;
        if (dy < 0){
            inc = -1;
            dy = -dy;
        }
        int d = (2 * dy) + dx;

        for (int x = x0; x <= x1; x++){
            try {    
                writePixel(x, y, framebuffer, new Color(255, 255, 255));
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (d > 0) {
                y += inc;
                d += (2 * (dy - dx));
            } else {
                d += (2 * dy);
            }
        }
    }


    // m >=1 || m <= -1
    private void bresenhamSlopeGTE45(int x0, int y0, int x1, int y1, int[][][] framebuffer) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int x = x0;
        
        int inc = 1;
        if (dx < 0){
            inc = -1;
            dx = -dx;
        }
        int d = (2 * dx) + dy;

        for (int y = y0; y <= y1; y++){
            try {    
                writePixel(x, y, framebuffer, new Color(255, 255, 255));
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (d > 0) {
                x += inc;
                d += (2 * (dx - dy));
            } else {
                d += (2 * dx);
            }
        }
    }



    private void writePixel(int x, int y, int[][][] framebuffer, Color color){
        framebuffer[0][y][x] = color.getRed();
        framebuffer[1][y][x] = color.getBlue();
        framebuffer[2][y][x] = color.getGreen();
    }
}