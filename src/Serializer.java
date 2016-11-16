import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

import org.jdom.*;
import org.jdom.output.XMLOutputter;

/*  
 * This class will serialize each object passed to it based on the formatting procedures outlined on the 
 * assignment page. The class is called from the object creator which passes its created object into this
 * class which will attach the serialized fields and content into an xml document.
 * The document is returned by the constructor method of this class.
 * 
 * Some of the code was aided with the reference of online material, any similarities should be assumed to be from this.
 */
public class Serializer {
	
	static Element serialized = new Element("serialized");
	static Document doc = new Document(serialized);
	
	public org.jdom.Document serialize(Object obj)  {
		
			doc.setRootElement(serialized);
			
			// Choosing a method to format XML
			if(obj.getClass().toString().equals("class serialization.SimpleObject")){
				serializeSimple(obj);
			}
			else if(obj.getClass().toString().equals("class serialization.ObjectReference")){
				serializeObjRef(obj);
			}
			else if(obj.getClass().toString().equals("class serialization.PrimitiveObject")){
				serializeObjPrim(obj);
			}
			else if(obj.getClass().toString().equals("class serialization.ArrayRefObject")){
				serializeArrayRef(obj);
			}
			else if(obj.getClass().toString().equals("class serialization.CollectionObject")){
				serializeObjCol(obj);
				
			}
			createXML();
			
		return doc;
	}
	// Obtain the doc file
	public org.jdom.Document getDoc(){
		return doc;
	}

	// Write to XML document
	private void createXML() {
		XMLOutputter xmlOutput = new XMLOutputter();
		try {
			xmlOutput.output(doc, new FileWriter("src//serialization//sero.xml"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	// Serialize Objects
	private void serializeObjCol(Object obj) {
		Element object = elementCreation(obj);
		Field[] aField = obj.getClass().getDeclaredFields();
		
		for(int i = 0; i < aField.length; i++){
			
			Element field = fieldCreation(aField, i);
			try {
				elementValue(obj, aField, i, field);
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
			
				System.out.println("Error has occured");
			} 
			object.addContent(field);
		}
		doc.getRootElement().addContent(object);
	}


}
