import java.io.Serializable;
import java.util.*;

public class CollectionObject implements Serializable {
	
	int digit1,digit2,digit3;
	List<Integer> aList = new ArrayList<Integer>();
	
	public CollectionObject(){
	}
	
	//Create a "Collections" Object using 3 Digits
	public CollectionObject(int digit1, int digit2, int digit3){
		
		this.digit1 = digit1;
		this.digit2 = digit2;
		this.digit3 = digit3;
		Collections.addAll(aList, digit1, digit2, digit3);
		Collections.sort(aList);
	
	}
	
	public List<Integer> getList(){
		return aList;
	}
}
