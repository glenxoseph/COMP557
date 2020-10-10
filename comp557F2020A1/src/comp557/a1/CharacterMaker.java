package comp557.a1;

import javax.swing.JTextField;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import mintools.parameters.BooleanParameter;

public class CharacterMaker {

	static public String name = "CHARACTER NAME - YOUR NAME AND STUDENT NUMBER";
	
	// TODO: Objective 8: change default of load from file to true once you start working with xml
	static BooleanParameter loadFromFile = new BooleanParameter( "Load from file (otherwise by procedure)", true);
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
			
			Tuple3d degrees = new Vector3d(0.5, 0.5, 0.5);
			Tuple3d position = new Vector3d(1, 1, 1);
			Tuple3d x = new Vector3d(-10, 10, 0.0);
			Tuple3d y = new Vector3d(-10, 10, 0.0);
			Tuple3d z = new Vector3d(-10, 10, 0.0);
			
			Tuple3d scale = new Vector3d(1, 1, 2);
			Tuple3d rotate = new Vector3d(-10, 10, 0.0);
			Tuple3d color = new Vector3d(0.6, 0, 0.3);
			Tuple3d translate = new Vector3d(-1, 1, 0.0);
			
//			
			GraphNode root = new FreeJoint("root");
			SphericalJoint ha = new SphericalJoint("1");
			ha.setDegrees(degrees, x, y, z);
			ha.setPosition(position);
			
			Geometry ho = new Geometry("yeah");
			ho.setShape("CUBE");
			ho.setScale(scale);
			ho.setRotate(rotate);
			ho.setColor(color);
			ho.setTranslate(translate);
//			
			root.add(ha);
			ha.add(ho);
			
			// Use this for testing, but ultimately it will be more interesting
			// to create your character with an xml description (see example).
			
			// Here we just return null, which will not be very interesting, so write
			// some code to create a test or partial character and return the root node.

			return root;
		}
	}
}
