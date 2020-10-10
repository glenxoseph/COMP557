package comp557.a1;

import javax.vecmath.Tuple3d;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class RotaryJoint extends GraphNode {
	
	DoubleParameter tx;
	DoubleParameter ty;
	DoubleParameter tz;
	DoubleParameter r;
	
	String axis;
	
		public RotaryJoint(String name) {
			super(name);
			tx = new DoubleParameter( name+" tx", 0, -2, 2 );
			ty = new DoubleParameter( name+" ty", 0, -2, 2 );
			tz = new DoubleParameter( name+" tz", 0, -2, 2 );
			dofs.add(r = new DoubleParameter(name+" degree", 0, -180, 180));
		}
		
		
		public void rotate(String axis, Tuple3d minMaxDef) {
			this.axis=axis;
			
			this.r.setMinimum(minMaxDef.x);
			this.r.setMaximum(minMaxDef.y);
			this.r.setDefaultValue(minMaxDef.z);
			
		}

		public void display(GLAutoDrawable drawable,BasicPipeline pipeline) {
			GL4 gl = drawable.getGL().getGL4();
			pipeline.setModelingMatrixUniform(gl);
			pipeline.push();
			
			pipeline.translate(tx.getValue(), ty.getValue(), tz.getValue());
			
			if(axis.equals("x")) {
				pipeline.rotate(r.getValue(), 1, 0, 0);
			}
			else if(axis.equals("y")) {
				pipeline.rotate(r.getValue(), 0, 1, 0);
			}	
			else if (axis.equals("z")) {
				pipeline.rotate(r.getValue(), 0, 0, 1);
			}
			
			pipeline.setModelingMatrixUniform(gl);
			super.display( drawable, pipeline );		
			pipeline.pop();
		}

		public void setPosition(Tuple3d position) {
			this.tx.setValue(position.x);
			this.ty.setValue(position.y);
			this.tz.setValue(position.z);
		}
}
		