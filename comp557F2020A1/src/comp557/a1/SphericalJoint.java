package comp557.a1;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class SphericalJoint extends GraphNode{

	DoubleParameter tx,ty,tz;
	DoubleParameter rx,ry,rz;
	
	public SphericalJoint(String name) {
		super(name);

		tx = new DoubleParameter( name + " tx", 0, -4, 4 );
		ty = new DoubleParameter( name + " ty", 0, -4, 4 );
		tz = new DoubleParameter( name + " tz", 0, -4, 4 );
		dofs.add( rx = new DoubleParameter( name + " rx", 0, -180, 180 ) );		
		dofs.add( ry = new DoubleParameter( name + " ry", 0, -180, 180 ) );
		dofs.add( rz = new DoubleParameter( name + " rz", 0, -180, 180 ) );
	}
	
	public void display(GLAutoDrawable drawable,BasicPipeline pipeline) {
		GL4 gl = drawable.getGL().getGL4();
		pipeline.push();
		
		pipeline.translate(tx.getValue(), 0, 0);
		pipeline.translate(0, ty.getValue(), 0);
		pipeline.translate(0, 0, tz.getValue());
		pipeline.rotate(rx.getValue(), 1, 0, 0);
		pipeline.rotate(ry.getValue(), 0, 1, 0);
		pipeline.rotate(rz.getValue(), 0, 0, 1);
		
		pipeline.setModelingMatrixUniform(gl);
		super.display( drawable, pipeline );		
		pipeline.pop();
	
	}

	public void setPosition(Tuple3d position) {
		
		this.tx.setValue(position.x);
		this.ty.setValue(position.y);
		this.tz.setValue(position.z);
	}
	
	public void setDegrees(Tuple3d degrees, Tuple3d x, Tuple3d y, Tuple3d z) {
		this.rx.setValue(degrees.x);
		this.ry.setValue(degrees.y);
		this.rz.setValue(degrees.z);
		
		this.rx.setMinimum(x.x);
		this.rx.setMaximum(x.y);
		this.rx.setDefaultValue(x.z);
		
		this.ry.setMinimum(y.x);
		this.ry.setMaximum(y.y);
		this.ry.setDefaultValue(y.z);
		
		this.rz.setMinimum(z.x);
		this.rz.setMaximum(z.y);
		this.rz.setDefaultValue(y.z);
	}
}

