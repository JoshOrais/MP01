import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import datastructures.graph.UndirectedWeightedGraph;

public class GraphReader {
	private File file;
	
	public GraphReader(File f) {
		this.file = f;
	}
	
	public UndirectedWeightedGraph getDirectedWeightedGraph() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(file));
		UndirectedWeightedGraph ret = new UndirectedWeightedGraph();
		boolean v_state = false;
		boolean e_state = false;
		
		String curr;
		while ((curr = br.readLine()) != null) {
			if (curr.charAt(0) == '#') continue;
			
			if (curr.trim().equals("VERTICES:")) {
				v_state = true;
				System.out.println("found ver");
				continue;
			}
			
			
			if (v_state) {
				if (curr.trim().equals("EDGES:")) {
					e_state = true;
					v_state = false;
					continue;
				}
				String[] names = curr.split(" ");
				for (String name : names)
					ret.addVertex(name);
			}
			
			if (e_state) { /// switch case better?
				String[] parts = curr.split(" ", 3);
				int weight = Integer.parseInt(parts[2]);
				ret.addEdge(parts[0], parts[1], weight);
			}
		}
		
		return ret;
	}
}