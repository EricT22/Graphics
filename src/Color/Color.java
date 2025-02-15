package Color;

public class Color extends ColorAbstract{

    public Color(double r, double g, double b) throws IllegalArgumentException{
        if ((r < 0.0 || r > 1.0) || (g < 0.0 || g > 1.0) || (b < 0.0 || b > 1.0)){
            throw new IllegalArgumentException("Value(s) out of range");
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public int[] scale(int s) {
        int[] scaled = {(int)(r*s), (int)(g*s), (int)(b*s)};

        return scaled;
    }
    
}
