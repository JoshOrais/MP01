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
							DRAW_MODE = 1;
	private int current_mode = IDLE_MODE;
	
	private Image dbImage = null;
	private Graphics dbg;
	
	private BufferedInput bufferedInput = null;
	
	private Point lastInput;
	
	ToggleButton button_a = new ToggleButton(500, 500, 20, 20); 	
	
	public void render(Graphics G) {
		G.setColor(Color.green);
		Point a = this.getMousePosition();
		
		if (current_mode == DRAW_MODE && a != null) 
			G.drawLine(lastInput.x, lastInput.y, a.x, a.y);
		G.setColor(Color.red);
		if (a != null) {
			G.drawLine(0, 0, a.x, a.y);
			G.drawLine(0, 800, a.x, a.y);
			G.drawLine(800, 0, a.x, a.y);
			G.drawLine(800, 800, a.x, a.y);
		}
		
		button_a.render(G);	
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
			dbg = dbImage.getGraphics();
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
		
		button_a.click(bufferedInput.x_pos , bufferedInput.y_pos);
		
		if (current_mode == IDLE_MODE) {
			lastInput = new Point(bufferedInput.x_pos , bufferedInput.y_pos);
			current_mode = DRAW_MODE;
		}
		else {
			current_mode = IDLE_MODE;
			lastInput = null;
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
