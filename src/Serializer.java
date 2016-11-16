import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

/*  
 * This method transforms a method to a XML
 * 
 * Some of the code was aided with the reference of online material, any similarities should be assumed to be from this.
 */
public class Serializer {
	
	static Element serialized = new Element("serialized");
	static Document doc = new Document(serialized);
	

	
	public org.jdom2.Document serialize(Object obj)  {
		serialized.detach();
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
	public org.jdom2.Document getDoc(){
		return doc;
	}

	// Write to XML document
	private void createXML() {
		XMLOutputter xmlOutput = new XMLOutputter();
		try {
			xmlOutput.output(doc, new FileWriter("sero.xml"));
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

	private Element elementCreation(Object obj) {
		Element object = new Element ("Object");
		int hash = obj.hashCode();
		String sHash = hash + "";
		object.setAttribute(new Attribute("class",obj.getClass().getSimpleName()));
		object.setAttribute(new Attribute("ID", sHash));
		return object;
	}

	// Serialize arrays with reference objects
	// Obtain all fields and check for arrays
	private void serializeArrayRef(Object obj) {
		Element object = elementCreation(obj);
		Element arrayObj = null;
		Field[] aField = obj.getClass().getDeclaredFields();
		
		for(int i = 0; i < aField.length; i++){
			if(aField[i].getType().isArray() == false){
				Element field = fieldCreation(aField, i);
				try {
					elementValue(obj, aField, i, field);	
				} catch (IllegalArgumentException | IllegalAccessException e) {
				
					System.out.println("Error has occured");
				} 
				object.addContent(field);
			} else{
				arrayObj = new Element("Object");
				arrayObj.setAttribute("class", aField[i].getDeclaringClass().getSimpleName());
				arrayObj.setAttribute("id", String.valueOf(aField[i].hashCode()));
				Object objValue = null;

				try {
					objValue = aField[i].get(obj);
				} catch (IllegalArgumentException e) {
					
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					
					e.printStackTrace();
				}
				String length = String.valueOf(Array.getLength((objValue)));
				arrayObj.setAttribute("length",length );
				int iLength = Integer.parseInt(length);
				for(int a = 0; a < iLength; a++ ){
					Element reference = new Element("reference");
					reference.addContent(String.valueOf(aField[i].hashCode()));
					arrayObj.addContent(reference);
				}
			}
		}
		doc.getRootElement().addContent(arrayObj);	
		doc.getRootElement().addContent(object);
	}

	// Serialize Array with Primitives
	// Obtain all elements and check if array
	// Otherwise treat as a regular primitive
	private void serializeObjPrim(Object obj) {
		
		Element object = elementCreation(obj);
		Element arrayObj = null;
		Field[] aField = obj.getClass().getDeclaredFields();
		Element field = null;

		for(int i = 0; i < aField.length; i++){
			if(aField[i].getType().isArray() == false){
				field = new Element("Field");
				field.setAttribute(new Attribute("name", aField[i].getName()));
				field.setAttribute("declaringclass",aField[i].getDeclaringClass().getSimpleName());
				try {
					elementValue(obj, aField, i, field);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
				
					System.out.println("Error has occured");
				} 
				object.addContent(field);
			}else{
				arrayObj = new Element("Object");
				arrayObj.setAttribute("class", aField[i].getDeclaringClass().getSimpleName());
				arrayObj.setAttribute("id", String.valueOf(aField[i].hashCode()));
				Object objValue = null;
				try {
					objValue = aField[i].get(obj);
				} catch (IllegalArgumentException e) {
					
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					
					e.printStackTrace();
				}
				String length = String.valueOf(Array.getLength((objValue)));
				arrayObj.setAttribute("length",length );
				int iLength = Integer.parseInt(length);
				for(int a = 0; a < iLength; a++ ){
					Object arrayElement = Array.get(objValue, a);
					Element aValue = new Element("value");
					aValue.addContent(arrayElement.toString());
					arrayObj.addContent(aValue);
				}
			}
		}
		doc.getRootElement().addContent(object);
		doc.getRootElement().addContent(arrayObj);
	}

	// Serialize objects with References 
	// Check if Primitives and format normally
	// Otherwise treat as a reference
	private void serializeObjRef(Object obj) {
		Element object = elementCreation(obj);
		Field[] aField = obj.getClass().getDeclaredFields();
		Element field = new Element("Field");
		Element afield = new Element("Field");
		
		for(int i = 0; i < aField.length; i++){
			if(aField[i].getType().isPrimitive()){
				field = new Element("Field");
				field.setAttribute(new Attribute("name", aField[i].getName()));
				field.setAttribute("declaringclass",aField[i].getDeclaringClass().getSimpleName());
				try {
					elementValue(obj, aField, i, field);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
				
					System.out.println("Error has occured");
				} 
				object.addContent(field);
			}
			else{
				afield = new Element("Field");
				afield.setAttribute(new Attribute("name", aField[i].getName()));
				afield.setAttribute("declaringclass",aField[i].getDeclaringClass().getSimpleName());
				Element reference = new Element("reference");
				reference.addContent(String.valueOf(aField[i].hashCode()));
				afield.addContent(reference);	
			}
		}

		doc.getRootElement().addContent(afield);
		doc.getRootElement().addContent(object);
	}

	// Serialize Simple Objects
	private void serializeSimple(Object obj) {
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


	private Element fieldCreation(Field[] aField, int i) {
		Element field = new Element("Field");
		field.setAttribute(new Attribute("name", aField[i].getName()));
		field.setAttribute("declaringclass",aField[i].getDeclaringClass().getSimpleName());
		return field;
	}

	private void elementValue(Object obj, Field[] aField, int i, Element field)
			throws IllegalAccessException {
			Element value = new Element("value");
			Object objValue = aField[i].get(obj);
			value.addContent(objValue.toString());
			field.addContent(value);
	}
}
