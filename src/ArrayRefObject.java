import java.io.Serializable;

public class ArrayRefObject implements Serializable {
	
	SimpleObject[] digitReferences = new SimpleObject[3];
	int digit1, digit2, digit3;
	
	public ArrayRefObject(){	
	}
	
	//Create a 3 digit object that references the SimpleObject
	ArrayRefObject(int digit1, int digit2, int digit3){
		
		this.digit1 = digit1;
		this.digit2 = digit2;
		this.digit3 = digit3;
		
		digitReferences[0] = new SimpleObject(digit1);
		digitReferences[1] = new SimpleObject(digit2);
		digitReferences[2] = new SimpleObject(digit3);	
	}
	
	public SimpleObject[] getArray(){
		return digitReferences;
	}
	
	public String toString(){
		return "First Referenced Digit: " + digitReferences[0] + " Second Referenced Digit: " + digitReferences[1] + " Third Referenced Digit: " + digitReferences[2];	
	}
}
