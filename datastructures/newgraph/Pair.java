package datastructures.graph;

public class Pair<A, B> {
	private A a;
	private B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public A itemA() {
		return a;
	}
	
	public B itemB() {
		return b;
	}
	
	public void setItemA(A a) {
		this.a = a;
	}
	
	public void setItemB(B b) {
		this.b = b;
	}
}