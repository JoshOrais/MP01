import java.util.ArrayList;

public abstract class Graph<T> {
	public void addVertex(T t) {
		addVertex(new Vertex<T>(t));
	}
	public void addEdge(T a, T b) {
		addEdge(new Vertex<T>(a), new Vertex<T>(b));
	}
	
	public void removeVertex(T t) {
		removeVertex(new Vertex<T>(t));
	}
	public void removeEdge(T a, T b) {
		removeEdge(new Vertex<T>(a), new Vertex<T>(b));
	}
	
	public abstract void addVertex(Vertex<T> t);
	public abstract void addEdge(Vertex<T> a, Vertex<T> b);

	public abstract void removeVertex(Vertex<T> t);
	public abstract void removeEdge(Vertex<T> a, Vertex<T> b);	
	
	public abstract Vertex<T> getVertices();
	public abstract ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> a);
	public abstract ArrayList<Edge<Vertex<T>>> getEdges();
	
	public abstract int vertexCount();
	public abstract int edgeCount();
	
	public abstract boolean isConnected(Vertex<T> a, Vertex<T> b);
	public abstract boolean isAdjacent(Vertex<T> a, Vertex<T> b);
	
}