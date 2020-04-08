package datastructures.graph;

import datastructures.Queue;

public class UndirectedMatrixGraph extends Graph {
	private boolean[][] adjacencyMatrix = new boolean[0][0];
	private String[] vertexNames = new String[0];
	
	private int getIndex(String vertexName) {
		for (int i = 0; i < vertexNames.length; ++i) {
			if (vertexNames[i].equals(vertexName)) return i;
		}
		return -1;
	}
	
	private void resizeMatrices(int newSize) {
		String[] n_vertexNames = new String[newSize];
		boolean[][] n_adjacencyMatrix = new boolean[newSize][newSize];
	
		int size = Math.min(vertexNames.length, newSize);
		for (int i = 0; i < size; ++i) {
			n_vertexNames[i] = vertexNames[i];
			for (int j = 0; j < size; ++j) {
				n_adjacencyMatrix[i][j] = adjacencyMatrix[i][j];
			}
		}
		
		adjacencyMatrix = n_adjacencyMatrix;
		vertexNames = n_vertexNames;
	}
	
	private void removeItem(int index) {
		int size = vertexNames.length;
		
		for (int i = index; i + 1 < size; ++i) {
			vertexNames[i] = vertexNames[i + 1];
			adjacencyMatrix[i] = adjacencyMatrix[i + 1];
		}
		
		for (int i = 0; i < size; ++i) {
			for (int j = index; j + 1 < size; ++j) {
				adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
			}
		}
		
		resizeMatrices(size - 1);
	}
	
	public String[] getVertices() {
		return vertexNames;
	}
	
	public String[] getAdjacentVertices(String a) {
		int ix = getIndex(a);
		int size = 0;
		for (int i = 0; i < vertexNames.length; ++i) {
			if (adjacencyMatrix[ix][i]) size++;
		}
		
		String[] adjacents = new String[size];
		
		int a = 0;
		for (int i = 0; i < vertexNames.length; ++i) {
			if (adjacencyMatrix[ix][i]) adjacents[a++] = vertexNames[i];
		}
	}
	
	public int getVertexCount() {
		return vertexNames.length;
	}
	
	public int getEdgeCount() {
		return 0;
	}
	
	public boolean isConnected(String a, String b);
	public boolean isAdjacent(String a, String b);
	
	public void addVertex(String a) throws Exception {
		if (getIndex(a) == -1) throw new Exception("ERROR: THERE IS ALREADY A VERTEX NAMED \"" + a + "\"; VERTICES CANNOT HAVE THE SAME NAME");
		int size = vertexNames.length;
		
		resizeMatrices(size + 1);
		
		vertexNames[size] = a;
	}
	
	public void removeVertex(String a) throws Exception {
		int ix = getIndex(a);
		
		if (ix == -1) throw new Exception("ERROR: VERTEX \"" +a+ "\" NOT FOUND");
		
		removeItem(ix);
	}
	
	public void addEdge(String a, String b) {
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		adjacencyMatrix[ix1][ix2] = true;
		adjacencyMatrix[ix2][ix1] = true;
	}
	
	public void removeEdge(String a, String b){
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		adjacencyMatrix[ix1][ix2] = false;
		adjacencyMatrix[ix2][ix1] = false;
	}
	
	public String[] depthFirstTraversal(String startingVertex) {
		int ix = getIndex(startingVertex);
		
		ArrayList<String> closedSet = new ArrayList<String>();
		closedSet.add(startingVertex);
		depthFirstTraversal(ix, closedSet);
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
	
	private void depthFirstTraversal(int IX, ArrayList<String> closedSet) {
		for (int i = 0; i < vertexNames.length; ++i) {
			if (adjacencyMatrix[IX][i]) {
				closedSet.add(vertexNames[i]);
				depthFirstTraversal(i, closedSet);
			}
		}
	}
	
	public String[] breadthFirstTraversal(String startingVertex) {
		Queue<String> q = new Queue<String>();
		ArrayList<String> closedSet = new ArrayList<String>();
		q.enqueue(startingVertex);
		
		String c;
		while ( (c = q.dequeue()) != null) {
			closedSet.add(c);
			int ix = getIndex(c);
			for (int i = 0; i < vertexNames.length; ++i) {
				if (adjacencyMatrix[ix][i]) {
					if (!closedSet.contains(vertexNames[i]) {
						q.enqueue(vertexNames[i]);
					}
				}
			}
		}
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
}