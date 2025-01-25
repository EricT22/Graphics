package Vector;

/**
 * Abstract class for creating a 3D Vector class
 * The vector will be represented by one point. The other is assumed to be (0, 0, 0)
 */
public abstract class VectorAbstract {

	/**
	 * The vector is stored as a direction vector (not tail and head form.) This will
	 * simplify manipulations to be performed later. It is assumed that the head is [0, 0, 0]
	 * 
	 * x, y, z coordinates of the direction vector.
	 */
	protected double x;
	protected double y;
	protected double z;
	
	/**
	 * Compute the angle between two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the angle between the two vectors in radians
	 */
	public abstract double angleBetween(VectorAbstract v2);

	/**
	 * Compute the dot product of two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the dot product of the two vectors
	 */
	public abstract double dot(VectorAbstract v2);

	/**
	 * Compute the cross product of two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the cross product of the two vectors
	 */
	public abstract VectorAbstract cross(VectorAbstract v2);

	/**
	 * Compute the unit vector of this
	 * @param v2 the second vector
	 * @return the cross product of the two vectors normalized to unit length
	 */
	public abstract VectorAbstract unit();

	/**
	 * Compute the length (from [0, 0, 0]) of the vector
	 * @return the length
	 */
	public abstract double length();

	/**
	 * Add two vectors component by component
	 * @param v2 The vector to add to this
	 * @return the new vector
	 */
	public abstract VectorAbstract add(VectorAbstract v2);

	/**
	 * Add two vectors component by component
	 * @param v2 The vector to subtract from this
	 * @return the new vector
	 */
	public abstract VectorAbstract sub(VectorAbstract v2);

	/**
	 * Multiply a vector by a scalar (increase the length of the vector)
	 * @param scale the scalar value for multiplication
	 * @return the new vector
	 */
	public abstract VectorAbstract mult(double scale);


	/**
	 * Default constructor for use by the extending class only (can't construct an abstract class)
	 */	
	protected VectorAbstract() {
		super();
	}

	/**
	 * Get the X component
	 * @return X
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the Y component
	 * @return Y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the Z component
	 * @return Z
	 */
	public double getZ() {
		return z;
	}
	
	@Override
	public String toString() {
	    return "[" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
	    if (obj == this) {
	        return true;
	    }
	    if (obj instanceof VectorAbstract) {
	        VectorAbstract v = (Vector) obj;
	        return (x == v.x) && (y == v.y) && (z == v.z);
	    }
	    return false;
	}

}