package Line;

import java.awt.Color;

public class Lines extends LineAbstract {

    private final double PARAMETRIC_INC = 1.0 / 2000.0;


    @Override
    public void twoPointForm(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }

        try {
            if (x1 - x0 == 0){
                for (int y = y0; y <= y1; y++){
                    writePixel(x0, y, framebuffer, new Color(255, 255, 255));
                }
            } else {
                double m = (double)(y1 - y0) / (x1 - x0);
                
                if ((0 <= m && m <= 1) || (-1 <= m && m <= 0)){
                    double y = y0;
                    for (int x = x0; x <= x1; x++){
                        writePixel(x, (int)Math.round(y), framebuffer, new Color(255, 255, 255));
                        y += m;
                    }
                } else if (m > 1 || m < -1){
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
    public void parametricForm(int x0, int y0, int x1, int y1, int[][][] framebuffer)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }

        try {
            for (double t = 0.0; t <= 1.0; t += PARAMETRIC_INC) {
                double x = x0 + (x1 - x0) * t;
                double y = y0 + (y1 - y0) * t;

                writePixel((int)Math.round(x), (int)Math.round(y), framebuffer, new Color(255, 255, 255));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }


    private void writePixel(int x, int y, int[][][] framebuffer, Color color){
        framebuffer[0][y][x] = color.getRed();
        framebuffer[1][y][x] = color.getBlue();
        framebuffer[2][y][x] = color.getGreen();
    }
    
}
