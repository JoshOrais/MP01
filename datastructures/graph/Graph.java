package datastructures.graph;

public abstract class Graph{
	public abstract String[] getVertices();
	//public abstract T[][] getEdges();
	public abstract String[] getAdjacentVertices(String a);
	
	public abstract int getVertexCount();
	public abstract int getEdgeCount();
	
	public abstract boolean isConnected(String a, String b);
	public abstract boolean isAdjacent(String a, String b);
	
	public abstract void addVertex(String a) throws Exception;
	public abstract void removeVertex(String a) throws Exception;
	
	public abstract void addEdge(String a, String b);
	public abstract void removeEdge(String a, String b);
	
	public abstract String[] depthFirstTraversal(String startingVertex);
	public abstract String[] breadthFirstTraversal(String startingVertex);
}