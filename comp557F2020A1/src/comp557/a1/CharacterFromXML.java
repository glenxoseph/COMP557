package comp557.a1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Loads an articulated character hierarchy from an XML file. 
 */
public class CharacterFromXML {

	public static GraphNode load( String filename ) {
		try {
			InputStream inputStream = new FileInputStream(new File(filename));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			return createScene( null, document.getDocumentElement() ); // we don't check the name of the document elemnet
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load simulation input file.", e);
		}
	}

	/**
	 * Load a subtree from a XML node.
	 * Returns the root on the call where the parent is null, but otherwise
	 * all children are added as they are created and all other deeper recursive
	 * calls will return null.
	 */
	public static GraphNode createScene( GraphNode parent, Node dataNode ) {
		NodeList nodeList = dataNode.getChildNodes();
		for ( int i = 0; i < nodeList.getLength(); i++ ) {
			Node n = nodeList.item(i);
			// skip all text, just process the ELEMENT_NODEs
			if ( n.getNodeType() != Node.ELEMENT_NODE ) continue;
			String nodeName = n.getNodeName();
			GraphNode node = null;
			if ( nodeName.equalsIgnoreCase( "node" ) ) {
				node = CharacterFromXML.createJoint( n );
			} else if ( nodeName.equalsIgnoreCase( "geom" ) ) {        		
				node = CharacterFromXML.createGeom( n ) ;            
			} else {
				System.err.println("Unknown node " + nodeName );
				continue;
			}
			if ( node == null ) continue;
			// recurse to load any children of this node
			createScene( node, n );
			if ( parent == null ) {
				// if no parent, we can only have one root... ignore other nodes at root level
				return node;
			} else {
				parent.add( node );
			}
		}
		return null;
	}

	/**​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
	 * Create a joint
	 * 
	 * TODO: Objective 8: XML, Adapt commented code in createJoint() to create your joint nodes when loading from xml
	 */
	public static GraphNode createJoint( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t;
		Tuple3d degrees;
		Tuple3d x; //maxmin of x
		Tuple3d y; //maxmin of y
		Tuple3d z; //maxmin of z

		Tuple3d r;
		Tuple3d p;
		String axis;
		if ( type.equals("free") ) {
			FreeJoint joint = new FreeJoint( name );
			return joint;
		} else if ( type.equals("spherical") ) {
			// position is optional (ignored if missing) but should probably be a required attribute!​‌​​​‌‌​​​‌‌​​​‌​​‌‌‌​​‌
			// Could add optional attributes for limits (to all joints)

			SphericalJoint joint = new SphericalJoint( name );
			degrees = getTuple3dAttr(dataNode, "degrees");
			x = getTuple3dAttr(dataNode, "x");
			y = getTuple3dAttr(dataNode, "y");
			z = getTuple3dAttr(dataNode, "z");
			if ( (t=getTuple3dAttr(dataNode,"position")) != null ) joint.setPosition( t );	
			joint.setDegrees(degrees, x, y, z);
			return joint;

		} else if ( type.equals("rotary") ) {
			// position and axis are required... passing null to set methods
			// likely to cause an execption (perhaps OK)

			r = getTuple3dAttr(dataNode,"rotate");
			p = getTuple3dAttr(dataNode,"position");
			axis = dataNode.getAttributes().getNamedItem("axis").getNodeValue();
			RotaryJoint joint = new RotaryJoint(name);

			joint.setPosition(p);
			joint.rotate(axis, r);
			return joint;

			//			Hinge joint = new Hinge( name );
			//			joint.setPosition( getTuple3dAttr(dataNode,"position") );
			//			joint.setAxis( getTuple3dAttr(dataNode,"axis") );
			//			return joint;

		} else {
			System.err.println("Unknown type " + type );
		}
		return null;
	}

	/**
	 * Creates a geometry DAG node 
	 * 
	 * TODO: Objective 5: Adapt commented code in greatGeom to create your geometry nodes when loading from xml
	 */
	public static GraphNode createGeom( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t;
		Tuple3d r;
		Tuple3d s;
		Tuple3d rgb;
		if ( type.equals("CUBE" ) || type.equals("SPHERE" ) || type.equals("SIMPLEAXIS" ) || type.equals("QUAD" )) {
			Geometry geom = new Geometry(name);
			
			t = getTuple3dAttr(dataNode, "translate");
			r = getTuple3dAttr(dataNode, "rotate");
			s = getTuple3dAttr(dataNode, "scale");
			rgb = getTuple3dAttr(dataNode, "rgb");
			
			geom.setTranslate(t);
			geom.setRotate(r);
			geom.setScale(s);
			geom.setColor(rgb);
			geom.setShape(type);
			return geom;
		} else {
			System.err.println("unknown type " + type );
		}
		return null;		
	}

	/**
	 * Loads tuple3d attributes of the given name from the given node.
	 * @param dataNode
	 * @param attrName
	 * @return null if attribute not present
	 */
	public static Tuple3d getTuple3dAttr( Node dataNode, String attrName ) {
		Node attr = dataNode.getAttributes().getNamedItem( attrName);
		Vector3d tuple = null;
		if ( attr != null ) {
			Scanner s = new Scanner( attr.getNodeValue() );
			tuple = new Vector3d( s.nextDouble(), s.nextDouble(), s.nextDouble() );			
			s.close();
		}
		return tuple;
	}

}