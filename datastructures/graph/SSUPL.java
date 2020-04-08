package datastructures.graph;

public class SSUPL {
	private static int getIndex(String vertex, String[] array) {
		for (int i = 0; i < array.length; ++i) {
			if (vertex.equals(array[i])) return i;
		}
		return -1;
	}
	
	public static boolean isEmpty(boolean[] a) {
		for (int i = 0; i < a.length; ++i) {
			if (a[i]) return false;
		}
		return true;
	}
	
	public static int getLeast(boolean[] a, boolean[] c, int[] b) {
		int ret = -1;
		int least = Integer.MAX_VALUE;
		
		for (int i = 0; i  < a.length; ++i) {
			if (a[i] && !c[i] &&b[i] < least) {
				ret = i;
				least = b[i];
			}
		}
		
		return ret;
	}
	
	public static int[] SSUPL(Graph G, String startingVertex) {
		
		///SHHHHHHHHHHHHHHHHH
		String[] vertices = G.getVertices();
		boolean[] visited = new boolean[vertices.length];
		boolean[] openset = new boolean[vertices.length];
		int[] weights = new int[vertices.length];
		
		// set initial weights to +INFINITY
		for (int i = 0; i < weights.length; ++i) { weights[i] = Integer.MAX_VALUE; };
		
		int ix = getIndex(startingVertex, vertices);
		weights[ix] = 0;
		openset[ix] = true;
		int ixx; 
		
		while ((ixx = getLeast(openset, visited, weights)) != -1) {
			visited[ixx] = true;
			openset[ixx] = false;
			for (String s : G.getAdjacentVertices(vertices[ixx])) {
				int vix = getIndex(s, vertices);
				if (weights[vix] > weights[ixx] + 1)
						weights[vix] = weights[ixx] + 1;
				if (!visited[vix])
					openset[vix] = true;
			}
		}
		
		
		return weights;
	}
	
	public static void main(String[] args) {
		Graph g = new UndirectedGraph();
		try {
			g.addVertex("v1"); g.addVertex("v2"); g.addVertex("v3"); g.addVertex("v4"); g.addVertex("v5");
			g.addVertex("v6"); g.addVertex("v7"); g.addVertex("v8"); g.addVertex("v9"); g.addVertex("v10");
			
			g.addEdge("v1", "v2");
			g.addEdge("v1", "v3");
			g.addEdge("v1", "v4");
			g.addEdge("v2", "v3");
			g.addEdge("v2", "v4");
			g.addEdge("v3", "v5");
			g.addEdge("v4", "v6");
			g.addEdge("v5", "v7");
			g.addEdge("v5", "v8");
			g.addEdge("v5", "v6");
			g.addEdge("v6", "v9");
			g.addEdge("v7", "v8");
			g.addEdge("v8", "v9");
			g.addEdge("v9", "v10");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] v = g.getVertices();
		int[] w = SSUPL(g, "v1");
		
		for (int  i = 0; i < v.length; ++i) {
			System.out.println(v[i] + " : " + w[i]);
		}
	}
}