package comp557.a1;

import javax.vecmath.Tuple3d;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class RotaryJoint extends GraphNode {
	double tx,ty,tz;
	String axis;
	DoubleParameter rx,ry,rz;
	
		public RotaryJoint(String name,double tx, double ty, double tz, String axis, double min, double max, double def) {
			super(name);
			this.tx=tx;
			this.ty=ty;
			this.tz=tz;
			this.axis=axis;
			if(axis.equals("x"))
			dofs.add(this.rx = (new DoubleParameter(name+" rx", def, min, max)));
			if(axis.equals("y"))
			dofs.add(this.ry = (new DoubleParameter(name+" ry", def, min, max)));
			if(axis.equals("z"))
			dofs.add(this.rz = (new DoubleParameter(name+" rz", def, min, max)));
		}

		public void display(GLAutoDrawable drawable,BasicPipeline pipeline) {
			GL4 gl = drawable.getGL().getGL4();
			pipeline.setModelingMatrixUniform(gl);
			pipeline.push();
			
			pipeline.translate(tx,ty,tz);
			
			if(axis.equals("x"))
				pipeline.rotate(rx.getValue(), 1, 0, 0);
			if(axis.equals("y"))
				pipeline.rotate(ry.getValue(), 0, 1, 0);
			if(axis.equals("z"))
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
		