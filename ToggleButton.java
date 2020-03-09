import java.awt.Graphics;
import java.awt.Color;

public class ToggleButton {
	private int x,y,width,height;
	private boolean toggled = false;

	public ToggleButton(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics G) {
		if (toggled)
			G.setColor(Color.white);
		else
			G.setColor(Color.gray);
		
		G.fillRect(x,y,width,height);
	}
	
	
	public void click(int x, int y) {
		if (x > this.x && x < this.x + width &&
			y > this.y && y < this.y + height )
			this.toggled = !this.toggled;
	}
}