import java.io.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;


/*
 *Class used to re-create a XML file from the given doc file 
 */
public class Deserializer implements Serializable {

    public Object[] deserialize() {

    	//Construct a doc from the XML
    	SAXBuilder builder = new SAXBuilder();
    	Document document = null;
    	try {
			document = builder.build("src/deserialization//output.xml");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// Obtaining Roots
    	// Parse, then View Objects
        Element root = document.getRootElement();
        List<Element> children = root.getChildren();
        List<Object> desObjects = new ArrayList<>();
        
        for (Element c : children) {
            List<Attribute> attributes = c.getAttributes();
            String childClass = attributes.get(0).getValue();
            childProcess(desObjects, c, childClass);
        }
        return desObjects.toArray();
    }

	private void childProcess(List<Object> desObjects, Element c,String childClass) {
		try {
		    Class actChild = Class.forName(childClass);
		    Object childObject = actChild.newInstance();
		    List<Element> fields = c.getChildren();
		    for (Element f : fields) {
		        List<Attribute> fieldAtts = f.getAttributes();
		        String fieldValue = f.getValue();
		        String fieldName = fieldAtts.get(0).getValue();

		         Field someField = childObject.getClass().getDeclaredField(fieldName);
		         String fieldType = someField.getType().getName();
		         someField.setAccessible(true);
		         caseSwitch(childObject, fieldValue, someField, fieldType);
		    }
		    desObjects.add(childObject);
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | SecurityException ex) {
		    ex.printStackTrace();
		}
	}

	private void caseSwitch(Object childObject, String fieldValue, Field someField,String fieldType) throws IllegalAccessException {
		switch (fieldType) {
		     case ("int"):
		         someField.setInt(childObject, Integer.parseInt(fieldValue));
		         break;
		     case ("double"):
		         someField.setDouble(childObject, Double.parseDouble(fieldValue));
		         break;
		     case ("boolean"):
		         someField.setBoolean(childObject, Boolean.parseBoolean(fieldValue));
		         break;
		 }
	}
}
