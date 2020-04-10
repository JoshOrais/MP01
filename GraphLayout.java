import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import datastructures.graph.DirectedWeightedGraph;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


// a JPanel that layouts a graph using force based whatever just do your job
public class GraphLayout extends JPanel {
	private DirectedWeightedGraph graph;
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private Vertex start = null;
	private Vertex end = null;
	private String[] path = null;
	
	public static final double K = 0.21f;
	public static final double DIST = 60.f;
	public static final int MAX_ITERATIONS = 10000;
	public static final float PADDINGPERCENT = 0.05f;
	public static final int CIRCLE_SIZE = 30;
	public static final Color CIRCLE_COLOR = Color.green;
	public static final Color LINE_COLOR = Color.black;
	public static final Color HIGHLIGHT_COLOR = Color.red;
	public static final Color HIGHLIGHT_CIRCLE_COLOR = Color.yellow;
	public static final Font FONT = new Font("calibri", Font.BOLD, CIRCLE_SIZE / 2);
	
	public GraphLayout(DirectedWeightedGraph g) {
		super();
		graph = g;
		init();
		
	}
	
	public void init() {
		vertices = new ArrayList<Vertex>();
		String[] vertexnames = graph.getVertices();
		float angle = 0;
		float angle_increment = 360.f / (vertexnames.length);
		for (String name : vertexnames) {
			int x = (int)(DIST * Math.cos(Math.toRadians(angle)));
			int y = (int)(DIST * Math.sin(Math.toRadians(angle)));
			this.vertices.add(new Vertex(name, x, y));
			angle += angle_increment;
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
					//hooke's law
					double force = K * (dist - DIST);
					if (force > 100.) force = 100.;
					forcex = dx * (force / dist);
					forcey = dy * (force / dist);
				}
				else {
					//coulomb;s law?
					double force = -(1000.f /(dist));
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
	
	public float[] getEuclidean(String[] names, String end) {
		float[] ret = new float[names.length];
		Vertex fin = null;
		for (Vertex v: vertices) { 
			if (v.id.equals(end)) {
				fin = v;
				break;
			}
		}
		
		if (fin == null) return null;
		
		for (int i = 0; i < names.length; ++i) {
			String name = names[i];
			for (Vertex v: vertices) {
				if (v.id.equals(name)) {
					ret[i] = (float)Math.sqrt((v.x - fin.x)*(v.x - fin.x) + (v.y - fin.y)*(v.y - fin.y));
					System.out.println(names[i] + "," + ret[i]);
				}
			}
		}
		return ret;
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
		
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(5, 5, width - 10, height - 10);
		
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
			for (Vertex u : vertices) {
				if (graph.isAdjacent(v.id, u.id)) {
					if (path(u) && path(v))
						g.setColor(HIGHLIGHT_COLOR);
					else
						g.setColor(LINE_COLOR);
					int ux = paddingx + map(u.x, minx, maxx, paddingx, padxend);
					int uy = paddingy + map(u.y, miny, maxy, paddingy, padyend);
					g.drawLine(x, y, ux, uy); 
				}
			}
		}
		
		
		g.setFont(FONT);
		FontMetrics fm = g.getFontMetrics(FONT);
		for (Vertex v : vertices) {
			int x = paddingx + map(v.x, minx, maxx, paddingx, padxend);
			int y = paddingy + map(v.y, miny, maxy, paddingy, padyend);
			if (highlight(v))
				g.setColor(HIGHLIGHT_CIRCLE_COLOR);
			else
				g.setColor(CIRCLE_COLOR);
			g.fillOval(x - CIRCLE_SIZE / 2 ,y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
			g.setColor(LINE_COLOR);
			g.drawOval(x - CIRCLE_SIZE / 2 ,y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
			int w2 = fm.stringWidth(v.id) / 2;
			g.drawString(v.id, x - w2 ,y + CIRCLE_SIZE / 4);
		}
		
		if (start != null) {
			int x = paddingx + map(start.x, minx, maxx, paddingx, padxend);
			int y = paddingy + map(start.y, miny, maxy, paddingy, padyend);
			g.setColor(HIGHLIGHT_COLOR);
			g.drawOval(x - CIRCLE_SIZE / 2 ,y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
			int w = fm.charWidth('S');
			int sx = x > (maxx - minx) / 2 ? x - w - CIRCLE_SIZE / 2 : x + w + CIRCLE_SIZE / 2;
			int sy = x > (maxy - miny) / 2 ? y - CIRCLE_SIZE / 2 : y + CIRCLE_SIZE;
			g.drawString("S", sx, sy);
		}
		
		if (end != null) {
			int x = paddingx + map(end.x, minx, maxx, paddingx, padxend);
			int y = paddingy + map(end.y, miny, maxy, paddingy, padyend);
			g.setColor(HIGHLIGHT_COLOR);
			g.drawOval(x - CIRCLE_SIZE / 2 ,y - CIRCLE_SIZE / 2, CIRCLE_SIZE, CIRCLE_SIZE);
			int w = fm.charWidth('D');
			int sx = x > (maxx - minx) / 2 ? x - w - CIRCLE_SIZE / 2 : x + w + CIRCLE_SIZE / 2;
			int sy = y > (maxy - miny) / 2 ? y - CIRCLE_SIZE / 2 : y + CIRCLE_SIZE;
			g.drawString("D", sx, sy);
		}
	}
	
	public void setGraph(DirectedWeightedGraph g) {
		this.graph = g;
		init();
		repaint();
	}
	
	private Vertex getVertex(String name) {
		for (Vertex v : vertices) {
			if (v.id.equals(name)) return v;
		}
		return null;
	}
	
	private boolean highlight(Vertex v) {
		if (v == start || v == end) return true;
		if (path(v)) return true;
		return false;
	}
	
	private boolean path(Vertex v) {
		if (this.path == null) return false;
		for (int i = 0; i < path.length; ++i) {
			if (v.id.equals(path[i])) return true;
		}
		return false;
	}
	
	public void path(String[] path) {
		this.path = path;
		repaint();
	}
	
	public boolean setStartVertex(String vertexname) {
		start = getVertex(vertexname);
		repaint();
		if (start == null) return false;
		return true;
	}
	
	public boolean setDestVertex(String vertexname) {
		end = getVertex(vertexname);
		repaint();
		if (end == null) return false;
		return true;
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