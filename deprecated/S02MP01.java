import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class S02MP01 extends JPanel implements Runnable{
	public Thread mainThread;
	
	public static final int IDLE_MODE = 0,
							EDGE_MODE = 1,
							VERTEX_MODE = 2;
	private int current_mode = IDLE_MODE;
	
	private Image dbImage = null;
	private Graphics2D dbg;
	
	private BufferedInput bufferedInput = null;
	
	private VertexObject activeVertex = null;
	private Graph graph = new Graph();
	
	
	ToggleButton pbutton = new ToggleButton(500, 500, 20, 20); 	
	ToggleButton vbutton = new ToggleButton(530, 500, 20, 20); 	
	ToggleButton ebutton = new ToggleButton(560, 500, 20, 20); 	
	ToggleButton dbutton = new ToggleButton(590, 500, 20, 20); 	
	
	ToggleButton[] buttons = { vbutton, ebutton, pbutton, dbutton };
	
	public void render(Graphics2D G) {
		G.setColor(Color.green);
		Point a = this.getMousePosition();
		
		G.setColor(Color.red);
		if (a != null) {
			G.drawLine(0, 0, a.x, a.y);
			G.drawLine(0, 800, a.x, a.y);
			G.drawLine(800, 0, a.x, a.y);
			G.drawLine(800, 800, a.x, a.y);
		}
		
		if (activeVertex != null && a != null) 
			G.drawLine(activeVertex.getX(), activeVertex.getY(), a.x, a.y);
		
		for (Vertex v : graph.getVertices()) {
			if (v != null) {
				v.getObject().render(G);
				for (Edge e : v.getEdges()) {
					int to = e.vertex;
					VertexObject oto = graph.vertex(to).getObject();
					
					G.drawLine(v.getObject().getX(), v.getObject().getY(), oto.getX(), oto.getY());
				}
			}
		}
		
		for (ToggleButton b : buttons) {
			b.render(G);
		}
	}
	
	public void update() {
		
	}
	
	private void doubleBuffering(){
		if (dbImage == null){
			dbImage = createImage(800, 800);
			if (dbImage == null){
				System.out.println("ERROR: Double Buffering Image is null!");
				return;
			}
			dbg = (Graphics2D)dbImage.getGraphics();
			dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		dbg.setColor(new Color( 0, 0, 0, 255));
		dbg.fillRect(0,0,800, 800);
		if (dbg!=null)
			render(dbg);
	}
	
	private void paintPanel(){
		Graphics g;
		try{
			g = this.getGraphics();
			g.setColor(Color.white);
			if ( (g!=null)  && dbImage != null){
				g.drawImage(dbImage, 0,0, 800, 800, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch (Exception e)
		{ System.out.println("Graphics context error: " + e); }
	}
	
	public void input() {
		if (bufferedInput == null) return;
		
		for (ToggleButton button : buttons) {
			if ( button.click(bufferedInput.x_pos , bufferedInput.y_pos)) {
				
				if (!button.isToggled()) {
					button.set(true);
					for (ToggleButton other: buttons) {
						if (other != button) other.set(false);
					}
				
					if (vbutton.isToggled()) current_mode = VERTEX_MODE;
					else if (ebutton.isToggled()) current_mode = EDGE_MODE;
					else current_mode = IDLE_MODE;
				}
				
				if (activeVertex != null) activeVertex = null;
				bufferedInput = null;
				return;
			}
		}
		
		if (current_mode == VERTEX_MODE)
			graph.addVertex(new VertexObject(bufferedInput.x_pos , bufferedInput.y_pos));
		
		if (current_mode == EDGE_MODE) {
				for (Vertex v : graph.getVertices()) {
					if (v != null) {
						if (v.getObject().click(bufferedInput.x_pos , bufferedInput.y_pos)) {
							if (activeVertex == null) activeVertex = v.getObject();
							else {
								graph.vertex(activeVertex.getVertex()).addEdge(v.getObject().getVertex(), 1);
								activeVertex = null;
							}
						}
					}
				}
		}
		
		bufferedInput = null;
	}
	public S02MP01() {
		
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1)
					bufferedInput = new BufferedInput(false, e.getX(), e.getY());
				else if (e.getButton() == MouseEvent.BUTTON3)
					bufferedInput = new BufferedInput(true , e.getX(), e.getY());
			}  
			public void mouseEntered(MouseEvent e) {}  
			public void mouseExited(MouseEvent e) {}  
			public void mousePressed(MouseEvent e) {} 
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	@Override
	public void run() {
		pbutton.set(true);
		while(true) {
			try {
				update();
				input();
				doubleBuffering();
				paintPanel();
				//Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		System.out.println("MP starts Thread");
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Machine Problems - Season 2 Episode 1");
		S02MP01 main = new S02MP01();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(900, 900));
		f.setSize(900, 900);
		f.setLayout(null);
		f.add(main);
		main.setLocation(0,0);
		main.setSize(800, 800);
		f.pack();
		f.setVisible(true);
		System.out.println("ADDING PANELS");
	}
}
