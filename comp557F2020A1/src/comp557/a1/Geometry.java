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
//	Tuple3d translate;
    

	double transX, transY, transZ;
	double rotateX, rotateY, rotateZ;
	double scaleX, scaleY, scaleZ;
	private String shape;
    
float red, green, blue;

	
	public Geometry(String name, String shape,
			double transX, double transY,double transZ, 
			double rotateX,double rotateY,double rotateZ,
			double scaleX, double scaleY,double scaleZ,
			float red, float green, float blue){
		super(name);
		
		this.transX = transX ;
		this.transY = transY ;
		this.transZ = transZ ;
		
		this.rotateX = rotateX ;
		this.transY = transY ;
		this.transZ = transZ ;
		
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		
		this.shape = shape;
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		
	}
	

	@Override
	public void display( GLAutoDrawable drawable, BasicPipeline pipeline ) {
		pipeline.push();
		
		GL4 gl = drawable.getGL().getGL4();
		//gl.glUniform3f(pipeline.lightColorID, 1, 1, 0);
		pipeline.setModelingMatrixUniform(gl);	
		
		gl.glUniform3f(pipeline.kdID, red, green, blue);
		
		pipeline.translate(transX, transY, transZ);
		pipeline.scale(scaleX, scaleY, scaleZ);
		pipeline.rotate(rotateX, 1, 0, 0);
		pipeline.rotate(rotateY, 0, 1, 0);
		pipeline.rotate(rotateZ, 0, 0, 1);
		

	
	
      if (shape.toUpperCase().equals("CUBE")) {	
			Cube.draw(drawable, pipeline);		
      }else if(shape.toUpperCase().equals("SPHERE")) {
			Sphere.draw(drawable, pipeline);
      }else if(shape.toUpperCase().equals("SIMPLEAXIS")) {
			SimpleAxis.draw(drawable, pipeline);
      }else if(shape.toUpperCase().equals("QUAD")) {
			Quad.draw(drawable, pipeline);
		
		}

		super.display( drawable, pipeline );		
		pipeline.pop();
	}
}
