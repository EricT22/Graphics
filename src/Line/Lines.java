package Line;

import java.awt.Color;
// import java.io.IOException;

// import Common.ReadWriteImage;

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

    // public static void main(String[] args) {
    //     LineAbstract lb = new Lines();
		
	// 	{
	// 		int framebuffer[][][] = new int[3][256][256];
    //         // x0 > x1
    //         lb.twoPointForm(255, 0, 0, 255, framebuffer);

    //         // y0 > y1 
    //         lb.twoPointForm(0, framebuffer[0].length - 1, 255, 127, framebuffer);

    //         // x0 > x1 and y0 > y1
    //         lb.twoPointForm(255, 255, 0, 0, framebuffer);
            
    //         try {
    //             ReadWriteImage.writeImage(framebuffer, "twopointlinetest.png");
    //         } catch (IOException e) {

    //         }
    //     }
    // }
    
}
