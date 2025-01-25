package Triangle;

import Vector.VectorAbstract;

/**
 * Abstract class for creating a Triangle class
 */
public abstract class TriangleAbstract {
	/**
	 * An array of vertices (which can be treated as points since VectorAbstract stores
	 * a vector as a direction vector (not tail and head), should be size 3.
	 */
	protected VectorAbstract[] vertices;
	
	/**
	 * Compute the center of the triangle (average of the 3 vertices)
	 * @return the central point of the triangle
	 */
	public abstract VectorAbstract getCenter();

	/**
	 * Compute the perimeter of the triangle
	 * @return the length of the perimeter
	 */
	public abstract double getPerimeter();

	/** 
	 * Compute the area of the triangle
	 * @return the area
	 */
	public abstract double getArea();
	
	/**
	 * Compute the normal vector of the triangle. This can be anchored at any vertex
	 * @return the normal vector as a unit vector (normalized)
	 */
	public abstract VectorAbstract getNormal();
	
	
	/**
	 * Default constructor for use by the extending class only (can't construct an abstract class)
	 */
	protected TriangleAbstract() {
		super();
	}

	/**
	 * Return the list of triangle vertices
	 * @return the vertices
	 */
	public VectorAbstract[] getVertices() {
		return vertices;
	}
	
	/**
	 * Set the list of triangle vertices
	 * @param vertices the new vertex values
	 */
	public void setVertices (VectorAbstract[] vertices) {
		this.vertices = vertices.clone();
	}

	@Override
	public String toString() {
		String s = "[";
		for(VectorAbstract v : vertices){
			s += v;
		}
		s += "]";
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof TriangleAbstract) {
			TriangleAbstract rhs = (TriangleAbstract)obj;
			for (int i = 0; i < vertices.length; ++i) {
				if (!this.vertices[i].equals(rhs.vertices[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}