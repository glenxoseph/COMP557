package comp557.a1;

import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import comp557.a1.geom.Cube;
import comp557.a1.geom.Quad;
import comp557.a1.geom.SimpleAxis;
import comp557.a1.geom.Sphere;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;


public class Geometry extends GraphNode{
	Tuple3d translate;
	Tuple3d scale;
	
	public enum Shape {Cube,Sphere,Quad,SimpleAxis}
	private Shape shape;

	
	public Geometry(String name,Shape shape,Tuple3d trans,Tuple3d scale) {
		super(name);
		this.translate = trans;
		this.scale = scale;
		this.shape = shape;
		if(this.translate == null) this.translate = new Vector3d(0,0,0);
		if(this.scale == null) this.scale = new Vector3d(0,0,0);
	}
	
	@Override
	public void display( GLAutoDrawable drawable, BasicPipeline pipeline ) {
		pipeline.push();
		
		GL4 gl = drawable.getGL().getGL4();
		pipeline.setModelingMatrixUniform(gl);
		
		pipeline.translate(translate.x,translate.y,translate.z);
		pipeline.scale(scale.x,scale.y,scale.z);
			
switch(shape) {
		
		case Cube:
			Cube.draw(drawable, pipeline);
			break;
		
		case Sphere:
			Sphere.draw(drawable, pipeline);
			break;
		case SimpleAxis:
			SimpleAxis.draw(drawable, pipeline);
			break;
		case Quad:
			//System.out.println("quad");
			Quad.draw(drawable, pipeline);
			break;
		
		}
		super.display( drawable, pipeline );		
		pipeline.pop();
	}
}
