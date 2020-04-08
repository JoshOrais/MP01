package datastructures.graph;

public abstract class WeightedGraph extends Graph{
	public abstract void addEdge(String vertexA, String vertexB, int weight);
	public abstract int getEdgeWeight(String vertexA, String vertexB);
	
	@Override
	public void addEdge(String vertexA, String vertexB) {
		addEdge(vertexA, vertexB, 1);
	}
}