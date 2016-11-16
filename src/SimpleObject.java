import java.io.Serializable;

public class SimpleObject implements Serializable  {
	
	int num;
	
	public SimpleObject(){	
	}
	
	//Increment the given Number
	public SimpleObject(int num){
		this.num = num + 1;	
	}
	public int getNumber(){
		return num;
	}
	public String toString(){
		return "Updated Number: " + num;
	}

}
