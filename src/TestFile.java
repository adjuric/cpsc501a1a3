import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestFile {

	@Test
	public void testSimpleObject() {
		SimpleObject anObject = new SimpleObject(1);
		int result = anObject.getNumber();
		assertEquals(result, 2);
	}
	
	@Test
	public void testReferenceObject() {
		ObjectReference anObject = new ObjectReference(1);
		int result = anObject.getNumber();
		assertEquals(result,3);
		
	}
	
	@Test
	public void testPrimArray() {
		PrimitiveObject anObject = new PrimitiveObject(1,2,3);
		int arr[] = anObject.getArray();
		assertEquals(arr[0],1);
		assertEquals(arr[1],2);
		assertEquals(arr[2],3);
	}
	
	@Test
	public void testRefArray() {
		ArrayRefObject anObject = new ArrayRefObject(1,2,3);
		SimpleObject arr[] = anObject.getArray();
		assertEquals(arr[0].getNumber(),2);
		assertEquals(arr[1].getNumber(),3);
		assertEquals(arr[2].getNumber(),4);
	}
	
	@Test
	public void testCollection() {
		CollectionObject anObject = new CollectionObject(5,1,7);
		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(5);
		testList.add(7);
		assertEquals(anObject.getList(), testList );
	}
	
	@Test
	public void testSimpleHash(){
		SimpleObject anObject = new SimpleObject(1);
		int hash = anObject.hashCode();
		assertEquals(hash, 27081509);
	}
	
	@Test
	public void testRefHash(){
		ObjectReference anObject = new ObjectReference(1);
		int hash = anObject.hashCode();
		assertEquals(hash, 22466154);
	}
	
	@Test
	public void testPrimArHash(){
		PrimitiveObject anObject = new PrimitiveObject(1,2,3);
		int hash = anObject.hashCode();
		assertEquals(hash, 32527011);
	}
	
	@Test
	public void testRefArHash(){
		ArrayRefObject anObject = new ArrayRefObject(1,2,3);
		int hash = anObject.hashCode();
		assertEquals(hash, 33078119);
	}
	
	@Test
	public void testColletionHash(){
		CollectionObject anObject = new CollectionObject(1,2,3);
		int hash = anObject.hashCode();
		assertEquals(hash, 27220797);
	}
	
	@Test
	public void testSimpleFields(){
		SimpleObject anObject = new SimpleObject();
		Field[] afield = anObject.getClass().getDeclaredFields();
		assertEquals(afield[0].toString(), "int serialization.SimpleObject.someNumber");
	}
	
	@Test
	public void testRefFields(){
		ObjectReference anObject = new ObjectReference();
		Field[] afield = anObject.getClass().getDeclaredFields();
		assertEquals(afield[0].toString(), "int serialization.ObjectReference.someNumber");
		assertEquals(afield[1].toString(), "serialization.SimpleObject serialization.ObjectReference.anObject");
		
	}
	
	@Test
	public void testPrimFields(){
		PrimitiveObject anObject = new PrimitiveObject();
		Field[] afield = anObject.getClass().getDeclaredFields();
		assertEquals(afield[0].toString(), "int serialization.PrimitiveObject.numberOne");
		assertEquals(afield[1].toString(), "int serialization.PrimitiveObject.numberTwo");
		assertEquals(afield[2].toString(), "int serialization.PrimitiveObject.numberThree");
		assertEquals(afield[3].toString(), "int[] serialization.PrimitiveObject.numberArray");
		
	}
	
	@Test
	public void testArrayRefFields(){
		ArrayRefObject anObject = new ArrayRefObject();
		Field[] afield = anObject.getClass().getDeclaredFields();
		assertEquals(afield[0].toString(), "serialization.SimpleObject[] serialization.ArrayRefObject.refArray");
		assertEquals(afield[1].toString(), "int serialization.ArrayRefObject.numberOne");
		assertEquals(afield[2].toString(), "int serialization.ArrayRefObject.numberTwo");
		assertEquals(afield[3].toString(), "int serialization.ArrayRefObject.numberThree");

	}
	
	@Test
	public void testCollFields(){
		CollectionObject anObject = new CollectionObject();
		Field[] afield = anObject.getClass().getDeclaredFields();
		assertEquals(afield[0].toString(), "int serialization.CollectionObject.numberOne");
		assertEquals(afield[1].toString(), "int serialization.CollectionObject.numberTwo");
		assertEquals(afield[2].toString(), "int serialization.CollectionObject.numberThree");
		assertEquals(afield[3].toString(), "java.util.List serialization.CollectionObject.firstList");
		
	}

}
