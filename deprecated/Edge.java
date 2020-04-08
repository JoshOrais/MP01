public class Edge {
	public int vertex;
	public double weight;
	
	public Edge(int vertex, double weight) {
		this.vertex = vertex;
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) return false;
		
		return ((Edge)o).vertex == this.vertex;
	}
}