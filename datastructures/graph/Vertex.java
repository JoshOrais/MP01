

public class Vertex {
	private String id;
	private ArrayList<Vertex> adjacencyList;
	
	public Vertex(String id, ArrayList<Vertex> adjacencyList) {
		this.id = id;
		this.adjacencyList = adjacencyList;
	}
	
	public Vertex(String id) {
		this(id, null);
	}
	
	public String getID() {
		return id;
	}
	
	public void addEdge(Vertex v) {
		if (adjacencyList == null)
			adjacencyList = new ArrayList<Vertex>();
		
		adjacencyList.add(v);
	}
	
	public void removeEdge(Vertex v) {
		if (adjacencyList == null) return;
		
		if (adjacencyList.contains(v))
			adjacencyList.remove(v);
	}
	
	public ArrayList<Vertex> getAdjacencyList() {
		return adjacencyList;
	}
}