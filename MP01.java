import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.io.File;
import datastructures.graph.DirectedWeightedGraph;

public class MP01 {
	public static DirectedWeightedGraph g = null;
	public static AstarSolver solver = null;
	public static GraphLayout a = new GraphLayout(new DirectedWeightedGraph());

	public static void main(String[] args) {
		JFrame frame = new JFrame("MP");
		frame.setLayout(null);
		frame.add(a);
		a.setBounds(0, 0, 500, 500);
		JButton b = new JButton("INPUT");
		frame.add(b);
		b.setBounds(505, 0, 120, 40);
		JFileChooser fc = new JFileChooser();
		
		b.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						try {
							File file = fc.getSelectedFile();
							g = new GraphReader(file).getDirectedWeightedGraph();
							a.setGraph(g);
							System.out.println(Arrays.toString(g.getVertices()));
						} catch (Exception ex) {
							System.out.println("SOMETHING HAPPENED");
						}
							
					}
				}
			}
		);
		
		JTextField startInput = new JTextField();
		JTextField destInput = new JTextField();
		frame.add(new JLabel("Starting vertex:")).setBounds(505, 60, 120, 20);
		frame.add(startInput).setBounds(505, 85, 120, 20);
		frame.add(new JLabel("Destination vertex:")).setBounds(505, 110, 120, 20);
		frame.add(destInput).setBounds(505, 135, 120, 20);
		
		startInput.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					a.setStartVertex(startInput.getText());
				}
			}
		);
		
		destInput.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					a.setDestVertex(destInput.getText());
				}
			}
		);
		
		
		
		JButton start = new JButton("START");
		frame.add(start).setBounds(505, 200, 120, 40);
		JButton step = new JButton("STEP");
		frame.add(step).setBounds(505, 245, 120, 40);
		
		start.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					solver = new AstarSolver(g);
					solver.setHeuristics(a.getEuclidean(solver.getNames(), destInput.getText()));
					solver.init(startInput.getText(),destInput.getText());
					System.out.println(Arrays.toString(solver.getHeuristics()));
				}
			}
		);
		step.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					solver.step();
					a.path(solver.getPath());
					System.out.println(Arrays.toString(solver.getPath()));
				}
			}
		);
		
		frame.setPreferredSize(new Dimension(480,480));
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}