import java.awt.*;

public class VertexObject {
	private int x, y, rad = 20;
	private int vertex;
	
	public VertexObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics2D G) {
		G.setColor(Color.white);
		G.fillOval(x - rad, y - rad, rad * 2, rad * 2);
	}
	
	public int getVertex() {
		return vertex;
	}
	
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	public boolean click(int x, int y) {
		return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) < rad * rad; 
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}
}