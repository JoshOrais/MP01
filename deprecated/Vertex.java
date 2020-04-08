import java.util.ArrayList;

public class Vertex {
	private VertexObject vx;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public Vertex(VertexObject vx) {
		this.vx = vx;
	}
	
	public boolean addEdge(int ix, double weight) {
		if (edges.contains(new Edge(ix, weight))) return false;
		
		edges.add(new Edge(ix, weight));
		return true;
	}
	
	public boolean removeEdge(int ix) {
		if (edges.contains(new Edge(ix, 0.f))) {
			edges.remove(new Edge(ix, 0.f));
			return true;
		}
		return false;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public VertexObject getObject() {
		return vx;
	}
	
}