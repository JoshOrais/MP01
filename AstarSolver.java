import java.util.ArrayList;
import datastructures.graph.UndirectedWeightedGraph;

public class AstarSolver {
	private UndirectedWeightedGraph graph;
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
	private String[] work_array = null;
	private String[] highlighted = null;
	public static final int NULL_STEP = -1,
							FIND_LEAST_FCOST = 0,
							GET_ADJACENT_VERTICES = 1,
							UPDATE_COST = 2,
							DONE = 3,
							FAIL = 4;
	private int step = NULL_STEP;
	private int cursor = 0;
	
	public AstarSolver(UndirectedWeightedGraph g) {
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
		step = FIND_LEAST_FCOST;
	}
	
	public boolean setHeuristics(float[] heuristics) {
		if (!done)  {
			return false;
		}
		if (heuristics.length != graph.getVertices().length) {
			return false;
		}
		this.heuristics = heuristics;
		return true;
	}
	
	public void step() {
		if (done) return;
		
		if (step == FIND_LEAST_FCOST) {
			String current = null;
			float least = Float.POSITIVE_INFINITY;
			
			if (openset.size() == 0) {
				done = true;
				found = false;
				step = FAIL;
				return;
			}
			
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
				step = DONE;
				return;
			}
		
			closedset.add(current);
			openset.remove(current);
			
			step = GET_ADJACENT_VERTICES;
			String[] n = {last};
			highlighted = n;
			return;
		}
		
		if (step == GET_ADJACENT_VERTICES) {
			work_array = graph.getAdjacentVertices(last);
			highlighted = graph.getAdjacentVertices(last);
			step = UPDATE_COST;
			return;
		}
		
		if (step == UPDATE_COST) {
			
			if (cursor == work_array.length) {
				cursor = 0;
				step = FIND_LEAST_FCOST;
				highlighted = null;
				return;
			}
			
			String a = work_array[cursor++];
			String[] n = {a};
			highlighted = n;
			
			float tentative = GCosts[ix(last)] + graph.getEdgeWeight(last, a);
			if (tentative < GCosts[ix(a)]) {
				path[ix(a)] = ix(last);
				GCosts[ix(a)] = tentative;
				FCosts[ix(a)] = GCosts[ix(a)] + heuristics[ix(a)];
				if (!closedset.contains(a))
					openset.add(a);
			}
		}
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
	
	public int getCurrentStep() {
		return step;
	}
	
	public String[] getHighlighted() {
		return highlighted;
	}
	
	
	public float[] getFCosts() {
		return FCosts;
	}
	
	public float[] getGCosts() {
		return GCosts;
	}
	
	public float[] getHeuristics() {
		return heuristics;
	}
	
	public String[] getNames() {
		return names;
	}
	
	public String getLast() {
		return last;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public float getCurrentCost() {
		for (int i = 0; i < names.length; ++i) {
			if (last.equals(names[i]))
				return GCosts[i];
		}
		
		return 0.f;
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