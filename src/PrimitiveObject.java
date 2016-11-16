import java.io.Serializable;

public class PrimitiveObject implements Serializable {
	
	int digit1,digit2,digit3;
	int[] digits = new int[3];
	
	public PrimitiveObject(){
	}
	
	public PrimitiveObject(int digit1, int digit2, int digit3){
		
		this.digit1 = digit1;
		this.digit2 = digit2;
		this.digit3 = digit3;
		
		digits[0] = this.digit1;
		digits[1] = this.digit2;
		digits[2] = this.digit3;
	}
	
	public int[] getArray(){
		return digits;
	}
	
	public String toString(){
		return "First Digit: " + digits[0] + " Second Digit: " + digits[1] + " Third Digit: " + digits[2];
	}

}
