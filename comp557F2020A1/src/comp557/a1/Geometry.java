package comp557.a1;

import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;
import com.jogamp.opengl.util.gl2.GLUT;

import comp557.a1.geom.Cube;
import comp557.a1.geom.Quad;
import comp557.a1.geom.SimpleAxis;
import comp557.a1.geom.Sphere;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;


public class Geometry extends GraphNode{

	double tx, ty, tz;
	double rx, ry, rz;
	double sx, sy, sz;
	double r, g, b;

	private String shape;
		
	public Geometry(String name) {
		super(name);
		
	}
	
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	public void setTranslate(Tuple3d translate) {
		this.tx = translate.x;
		this.ty = translate.y;
		this.tz = translate.z;
	}
	
	public void setScale(Tuple3d scale) {
		this.sx = scale.x;
		this.sy = scale.y;
		this.sz = scale.z;
	}
	
	public void setRotate(Tuple3d rotate) {
		this.rx = rotate.x;
		this.ry = rotate.y;
		this.rz = rotate.z;
	}
	
	public void setColor(Tuple3d color) {
		this.r = color.x;
		this.g = color.y;
		this.b = color.z;
	}
	

	@Override
	public void display( GLAutoDrawable drawable, BasicPipeline pipeline ) {
		pipeline.push();
		
		GL4 gl = drawable.getGL().getGL4();
		//gl.glUniform3f(pipeline.lightColorID, 1, 1, 0);
		pipeline.setModelingMatrixUniform(gl);	
		
		gl.glUniform3f(pipeline.kdID, (float) r, (float) g, (float) b);
		
		pipeline.translate(tx, ty, tz);
		pipeline.scale(sx, sy, sz);
		pipeline.rotate(rx, 1, 0, 0);
		pipeline.rotate(ry, 0, 1, 0);
		pipeline.rotate(rz, 0, 0, 1);
		
		if (shape.equals("CUBE")) {
			Cube.draw(drawable, pipeline);
		}
		else if (shape.equals("SPHERE")) {
			Sphere.draw(drawable, pipeline);
		}
		else if (shape.equals("SIMPLEAXIS")) {
			SimpleAxis.draw(drawable, pipeline);
		}
		else if(shape.equals("QUAD")) {
			Quad.draw(drawable, pipeline);
		}
		
		super.display( drawable, pipeline );		
		pipeline.pop();
	}
}
