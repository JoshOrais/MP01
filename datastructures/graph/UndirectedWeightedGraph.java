package datastructures.graph;

import java.util.ArrayList;

//AdjacencyList implementation of Undirected and Weighted Graph
public class UndirectedWeightedGraph extends WeightedGraph{
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	private Vertex getVertexByName(String name) {
		for (Vertex v : vertices){
			if (v.name.equals(name)) return v;
		}
		return null;
	}
	
	public String[] getVertices() {
		String[] vertexNames = new String[vertices.size()];
		
		for (int i = 0; i < vertexNames.length; ++i) {
			vertexNames[i] = vertices.get(i).name;
		}
		
		return vertexNames;
	}
	
	public String[] getAdjacentVertices(String a) {
		Vertex current = getVertexByName(a);
		
		String[] adjacents = new String[current.connectedEdges.size()];
		
		for (int  i = 0; i < adjacents.length; ++i) {
			adjacents[i] = current.connectedEdges.get(i).getNameAndWeight();
		}
		
		return adjacents;
	}
	
	public int getVertexCount() {
		return vertices.size();
	}
	
	public int getEdgeCount() {
		int count = 0;
		
		for (Vertex v : vertices) {
			count += v.connectedEdges.size();
		}
		
		return count;
	}
	
	public boolean isConnected(String a, String b) {
		Vertex current = getVertexByName(a);
		
		return false;
	}
	
	private boolean isConnected(Vertex a, String b, ArrayList<Vertex> closedSet) {
		if (a.name.equals(b)) return true;
		
		for (Edge e : a.connectedEdges) {
			if (!closedSet.contains(e.vertex)){
				closedSet.add(e.vertex);
				if (isConnected(e.vertex, b, closedSet)) return true;
			}
		}
		
		return false;
	}
	
	public boolean isAdjacent(String a, String b) {
		Vertex current = getVertexByName(a);
		
		for (Edge e : current.connectedEdges) {
			if (e.vertex.name.equals(b)) return true;
		}
		
		return false;
	}
	
	public void addVertex(String a) throws Exception {
		
		for (Vertex v : vertices) {
			if (v.name.equals(a)) throw new Exception("ERROR: THERE IS ALREADY A VERTEX NAMED \"" + a + "\"; VERTICES CANNOT HAVE THE SAME NAME");
		}
		
		vertices.add(new Vertex(a));
	}
	public void removeVertex(String a) throws Exception {
		for (Vertex v : vertices) {
			if (v.name.equals(a)) {
				vertices.remove(v);
				return;
			}
		}
		
		throw new Exception("ERROR: VERTEX \"" +a+ "\" NOT FOUND");
	}
	
	public void addEdge(String a, String b) {
		this.addEdge(a,b,0);
	}
	
	public void addEdge(String a, String b, int weight) {
		Vertex v_a = getVertexByName(a);
		Vertex v_b = getVertexByName(b);
		
		v_a.addEdge(v_b, weight);
		v_b.addEdge(v_a, weight);
	}
	
	public void removeEdge(String a, String b) {
		Vertex v_a = getVertexByName(a);
		Vertex v_b = getVertexByName(b);
		
		v_a.removeEdge(v_b);
		v_b.removeEdge(v_a);
	}
	
	public String[] depthFirstTraversal(String startingVertex) {
		ArrayList<Vertex> closedSet = new ArrayList<Vertex>();
		
		Vertex sv = getVertexByName(startingVertex);
		closedSet.add(sv);
		depthFirstTraversal(sv, closedSet);
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i).name;
		}
		
		return ret;
	}
	
	private void depthFirstTraversal(Vertex vertex, ArrayList<Vertex> closedSet) {
		for (Edge e : vertex.connectedEdges) {
			Vertex v = e.vertex;
			if (!closedSet.contains(v)) {
				closedSet.add(v);
				//IMPLICIT USE OF A STACK
				depthFirstTraversal(v, closedSet);
			}
		}
	}
	
	public String[] breadthFirstTraversal(String startingVertex) {
		Vertex sv = getVertexByName(startingVertex);
		
		ArrayList<Vertex> closedSet = new ArrayList<Vertex>();
		Queue q = new Queue();
		closedSet.add(sv);
		q.enqueue(sv);
		
		Vertex current;
		while ((current = q.dequeue()) != null) {
			for (Edge e: current.connectedEdges){
				if (!closedSet.contains(e.vertex)){
					closedSet.add(e.vertex);
					q.enqueue(e.vertex);
				}
			}
		}
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i).name;
		}
		
		return ret;
	}

	class Vertex {
		public ArrayList<Edge> connectedEdges = new ArrayList<Edge>();
		public String name;
		
		public Vertex(String name){
			this.name = name;
		}
		
		public void addEdge(Vertex v, int weight) {
			connectedEdges.add(new Edge(v, weight));
		}
		
		public void removeEdge(String v) {
			for (Edge e: connectedEdges) {
				if (e.vertex.name == v) {
					connectedEdges.remove(e);
				}
			}
		}
		
		public void removeEdge(Vertex v) {
			for (Edge e: connectedEdges) {
				if (e.vertex == v) {
					connectedEdges.remove(e);
				}
			}
		}
	}
	
	class Edge {
		public Vertex vertex;
		public int weight;
		
		public Edge(Vertex vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		public String getNameAndWeight() {
			String ret = "";
			return ret + vertex.name + " (" + String.valueOf(weight) + ")";
		}
	}
	
	class Queue {
		public Link front;
		class Link {
			public Link next;
			public Vertex vertex;
			public Link(Vertex v){
				this.vertex = v;
			}
		}
		
		public void enqueue(Vertex v) {
			if (front == null ) {
				front = new Link(v);
				return;
			}
			
			Link current = front;
			
			while (current.next != null)
				current = current.next;
			current.next = new Link(v);
		}
		
		public Vertex dequeue() {
			if (front == null) return null;
			Vertex a = front.vertex;
			if (front != null)
				front = front.next;
			return a;
		}
	}
}