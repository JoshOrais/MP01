public class DirectedGraph<T> extends Graph {
	private ArrayList<Vertex<T>> vertices = new ArrayList<Vertex<T>>();
	private ArrayList<Edge<Vertex<T>>> edges = new ArrayList<Edge<Vertex<T>>>();
	
	//Maybe this should throw an exception
	public void addVertex(Vertex<T> t) {
		if (vertices.contains(t)) return;
		
		vertices.add(t);
	}
	public void addEdge(Vertex<T> a, Vertex<T> b) {
		if (!vertices.contains(a) || !vertices.contains(b)) return;
		
		Edge<Vertex<T>> current = new Edge<Vertex<T>>(a, b);
		edges.add(current);
		vertices.attachEdge(current);
	}

	public void removeVertex(Vertex<T> t) {
		if (!vertices.contains(t)) return;
		
		vertices.remove(t);
	}
	public void removeEdge(Vertex<T> a, Vertex<T> b) {
		if (!edges.contains(new Edge(a, b)) return;
		
		vertices.get(a)
	}
	
	public abstract Vertex<T> getVertices();
	public abstract ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> a);
	public abstract ArrayList<Edge<Vertex<T>>> getEdges();
	
	public abstract int vertexCount();
	public abstract int edgeCount();
	
	public abstract boolean isConnected(Vertex<T> a, Vertex<T> b);
	public abstract boolean isAdjacent(Vertex<T> a, Vertex<T> b);
}