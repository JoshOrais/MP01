import java.util.ArrayList;

public abstract class WeightedGraph<T> extends Graph<T> {
	@Override
	public void addEdge(T a, T b) {
		addEdge(new Vertex<T>(a), new Vertex<T>(b));
	}
	
	@Override
	public void addEdge(Vertex<T> a, Vertex<T> b) {
		addEdge(a, b, 0);
	}
	
	public abstract void addEdge(Vertex<T> a, Vertex<T> b, int weight);
	
	public int getEdgeWeight(T a, T b) {
		return getEdgeWeight(new Vertex<T>(a), new Vertex<T>(b));
	}
	public abstract int getEdgeWeight(Vertex<T> a, Vertex<T> b);
}