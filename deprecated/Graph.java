public class Graph {
	public static final int MAX_SIZE = 50;
	
	private Vertex[] vertices = new Vertex[MAX_SIZE];
	private int size = 0;
	
	
	public Vertex vertex(int i) {
		return vertices[i];
	}
	
	public boolean addVertex(VertexObject vx) {
		if (size >= MAX_SIZE) return false;
		size++;
		vx.setVertex(next());
		vertices[next()] = new Vertex(vx);
		return true;
	}
	
	public void removeVertex(int ix) {
		
		for (int  i = 0; i < MAX_SIZE; ++i) {
			vertices[i].removeEdge(i);
		}
		
		
		vertices[ix] = null;
	}
	
	private int next() {
		for (int i = 0; i < MAX_SIZE; ++i) {
			if (vertices[i] == null) return i;
		}
		
		return -1;
	}
	
	public Vertex[] getVertices() {
		return vertices;
	}
}