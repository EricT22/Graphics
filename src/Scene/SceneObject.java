package Scene;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Triangle.*;
import Vector.*;
import Shader.*;
import Color.*;
import Common.*;


public class SceneObject extends SceneObjectAbstract implements Serializable{

	private static final long serialVersionUID = 8767842381397319727L;

	public SceneObject() {
		super();
		triangles = new ArrayList<TriangleAbstract>();
	}
	
	public SceneObject(String stlfile) {
		super();
		SceneObject so;
		try {
			so = this.makeObject(stlfile);
			triangles = so.triangles;
		} 
		catch (IOException e) {
			triangles = new ArrayList<TriangleAbstract>();
		}
	}
	
	public void addTriangle(TriangleAbstract ta) {
		triangles.add(ta);
	}

	@Override
	public VectorAbstract getCenter() {
		double x = 0;
		double y = 0;
		double z = 0;
		int count = triangles.size() * 3; // # of verts
		
		for (TriangleAbstract ta : triangles) {
			VectorAbstract[] verts = ta.getVertices();
			for (VectorAbstract va : verts) {
				x += va.getX();
				y += va.getY();
				z += va.getZ();
			}
		}
		return new Vector(x / count, y / count, z / count, new Color(0.0, 0.0, 0.0));
	}
	
	
	@Override
	public void render(int[][][] framebuffer, boolean shownormals, ShaderAbstract.FILLSTYLE fill, VectorAbstract viewpoint) {
		// sort based on zcenter
		for (TriangleAbstract ta : triangles) {
			if (ta.isVisible(viewpoint)) {
				ta.render(framebuffer, shownormals,  fill, viewpoint);
			}
		}
	}
	
	
	@Override
	public void scale(VectorAbstract scale) {
		VectorAbstract center = this.getCenter();
		for (int i = 0; i < triangles.size(); ++i) {
			TriangleAbstract ta = triangles.get(i);
			ta = ta.scale(scale, center, ta);
			triangles.set(i, ta);			
		}
	}

	@Override
	public void translate(VectorAbstract trans) {
		for (int i = 0; i < triangles.size(); ++i) {
			TriangleAbstract ta = triangles.get(i);
			ta = ta.translate(trans, ta);
			triangles.set(i, ta);			
		}
	}
	
	@Override
	public void rotate(VectorAbstract axis, double theta) {
		VectorAbstract center = this.getCenter();
		for (int i = 0; i < triangles.size(); ++i) {
			TriangleAbstract ta = triangles.get(i);
			ta = ta.rotateAxis(axis, center, Math.toRadians(theta), ta);
			triangles.set(i, ta);			
		}
	}

	@Override
	public VectorAbstract[] getExtents() {
		double minx, miny, minz;
		double maxx, maxy, maxz;
		
		maxx = minx = triangles.get(0).getVertices()[0].getX();
		maxy = miny = triangles.get(0).getVertices()[0].getY();
		maxz = minz = triangles.get(0).getVertices()[0].getZ();
		
		for (TriangleAbstract ta : triangles) {
			VectorAbstract[] verts = ta.getVertices();
			for (VectorAbstract va : verts) {
				double x = va.getX();
				double y = va.getY();
				double z = va.getZ();
				if (x > maxx) maxx = x;
				if (x < minx) minx = x;
				if (y > maxy) maxy = y;
				if (y < miny) miny = y;
				if (z > maxz) maxz = z;
				if (z < minz) minz = z;
				
			}
		}
		VectorAbstract[] result = {new Vector(minx, miny, minz, null), new Vector(maxx, maxy, maxz, null)};
		return result;
	}


	@Override
	public String toString(){
		String s = "";
		for (TriangleAbstract ta : triangles) {
			VectorAbstract[] verts = ta.getVertices();
			s += "Triangle:\n";
			for (VectorAbstract va : verts) {
				s += va + ", ";
			}
			s += "\n";
		}

		return s;
	}


	private SceneObject makeObject(String filename) throws IOException {
		Color color = null;
		
		// -- scene object to be built/returned
		SceneObject so = new SceneObject();

		// -- read the mesh from the STL file provided
		List<TriangleAbstract> mesh = STLParser.parseSTLFile(Path.of(filename));
		
		// -- add random color to the mesh triangles
		Random rn = new Random(42);
		for (int i = 0; i < mesh.size(); ++i) {
			VectorAbstract[] coloredVerts = new VectorAbstract[3];
			color = new Color(rn.nextInt(1000) / 1000.0, rn.nextInt(100) / 100.0, rn.nextInt(10) / 10.0);

			for (int j = 0; j < 3; ++j) {
				double x = mesh.get(i).getVertices()[j].getX();
				double y = mesh.get(i).getVertices()[j].getY();
				double z = mesh.get(i).getVertices()[j].getZ();

				coloredVerts[j] = new Vector(x, y, z, color);
			}
			mesh.get(i).setVertices(coloredVerts);
			so.addTriangle(mesh.get(i));
		}

		// -- get the extents of the mesh (the bounding 3D rectangle)
		VectorAbstract[] extents = so.getExtents();
		
		// -- calculate the ranges of x, y, z extents
		double xrange = Math.abs(extents[1].getX() - extents[0].getX());
		double yrange = Math.abs(extents[1].getY() - extents[0].getY());
		double zrange = Math.abs(extents[1].getZ() - extents[0].getZ());

		// -- scale all vertices to create unit extents
		for (int i = 0; i < mesh.size(); ++i) {
			VectorAbstract[] uVerts = new VectorAbstract[3];

			for (int j = 0; j < 3; ++j) {
				double x = mesh.get(i).getVertices()[j].getX();
				double y = mesh.get(i).getVertices()[j].getY();
				double z = mesh.get(i).getVertices()[j].getZ();
				x /= xrange;
				y /= yrange;
				z /= zrange;

				uVerts[j] = new Vector(x, y, z, mesh.get(i).getVertices()[j].getColor());
			}
			mesh.get(i).setVertices(uVerts);
		}

		return so;
	}

	public void serialize(String pathname){
		try {
            FileOutputStream fos = new FileOutputStream(new File(pathname));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (TriangleAbstract t : triangles){
				for (VectorAbstract v : t.getVertices()){
					oos.writeObject(v);
				}
			}

            oos.flush();
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("Can't read file.");
        }
	}

	public void deserialize(String pathname){
		try {
            FileInputStream fis = new FileInputStream(new File(pathname));
            ObjectInputStream ois = new ObjectInputStream(fis);

			ArrayList<VectorAbstract> vertices = new ArrayList<>();
			while (true){
				try {
					Object o = ois.readObject();
			
					if (o instanceof VectorAbstract){
						VectorAbstract v = (VectorAbstract) o;
						vertices.add(v);
						
					} else {
						System.out.println("Expected a VectorAbstract, got a " + o.getClass().getName());
					}
				} catch (EOFException e){
					break;
				}
			}

			ois.close();
			fis.close();

			try {
				triangles = new ArrayList<>();

				for (int i = 0; i < vertices.size(); i += 3){
					triangles.add(new Triangle(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2)));
				}
			} catch (IndexOutOfBoundsException e){
				System.out.println("Not enough vertices.");
			}
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e){
            System.out.println("Can't read file.");
        } catch (ClassNotFoundException e){
            System.out.println("Class not found.");
        }
	}


	public ArrayList<TriangleAbstract> getTriangles(){
		return triangles;
	}
}
