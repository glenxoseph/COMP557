// Glen Xu 260767363

package comp557.a2;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import mintools.parameters.DoubleParameter;
import mintools.swing.VerticalFlowPanel;

/** 
 * Left Mouse Drag Arcball
 * @author kry
 */
public class ArcBall {

	private DoubleParameter fit = new DoubleParameter( "Fit", 1, 0.5, 2 );
	private DoubleParameter gain = new DoubleParameter( "Gain", 1, 0.5, 2, true );

	Vector3d lastPosition = new Vector3d();
	Matrix4d rotation = new Matrix4d();

	/** The accumulated rotation of the arcball */
	
	Matrix4d R = new Matrix4d();
	
	public ArcBall() {
		R.setIdentity();
		rotation.setIdentity();
	}

	/** 
	 * Convert the x y position of the mouse event to a vector for your arcball computations 
	 * @param e
	 * @param v
	 */
	public void setVecFromMouseEvent( MouseEvent e, Vector3d v ) {
		Component c = e.getComponent();
		Dimension dim = c.getSize();
		double width = dim.getWidth();
		double height = dim.getHeight();
		int mousex = e.getX();
		int mousey = e.getY();

		// TODO: Objective 1: finish arcball vector helper function
		double radius = Math.min(width, height) / fit.getFloatValue();
//		double radius = width;
		double x, y, z, s;

		x = (mousex - width / 2) / radius;
		y = - (mousey - height / 2) / radius;

		// square of the length of the vector to the point from the center
		double r = Math.pow(x, 2) + Math.pow(y, 2);

		if (r > 1.0) { // outside
			// normalizing factor
			s = 1.0 / Math.sqrt(r);
			x = s * x;
			y = s * y;
			z = 0.0;
		}
		else { // inside
			z = Math.sqrt(1.0 - r);
		}

		v.set(x, y, z);
		v.normalize();
	}

	public void attach( Component c ) {
		c.addMouseMotionListener( new MouseMotionListener() {			
			@Override
			public void mouseMoved( MouseEvent e ) {}
			@Override
			public void mouseDragged( MouseEvent e ) {				
				if ( (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0 ) {

					// TODO: Objective 1: Finish arcball rotation update on mouse drag when button 1 down!

					Vector3d thisPosition = new Vector3d();
					
					setVecFromMouseEvent(e, thisPosition);

					thisPosition.normalize();
					lastPosition.normalize();

					Vector3d perp = new Vector3d();
					perp.cross(lastPosition, thisPosition);

					double angle = lastPosition.dot(thisPosition);
					
					AxisAngle4d rot = new AxisAngle4d();
					
					rot.x = perp.x;
					rot.y = perp.y;
					rot.z = perp.z;
					rot.angle = Math.acos(angle) * gain.getFloatValue();
					
					lastPosition.set(thisPosition);
					
					rotation.set(rot);
					R.mul(rotation);

				}
			}
		});
		c.addMouseListener( new MouseListener() {
			@Override
			public void mouseReleased( MouseEvent e) {}

			@Override
			public void mousePressed( MouseEvent e) {
				// TODO: Objective 1: arcball interaction starts when mouse is clicked
				setVecFromMouseEvent(e, lastPosition);
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}

	public JPanel getControls() {
		VerticalFlowPanel vfp = new VerticalFlowPanel();
		vfp.setBorder( new TitledBorder("ArcBall Controls"));
		vfp.add( fit.getControls() );
		vfp.add( gain.getControls() );
		return vfp.getPanel();
	}

}