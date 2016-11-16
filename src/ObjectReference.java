import java.io.Serializable;

public class ObjectReference implements Serializable {
	
	int num;
	SimpleObject anObject;
	
	public ObjectReference(){
	}
	
	//Create a 1 Digit Object
	//Which references another object (SimpleObject) in our case
	public ObjectReference(int num){
		
		this.num = num *2;
		anObject = new SimpleObject(this.num);
		this.num = anObject.getNumber();	
	}
	
	public int getNumber(){
		return num;
	}
	
	public String toString(){
		return "The number doubled and incremented: " + num;
	}

}
