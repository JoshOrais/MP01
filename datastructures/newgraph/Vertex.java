import java.util.ArrayList;

public class Vertex<T> {
	private final T id;
	private ArrayList<Edge<Vertex<T>>> attachedEdges = new ArrayList<Edge<Vertex<T>>>();
	
	public Vertex(T id) {
		this.id = id;
	}
	
	public T getID() {
		return this.id;
	}
	
	
	//temporary
	@Override 
	public boolean equals(Object o) {
		if (!(o instanceof Vertex)) return false;
		
		return ((Vertex)o).id.equals(this.id);
	}
	
	public void attachEdge(Edge<Vertex<T>> edge) {
		attachedEdges.add(edge);
	}
	
	public ArrayList<Edge<Vertex<T>>> getAttachedEdges() {
		return attachedEdges;
	}
}