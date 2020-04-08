import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		ArrayList<Vertex<String>> ar = new ArrayList<Vertex<String>>();
		
		ar.add(new Vertex<String>("WHAT"));
		
		if (ar.contains(new Vertex<Integer>(1))) 
			System.out.println("CONTAINS WHAT");
		else 
			System.out.println("THE FASA");
	}
}