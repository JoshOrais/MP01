import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import datastructures.graph.DirectedWeightedGraph;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


// a JPanel that layouts a graph using force based whatever just do your job
public class GraphLayout extends JPanel {
	private DirectedWeightedGraph graph;
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private final double K = 0.57f;
	private final double DIST = 60.f;
	private final int MAX_ITERATIONS = 10000;
	private final float PADDINGPERCENT = 0.05f;
	private final int CIRCLE_SIZE = 20;
	private final Color CIRCLE_COLOR = Color.green;
	private final Color LINE_COLOR = Color.black;
	
	public GraphLayout(DirectedWeightedGraph g) {
		super();
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				repaint();
			}  
			public void mouseEntered(MouseEvent e) {}  
			public void mouseExited(MouseEvent e) {}  
			public void mousePressed(MouseEvent e) {} 
			public void mouseReleased(MouseEvent e) {}
		});
		graph = g;
		init();
		
	}
	
	public void init() {
		String[] vertexnames = graph.getVertices();
		int x = 200;
		int y = 200;
		int even = 1;
		for (String name : vertexnames) {
			this.vertices.add(new Vertex(name, x, y + even * 10));
			x += 10;
			even *= -1;
		}
		
		
		for (int p = 0; p < MAX_ITERATIONS; ++p) {
		int[] xchange = new int[vertices.size()];
		int[] ychange = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); ++i) {
			Vertex v = vertices.get(i);
			double x_f = 0.f, y_f = 0.f;
			for (int j = 0; j < vertices.size(); ++j) {
				Vertex u = vertices.get(j);
				if (v.id == u.id) continue;
				double dx = u.x - v.x;
				double dy = u.y - v.y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				double forcex, forcey;
				if (graph.isAdjacent(v.id, u.id) || graph.isAdjacent(u.id, v.id)) {
					double force = K * (dist - DIST);
					if (force > 100.) force = 100.;
					forcex = dx * (force / dist);
					forcey = dy * (force / dist);
				}
				else {
					double force = -K * 1.8 *(DIST / dist);
					forcex = dx * (force / dist);
					forcey = dy * (force / dist);
				}
				
				x_f += forcex;
				y_f += forcey;
			}
			xchange[i] = (int)(x_f);
			ychange[i] = (int)(y_f);
		}
		
		for (int i = 0; i < xchange.length; ++i) {
			Vertex v = vertices.get(i);
			v.x += xchange[i];
			v.y += ychange[i];
		}
		}
	}
	
	
	//maps x from numeric range [fromstart, fromend] to [tostart, toend]
	private int map(int x, int fromstart, int fromend, int tostart, int toend) {
		
		return (int)((float)((toend - tostart) * (x - fromstart)) / (float)(fromend - fromstart)); // something something precision
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int minx = 0, miny = 0,
			maxx = 0, maxy = 0;
		boolean first = true;
		
		g.setColor(Color.green);
		int width = this.getWidth();
		int height = this.getHeight();
		int paddingx = (int)(width * PADDINGPERCENT);
		int paddingy = (int)(height * PADDINGPERCENT);
		int padxend = width - paddingx;
		int padyend = height - paddingy;
		
		for (Vertex v : vertices) {
			if (first) {
				first = false;
				minx = v.x; miny = v.y;
				maxx = v.x; maxy = v.y;
			}
			if (v.x < minx) minx = v.x;
			if (v.x > maxx) maxx = v.x;
			if (v.y < miny) miny = v.y;
			if (v.y > maxy) maxy = v.y;
		}
		
		
		for (Vertex v : vertices) {
			int x = paddingx + map(v.x, minx, maxx, paddingx, padxend);
			int y = paddingy + map(v.y, miny, maxy, paddingy, padyend);
			g.setColor(LINE_COLOR);
			for (Vertex u : vertices) {
				if (graph.isAdjacent(v.id, u.id)) {
					int ux = paddingx + map(u.x, minx, maxx, paddingx, padxend);
					int uy = paddingy + map(u.y, miny, maxy, paddingy, padyend);
					g.drawLine(x, y, ux, uy); 
				}
			}
			g.setColor(CIRCLE_COLOR);
			g.fillOval(x - CIRCLE_SIZE / 2 ,y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
		}
	}
	
	public class Vertex {
		public int x, y;
		public String id;
		public Vertex(String id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
} 