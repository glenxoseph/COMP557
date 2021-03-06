// Glen Xu 260767363

package comp557.a2;

import javax.swing.JPanel;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import mintools.parameters.DoubleParameter;
import mintools.parameters.Parameter;
import mintools.parameters.ParameterListener;
import mintools.parameters.Vec3Parameter;
import mintools.swing.VerticalFlowPanel;

/**
 * Camera class to be used both for viewing the scene, but also to draw the scene from 
 * a point light.
 */
public class Camera {

	Vec3Parameter position = new Vec3Parameter("position", 0, 0, 10 );
	Vec3Parameter lookat = new Vec3Parameter("look at", 0, 0, 0 );
	Vec3Parameter up = new Vec3Parameter("up", 0, 1, 0 );

	DoubleParameter near = new DoubleParameter( "near plane", 1, 0.1, 10 );    
	DoubleParameter far = new DoubleParameter( "far plane" , 40, 1, 100 );    
	DoubleParameter fovy = new DoubleParameter( "fovy degrees" , 27, 14, 67 );    	

	/** Viewing matrix to be used by the pipeline */
	Matrix4d V = new Matrix4d();
	/** Projection matrix to be used by the pipeline */
	Matrix4d P = new Matrix4d();

	public Camera() {
		near.addParameterListener( new ParameterListener<Double>() {			
			@Override
			public void parameterChanged(Parameter<Double> parameter) {
				// Let's keep near and far from crossing!
				if ( near.getValue() >= far.getValue() ) {
					far.setValue( near.getValue() + 0.1 );
				}
			}
		});
		far.addParameterListener( new ParameterListener<Double>() {
			@Override
			public void parameterChanged(Parameter<Double> parameter) {
				// Let's keep near and far from crossing!
				if ( far.getValue() <= near.getValue() ) {
					near.setValue( far.getValue() - 0.1 );
				}
			}
		});
	}

	/**
	 * Update the projection and viewing matrices
	 * We'll do this every time we draw, though we could choose to more efficiently do this only when parameters change.
	 * @param width of display window (for aspect ratio)
	 * @param height of display window (for aspect ratio)
	 */
	public void updateMatrix( double width, double height ) {

		// TODO: Objective 2: Replace the default viewing matrix with one constructed from the parameters available in this class!
		//V.set( new double[] {
		//		1,  0,  0,  0,
		//		0,  1,  0,  0,
		//		0,  0,  1, -2.5,
		//		0,  0,  0,  1,
		//} );

		Vector3d eye = new Vector3d();
		position.get(eye);

		Vector3d uP = new Vector3d();
		up.get(uP);
		uP.normalize();

		Vector3d lookAt = new Vector3d();
		lookat.get(lookAt);

		Vector3d w = new Vector3d();
		w.x = eye.x - lookAt.x;
		w.y = eye.y - lookAt.y;
		w.z = eye.z - lookAt.z;
		w.normalize();

		Vector3d u = new Vector3d(); 
		u.cross(uP, w);
		u.normalize();

		Vector3d v = new Vector3d();
		v.cross(w, u);

		V.set(new double[] {
				u.x, v.x, w.x, eye.x,
				u.y, v.y, w.y, eye.y,
				u.z, v.z, w.z, eye.z,
				0, 0, 0, 1,

		} );
		
		V.invert();

		// TODO: Objective 3: Replace the default projection matrix with one constructed from the parameters available in this class!
		//        P.set( new double[] {
		//        		1,  0,  0,  0,
		//        		0,  1,  0,  0,
		//        		0,  0, -2, -3,
		//        		0,  0, -1,  1,
		//        } );    	    

		double n = near.getValue();
		double f = far.getValue();
		double t = n * Math.tan(fovy.getValue() * Math.PI / 360.0);
		double b = -t;
		double r = t * width / height;
		double l = -r;

		double a = 2 * n / (r - l);
		double c = (r + l) / (r - l);
		double d = 2 * n / (t - b);
		double e = (t + b) / (t - b);
		double g = (n + f) / (n - f);
		double h = 2 * n * f / (n - f);

		P.set( new double[] {
				a, 0, c, 0,
				0, d, e, 0,
				0, 0, g, h,
				0, 0, -1, 0,
		} );
	}

	/**
	 * @return controls for the camera
	 */
	public JPanel getControls() {
		VerticalFlowPanel vfp = new VerticalFlowPanel();
		vfp.add( position );
		vfp.add( lookat );
		vfp.add( up );
		vfp.add( near.getControls() );
		vfp.add( far.getControls() );
		vfp.add( fovy.getControls() );
		return vfp.getPanel();
	}

}
