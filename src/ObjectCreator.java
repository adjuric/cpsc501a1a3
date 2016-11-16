import java.util.List;
import java.util.Scanner;

public class ObjectCreator {

	public static void main (String [] args){
		Interface();
	}

	static Serializer demo = new Serializer();
	
	public static void Interface(){
		Scanner in = new Scanner(System.in);
		String userChoice;
		boolean done = false;
		while (done  != true){
			System.out.println("Pick an object to create: ");
			System.out.println("1 - Simple Object (Include a digit, its value will increase by 1)");
			System.out.println("2 - Reference Object (Include a digit, its value will be doubled then incremented by 1)");
			System.out.println("3 - Primitve Array Object (Include 3 digits)");
			System.out.println("4 - Reference Array Object (Include 3 digits, all of which will be incremented by 1)");
			System.out.println("5 - Collection Object (Include 3 digits, they will be added to a list and sorted)");
			System.out.println("9 - To End Program)");
			System.out.print("Your Choice?: ");
			userChoice = in.nextLine();
		
			switch(userChoice){
			case("1"):
				SimpleObject();
				break;
			case("2"):
				ObjectRef();
				break;
			case("3"):
				ObjectArrayPrim();
				break;
			case("4"):
				ObjectArrayRef();
				break;
			case("5"):
				ObjectColl();
				break;
			case("9"):
				done = true;
				ClientEmulator aClient = new ClientEmulator();
				aClient.transfer("192.168.1.41", "12345", "src//serialization//sero.xml");
				break;
			default:
				System.out.println("invalid userChoice");
			}	
			System.out.println("");
		}
	}
	
	public static void SimpleObject(){
		int userChoice;
		Scanner in = new Scanner(System.in);
		System.out.println("Select a Integer: ");
		System.out.print("Chocie: ");
		userChoice = in.nextInt();
		SimpleObject anObject = new SimpleObject(userChoice);
		demo.serialize(anObject);	
	}
	
	public static void ObjectRef(){
		int userChoice;
		Scanner in = new Scanner(System.in);
		System.out.println("Select a Integer: ");
		System.out.print("Choice: ");
		userChoice = in.nextInt();
		ObjectReference anObject = new ObjectReference(userChoice);
		demo.serialize(anObject);	
	}
	
	public static void ObjectArrayPrim(){
		int userChoice1,userChoice2,userChoice3;
		Scanner in = new Scanner(System.in);
		System.out.println("Select 3 Integers: ");
		System.out.print("Digit 1: ");
		userChoice1 = in.nextInt();
		System.out.print("Digit 2: ");
		userChoice2 = in.nextInt();
		System.out.print("Digit 3: ");
		userChoice3 = in.nextInt();
		PrimitiveObject anObject = new PrimitiveObject(userChoice1, userChoice2, userChoice3);
		demo.serialize(anObject);	
	}
	
	public static void ObjectArrayRef(){
		int userChoice1,userChoice2,userChoice3;
		Scanner in = new Scanner(System.in);
		System.out.println("Select 3 Integers: : ");
		System.out.print("Digit 1: ");
		userChoice1 = in.nextInt();
		System.out.print("Digit 2: ");
		userChoice2 = in.nextInt();
		System.out.print("Digit 3: ");
		userChoice3 = in.nextInt();
		ArrayRefObject anObject = new ArrayRefObject(userChoice1, userChoice2, userChoice3);
		demo.serialize(anObject);
	}
	
	public static void ObjectColl(){
		int userChoice1,userChoice2,userChoice3;
		Scanner in = new Scanner(System.in);
		//System.out.println("in c");
		System.out.println("Select 3 Integers: ");
		System.out.print("Digit 1: ");
		userChoice1 = in.nextInt();
		System.out.print("Digit 2: ");
		userChoice2 = in.nextInt();
		System.out.print("Digit 3: ");
		userChoice3 = in.nextInt();
		CollectionObject anObject = new CollectionObject(userChoice1, userChoice2, userChoice3);
		demo.serialize(anObject);	
	}
}
