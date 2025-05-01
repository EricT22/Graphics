package Vector;

import Color.*;

public class Vector extends VectorAbstract{

    private static double PRECISION = 0.0001;

    public Vector(double x, double y, double z, Color c){
        super();
        
        this.x = x;
        this.y = y;
        this.z = z;

        this.color = c;
    }


    @Override
    public double angleBetween(VectorAbstract v2) {
        return Math.acos(this.dot(v2) / (this.length() * v2.length()));
    }


    @Override
    public double dot(VectorAbstract v2) {
        return this.x * v2.x + this.y * v2.y + this.z * v2.z;
    }


    @Override
    public VectorAbstract cross(VectorAbstract v2) {
        return new Vector((this.y * v2.z - this.z * v2.y), (this.z * v2.x - this.x * v2.z), (this.x * v2.y - this.y * v2.x), this.color);
    }


    @Override
    public VectorAbstract unit() {
        double mag = this.length();

        return new Vector(this.x / mag, this.y / mag, this.z / mag, this.color);
    }


    @Override
    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }


    @Override
    public VectorAbstract add(VectorAbstract v2) {
        return new Vector(this.x + v2.x, this.y + v2.y, this.z + v2.z, this.color);
    }


    @Override
    public VectorAbstract sub(VectorAbstract v2) {
        return new Vector(this.x - v2.x, this.y - v2.y, this.z - v2.z, this.color);
    }


    @Override
    public VectorAbstract mult(double scale) {
        return new Vector(this.x * scale, this.y * scale, this.z * scale, this.color);
    }

    public VectorAbstract copy(){
        return new Vector(x, y, z, new Color(color.getR(), color.getG(), color.getB()));
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof VectorAbstract){
            VectorAbstract v = (VectorAbstract) o;

            return (Math.abs(this.x - v.x) < PRECISION) && (Math.abs(this.y - v.y) < PRECISION) && (Math.abs(this.z - v.z) < PRECISION);
        }
        return false;
    }
    
}
