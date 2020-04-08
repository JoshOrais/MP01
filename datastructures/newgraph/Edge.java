
public class Edge<T extends Vertex> {
	private final T vertexA;
	private final T vertexB;
	private final int weight;
	
	public Edge(T vertexA, T vertexB) {
		this(vertexA, vertexB, 1);
	}
	
	public Edge(T vertexA, T vertexB, int weight) {
		this.vertexA = vertexA;
		this.vertexB = vertexB;
		this.weight = weight;
	}
	
	public T getAVertex() {
		return vertexA;
	}
	
	public T getBVertex() {
		return vertexB;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Edge)) return false;
		
		return this.vertexA.equals(((Edge)other).vertexA) 
			&& this.vertexB.equals(((Edge)other).vertexB);
	}
	
	public int getWeight() {
		return weight;
	}
}