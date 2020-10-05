package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class SphericalJoint extends GraphNode{

	double tx,ty,tz;
	DoubleParameter rx,ry,rz;
	
	public SphericalJoint(String name,double tx, double x_def, double x_min, double x_max,
			                          double ty, double y_def, double y_min, double y_max,
			                          double tz, double z_def, double z_min, double z_max) {
		super(name);
		this.tx=tx;
		this.ty=ty;
		this.tz=tz;
		dofs.add( rx = new DoubleParameter( name+" rx", x_def, x_min, x_max ) );		
		dofs.add( ry = new DoubleParameter( name+" ry", y_def, y_min, y_max ) );
		dofs.add( rz = new DoubleParameter( name+" rz", z_def, z_min, z_max ) );
	}
	
	public void display(GLAutoDrawable drawable,BasicPipeline pipeline) {
		GL4 gl = drawable.getGL().getGL4();
		pipeline.push();
		
		pipeline.translate(tx,0,0);
		pipeline.translate(0,ty,0);
		pipeline.translate(0,0,tz);
		pipeline.rotate(rx.getValue(), 1, 0, 0);
		pipeline.rotate(ry.getValue(), 0, 1, 0);
		pipeline.rotate(rz.getValue(), 0, 0, 1);

		
		pipeline.setModelingMatrixUniform(gl);
		super.display( drawable, pipeline );		
		pipeline.pop();
	
	}

	public void setPosition(Tuple3d position) {
		
		this.tx=position.x;
		this.ty=position.y;
		this.tz=position.z;
	}
}

