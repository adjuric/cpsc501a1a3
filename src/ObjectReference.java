import java.io.Serializable;

public class ObjectReference implements Serializable {
	
	int num;
	SimpleObject anObject;
	
	public ObjectReference(){
	}
	
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
