package Line;

import Color.*;

public class ScanConvertLine extends ScanConvertAbstract{

    @Deprecated
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
                    writePixel(x0, y, framebuffer, new Color(1.0, 1.0, 1.0));
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
                        writePixel(x, (int)Math.round(y), framebuffer, new Color(1.0, 1.0, 1.0));
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
                        writePixel((int)Math.round(x), y, framebuffer, new Color(1.0, 1.0, 1.0));
                        x += (1/m); // have to put slope in terms of x
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }


        try {
            if (Math.abs(y1 - y0) < Math.abs(x1 - x0)){
                if (x0 > x1) {
                    bresenhamSlopeLT45(x1, y1, x0, y0, c1, c0, framebuffer);
                } else {
                    bresenhamSlopeLT45(x0, y0, x1, y1, c0, c1, framebuffer);
                }
            } else {
                if (y0 > y1) {
                    bresenhamSlopeGTE45(x1, y1, x0, y0, c1, c0, framebuffer);
                } else {
                    bresenhamSlopeGTE45(x0, y0, x1, y1, c0, c1, framebuffer);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
    }


    // -1 < m < 1
    private void bresenhamSlopeLT45(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer){
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
                double multiplier = (double)(x - x0) / (double)(x1 - x0);

                writePixel(x, y, framebuffer, linearlyInterpolate(c0, c1, multiplier));
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
    private void bresenhamSlopeGTE45(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer) {
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
                double multiplier = (double)(y - y0) / (double)(y1 - y0);

                writePixel(x, y, framebuffer, linearlyInterpolate(c0, c1, multiplier));
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (d > 0) {
                x += inc;
                d += (2 * (dx - dy));
            } else {
                d += (2 * dx);
            }
        }
    }



    private void writePixel(int x, int y, int[][][] framebuffer, ColorAbstract color){
        int[] rgb = color.scale(255);

        framebuffer[0][y][x] = rgb[0];
        framebuffer[1][y][x] = rgb[1];
        framebuffer[2][y][x] = rgb[2];
    }

    private ColorAbstract linearlyInterpolate(ColorAbstract c0, ColorAbstract c1, double multiplier){
        double r = c0.getR();
        double g = c0.getG();
        double b = c0.getB();
        
        if (r > c1.getR()){
            r -= (multiplier * (r - c1.getR()));
        } else if (c1.getR() > r){
            r += (multiplier * (c1.getR() - r));
        }

        if (g > c1.getG()){
            g -= (multiplier * (g - c1.getG()));
        } else if (c1.getG() > g){
            g += (multiplier * (c1.getG() - g));
        }
        
        if (b > c1.getB()){
            b -= (multiplier * (b - c1.getB()));
        } else if (c1.getB() > b){
            b += (multiplier * (c1.getB() - b));
        }

        r = (r > 1.0) ? 1.0 : (r < 0.0) ? 0.0 : r;
        g = (g > 1.0) ? 1.0 : (g < 0.0) ? 0.0 : g;
        b = (b > 1.0) ? 1.0 : (b < 0.0) ? 0.0 : b;

        return new Color(r, g, b);
    }
    
}