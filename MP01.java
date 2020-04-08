import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import datastructures.graph.DirectedWeightedGraph;

public class MP01 {


	public static void main(String[] args) {
		JFrame frame = new JFrame("MP");
		frame.setLayout(null);
		GraphLayout a = new GraphLayout(new DirectedWeightedGraph());
		//try {
			//DirectedWeightedGraph g = new GraphReader(new File("test.txt")).getDirectedWeightedGraph();
			//a.setGraph(g);
		//}catch(Exception e) {
		//	e.printStackTrace();
		//}
		frame.add(a);
		a.setBounds(0, 0, 500, 500);
		JButton b = new JButton("INPUT");
		frame.add(b);
		b.setBounds(500, 0, 120, 40);
		JFileChooser fc = new JFileChooser();
		
		b.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						try {
							File file = fc.getSelectedFile();
							DirectedWeightedGraph g = new GraphReader(file).getDirectedWeightedGraph();
							a.setGraph(g);
						} catch (Exception ex) {
							System.out.println("SOMETHING HAPPENED");
						}
							
					}
				}
			}
		);
		
		frame.setPreferredSize(new Dimension(480,480));
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}