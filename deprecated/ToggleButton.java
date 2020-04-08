import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class ToggleButton {
	private int x,y,width,height;
	private boolean toggled = false, hover = false;
	private String alttext;
	private Font calibri = new Font("calibri", 11);
	

	public ToggleButton(int x, int y, int width, int height) {
		this(x,y,width,height,"");
	}

	public ToggleButton(int x, int y, int width, int height, String alttext) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.alttext = alttext;
	}
	
	public void render(Graphics2D G) {
		if (toggled)
			G.setColor(Color.white);
		else
			G.setColor(Color.gray);
		
		G.fillRect(x,y,width,height);
		
		if (hover) {
			FontMetrics fm = new FontMetrics(calibri);
			int width = fm.stringWidth(alttext);
			int height = fm.getHeight();
			G.setColor(Color.white);
			G.fillRect(x + this.width - width, 
		}
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void set(boolean toggled) {
		this.toggled = toggled;
	}
	
	public void toggle() {
		this.toggled = !toggled;
	}
	
	public boolean click(int x, int y) {
		if (x > this.x && x < this.x + width &&
			y > this.y && y < this.y + height ) 
			return true;
		return false;	
	}
	
	public void hover() {
		this.hover = true;
	}
}