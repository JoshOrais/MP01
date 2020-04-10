import java.util.ArrayList;
import datastructures.graph.DirectedWeightedGraph;

public class AstarSolver {
	private DirectedWeightedGraph graph;
	private float[] FCosts;
	private float[] GCosts;
	private float[] heuristics = null;
	private int[] path;
	private String[] names;
	private ArrayList<String> openset;
	private ArrayList<String> closedset;
	private String start = null, end = null, last = null;
	private boolean done = true;
	private boolean found = false;
	
	public AstarSolver(DirectedWeightedGraph g) {
		this.graph = g;
		
		names = graph.getVertices();
		FCosts = new float[names.length];
		GCosts = new float[names.length];
		heuristics = new float[names.length];
		path = new int[names.length];
		for (int i = 0; i < path.length; ++i) { path[i] = -1; }
	}
	
	private String[] getVertexNames() {
		return names;
	}
	
	private int ix(String name) {
		for (int i = 0; i < names.length; ++i) {
			if (names[i].equals(name)) return i;
		}
		return -1;
	}
	
	public void init(String start, String end) {
		if (!done) return;
		
		this.start = start;
		this.end = end;
		openset = new ArrayList<String>();
		closedset = new ArrayList<String>();
		
		for (int i = 0; i < GCosts.length; ++i) {
			FCosts[i] = Float.POSITIVE_INFINITY;
			GCosts[i] = Float.POSITIVE_INFINITY;
		}
		
		openset.add(start);
		GCosts[ix(start)] = 0;
		FCosts[ix(start)] = heuristics[ix(start)];
		
		done = false;
		found = false;
		last = null;
	}
	
	public boolean setHeuristics(float[] heuristics) {
		if (!done)  {
			System.out.println("wadsad");
			return false;
		}
		if (heuristics.length != graph.getVertices().length) {
			System.out.println("WHATASDAD");
			return false;
		}
		this.heuristics = heuristics;
		return true;
	}
	
	public float[] getHeuristics() {
		return heuristics;
	}
	
	public String[] getNames() {
		return names;
	}
	
	public void step() {
		if (done) return;
		
		if (openset.size() == 0) {
			done = true;
			found = false;
			return;
		}
		
		String current = null;
		float least = Float.POSITIVE_INFINITY;
		for (String v : openset) {
			if (FCosts[ix(v)] < least) {
				current = v;
				least = FCosts[ix(v)];
			}
		}
		this.last = current;
		
		if (current.equals(end)) {
			found = true;
			done = true;
			return;
		}
		
		closedset.add(current);
		openset.remove(current);
		System.out.println(last);
		
		for (String a : graph.getAdjacentVertices(current)) {
			float tentative = GCosts[ix(current)] + graph.getEdgeWeight(current, a);
			if (tentative < GCosts[ix(a)]) {
				path[ix(a)] = ix(current);
				GCosts[ix(a)] = tentative;
				FCosts[ix(a)] = GCosts[ix(a)] + heuristics[ix(a)];
				if (!closedset.contains(a))
					openset.add(a);
				else 
					System.out.println("what happened");
			}
		}
		System.out.println(openset.toString());
		
	}
	
	public String[] getOpenset() {
		String[] ret = new String[openset.size()];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = openset.get(i);
		}
		return ret;
	}
	
	public String[] getClosedSet() {
		String[] ret = new String[closedset.size()];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedset.get(i);
		}
		return ret;
	}
	
	
	public String[] getPath() {
		if (last == null) return null;
		ArrayList<String> ret = new ArrayList<String>();
		int current = ix(last);
		while(current != -1) {
			ret.add(names[current]);
			current = path[current];
		}
		
		String[] ret2 = new String[ret.size()];
		for (int i = 0; i < ret2.length; ++i) {
			ret2[i] = ret.get(i);
		}
		return ret2;
	}
}