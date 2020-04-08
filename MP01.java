import javax.swing.JFrame;
import java.awt.Dimension;
import datastructures.graph.DirectedWeightedGraph;

public class MP01 {


	public static void main(String[] args) {
		JFrame frame = new JFrame("MP");
		
		DirectedWeightedGraph graph = new DirectedWeightedGraph();
		try {
			graph.addVertex("1");
			graph.addVertex("2");
			graph.addVertex("3");
			graph.addVertex("4");
			graph.addVertex("5");
			graph.addVertex("6");
			graph.addVertex("7");
			graph.addVertex("8");
			graph.addVertex("9");
			graph.addEdge("1","2");
			graph.addEdge("2","3");
			graph.addEdge("3","4");
			graph.addEdge("4","5");
			graph.addEdge("5","6");
			graph.addEdge("6","7");
			graph.addEdge("7","8");
			graph.addEdge("8","9");
			graph.addEdge("9","1");
			graph.addEdge("9","2");
			graph.addEdge("9","3");
			graph.addEdge("9","4");
			graph.addEdge("9","5");
			graph.addEdge("9","6");
			graph.addEdge("9","7");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		frame.setLayout(null);
		GraphLayout a = new GraphLayout(graph);
		frame.add(a);
		a.setBounds(0, 0, 500, 500);
		
		frame.setPreferredSize(new Dimension(480,480));
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}