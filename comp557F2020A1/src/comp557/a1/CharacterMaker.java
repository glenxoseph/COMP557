package comp557.a1;

import javax.swing.JTextField;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import comp557.a1.Geometry.Shape;
import mintools.parameters.BooleanParameter;

public class CharacterMaker {

	static public String name = "CHARACTER NAME - YOUR NAME AND STUDENT NUMBER";
	
	// TODO: Objective 8: change default of load from file to true once you start working with xml
	static BooleanParameter loadFromFile = new BooleanParameter( "Load from file (otherwise by procedure)", false );
	static JTextField baseFileName = new JTextField("data/a1data/character");
	
	/**
	 * Creates a character, either procedurally, or by loading from an xml file
	 * @return root node
	 */
	static public GraphNode create() {
		
		if ( loadFromFile.getValue() ) {
			// TODO: Objectives 6: create your character in the character.xml file 
			return CharacterFromXML.load( baseFileName.getText() + ".xml");
		} else {
			// TODO: Objective 3,4,5,6: test DAG nodes by creating a small DAG in the CharacterMaker.create() method 
			
			Tuple3d tran = new Vector3d(1.0, 1.0, -1.0);
			
			GraphNode root = new FreeJoint("root");
			GraphNode arm = new RotaryJoint("arm", 3.0, 1.0, 2.0, "y", 0.0, 90.0, 0.0);
			SphericalJoint neck = new SphericalJoint("hand", 2, 0, -30, 30, 0, 0, -30, 30, 0, 0, -30, 30);
			GraphNode sp = new Geometry("namr", Shape.Sphere, tran, tran);
			GraphNode sp3 = new Geometry("namr", Shape.Cube, tran, tran);
			GraphNode sp1 = new Geometry("namr", Shape.Quad, tran, tran);
			GraphNode sp2 = new Geometry("namr", Shape.SimpleAxis, tran, tran);
			
			arm.add(sp3);
			root.add(arm);
			root.add(neck);
			root.add(sp);
			neck.add(sp1);
			neck.add(sp3);
			
			// Use this for testing, but ultimately it will be more interesting
			// to create your character with an xml description (see example).
			
			// Here we just return null, which will not be very interesting, so write
			// some code to create a test or partial character and return the root node.

			return root;
		}
	}
}
