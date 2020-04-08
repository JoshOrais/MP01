package datastructures.graph;

import java.util.ArrayList;

//AdjacencyList implementation of Undirected and Weighted Graph
public class UndirectedGraph extends Graph{
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
		
		String[] adjacents = new String[current.adjacentVertices.size()];
		
		for (int  i = 0; i < adjacents.length; ++i) {
			adjacents[i] = current.adjacentVertices.get(i).name;
		}
		
		return adjacents;
	}
	
	public int getVertexCount() {
		return vertices.size();
	}
	
	public int getEdgeCount() {
		int count = 0;
		
		for (Vertex v : vertices) {
			count += v.adjacentVertices.size();
		}
		
		return count;
	}
	
	public boolean isConnected(String a, String b) {
		Vertex current = getVertexByName(a);
		
		ArrayList<Vertex> closedSet = new ArrayList<Vertex>();
		closedSet.add(current);
		return isConnected(current, b, closedSet);
	}
	
	private boolean isConnected(Vertex a, String b, ArrayList<Vertex> closedSet) {
		if (a.name.equals(b)) return true;
		
		for (Vertex v : a.adjacentVertices) {
			if (!closedSet.contains(v)){
				closedSet.add(v);
				if (isConnected(v, b, closedSet)) return true;
			}
		}
		
		return false;
	}
	
	public boolean isAdjacent(String a, String b) {
		Vertex current = getVertexByName(a);
		
		for (Vertex v : current.adjacentVertices) {
			if (v.name.equals(b)) return true;
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
		Vertex v_a = getVertexByName(a);
		Vertex v_b = getVertexByName(b);
		
		v_a.addEdge(v_b);
		v_b.addEdge(v_a);
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
		for (Vertex v : vertex.adjacentVertices) {
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
			for (Vertex v: current.adjacentVertices){
				if (!closedSet.contains(v)){
					closedSet.add(v);
					q.enqueue(v);
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
		public ArrayList<Vertex> adjacentVertices = new ArrayList<Vertex>();
		public String name;
		
		public Vertex(String name){
			this.name = name;
		}
		
		public void addEdge(Vertex v) {
			adjacentVertices.add(v);
		}
		
		public void removeEdge(Vertex v) {
			adjacentVertices.remove(v);
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