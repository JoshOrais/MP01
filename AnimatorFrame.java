import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.File;
import datastructures.graph.UndirectedWeightedGraph;

public class AnimatorFrame extends JFrame{
	private AstarSolver solver = null;
	private int waitTime;
	private UndirectedWeightedGraph g = null;
	private GraphLayout a = new GraphLayout(new UndirectedWeightedGraph());
	private QueueVis qvis = new QueueVis();
	private Thread main;
	private boolean startSet = false, endSet = false, doonce = true;
	private int delay = DELAY_MAX * DELAY_MAX;
	public static final int DELAY_MAX = 45, DELAY_MIN = 1;
	
	public AnimatorFrame() {
		super("Machine Problem No.1 : A* Algorithm Visualization");
		setLayout(null);
		JButton b = new JButton("INPUT");
		JButton start = new JButton("START");
		JButton step = new JButton("STEP");
		JFileChooser fc = new JFileChooser();
		JTextField startInput = new JTextField();
		JTextField destInput = new JTextField();
		
		b.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						try {
							File file = fc.getSelectedFile();
							g = new GraphReader(file).getDirectedWeightedGraph();
							a.setGraph(g);
						} catch (Exception ex) {
							qvis.setStatusMessage("Invalid File!");
							ex.printStackTrace();
						}
							
					}
				}
			}
		);
		
		startInput.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startSet = a.setStartVertex(startInput.getText());
				}
			}
		);
		
		destInput.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					endSet = a.setDestVertex(destInput.getText());
				}
			}
		);
		
		start.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (solver != null) {
						if (!solver.isDone())
							return;
					}
					if (!startSet && !endSet) {
						qvis.setStatusMessage("START and END vertices not set!");
						return;
					}
					if (!startSet) {
						qvis.setStatusMessage("START vertex not set!");
						return;
					}
					if (!endSet) {
						qvis.setStatusMessage("END vertex not set!");
						return;
					}
					solver = new AstarSolver(g);
					solver.setHeuristics(a.getEuclidean(solver.getNames(), destInput.getText()));
					solver.init(startInput.getText(),destInput.getText());
					qvis.set(solver.getNames(), solver.getFCosts(), solver.getGCosts(), solver.getHeuristics());
					qvis.setStatusMessage("STARTING");
					main = new Thread(new AnimatorThread(AnimatorFrame.this));
					main.start();
				}
			}
		);
		
		
		JSlider speedslider  = new JSlider(JSlider.VERTICAL, DELAY_MIN, DELAY_MAX, DELAY_MAX);	
		speedslider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					delay = speedslider.getValue();
					delay = delay * delay;
				}
			}
		);
		speedslider.setMajorTickSpacing(1);
		speedslider.setPaintTicks(true);
		speedslider.setInverted(true);

		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 1 ), new JLabel("SPEEDEST") );
		labelTable.put( new Integer( DELAY_MAX/3 ), new JLabel("SPEEEDER") );
		labelTable.put( new Integer( DELAY_MAX ), new JLabel("SPEED") );
		speedslider.setLabelTable( labelTable );

		speedslider.setPaintLabels(true);
		
		add(b).setBounds(810, 0, 120, 40);
		add(a).setBounds(305, 0, 500, 550);
		add(new JLabel("Starting vertex:")).setBounds(810, 60, 120, 20);
		add(startInput).setBounds(810, 85, 120, 20);
		add(new JLabel("Destination vertex:")).setBounds(810, 110, 120, 20);
		add(destInput).setBounds(810, 135, 120, 20);
		add(start).setBounds(810, 200, 120, 40);
		add(qvis).setBounds(0, 0, 300,650);
		add(new JLabel("Simulation Speed")).setBounds(810, 245, 120, 20);
		add(speedslider).setBounds(810, 270, 120, 270);
		
		
		setSize(950,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void step() {
		solver.step();
		qvis.set(solver.getNames(), solver.getFCosts(), solver.getGCosts(), solver.getHeuristics());
		a.highlightNodes(solver.getHighlighted());
		a.path(solver.getPath());
		a.setTotalCost(solver.getCurrentCost());
		switch (solver.getCurrentStep()) {
			case AstarSolver.FIND_LEAST_FCOST:
				qvis.setStatusMessage("Finding vertex with least F-cost");
				break;
			case AstarSolver.GET_ADJACENT_VERTICES:
				qvis.setStatusMessage("Getting vertices adjacecnt to \'" + solver.getLast() + "\'");
				break;
			case AstarSolver.UPDATE_COST :
				qvis.setStatusMessage("Updating cost of vertex \'" + solver.getHighlighted()[0] + "\'");
				break;
			case AstarSolver.DONE :
				qvis.setStatusMessage("DONE");
				break;
			case AstarSolver.FAIL :
				qvis.setStatusMessage("FAILED to find path");
				break;
			default:
				qvis.setStatusMessage("WAITING");
				break;
		}
	}
	
	
	
	private class AnimatorThread implements Runnable {
		AnimatorFrame frame;
		
		public AnimatorThread(AnimatorFrame frame) {
			this.frame = frame;
		}
		
		@Override
		public void run() {
			int delayed = 0;
			while (!frame.solver.isDone()) {
				try {
					if (delayed >= frame.getDelay()) {
						frame.step();
						frame.repaint();
						delayed = 0;
					}
					Thread.sleep(1);
					delayed++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}