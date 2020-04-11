import javax.swing.JPanel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Color;
import java.text.DecimalFormat;

public class QueueVis extends JPanel {
	private float[] fcosts = null,
					gcosts = null,
					heuristics = null;
	private String[] names = null;
	private String status = "IDLE";
	private int[] indexes = null;

	public static final int MAX_ROWS = 19,
							MAX_COLS = 4,
							PAD = 3;
	public static final Font FONT = new Font("calibri", Font.PLAIN, 14);
	public static final DecimalFormat FORMAT = new DecimalFormat(".##");
	public static final Color CELL_COLOR = Color.black,
							  CELL_COLOR2 = Color.gray,
							  BG_COLOR = Color.white,
							  TEXT_COLOR = Color.white;
	

	public void paintComponent(Graphics g) {
		// i like doing this because saying 'width' is better than calling getWidth() imo;
		int width = this.getWidth();
		int height = this.getHeight();
		int cellHeight = height / (MAX_ROWS + 1);
		int cellWidth = width / MAX_COLS;
		
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, width, height);
		g.setFont(FONT);
		FontMetrics fm = g.getFontMetrics(FONT);
		int textHeight = fm.getHeight();
		
		
		g.setColor(CELL_COLOR);
		g.drawRect(0			, 0, cellWidth * MAX_COLS, cellHeight);
		g.drawString("Status: " + status, PAD, (cellHeight + textHeight) / 2);
		
		g.drawRect(0			, cellHeight, cellWidth, cellHeight);
		g.drawRect(cellWidth	, cellHeight, cellWidth, cellHeight);
		g.drawRect(cellWidth * 2, cellHeight, cellWidth, cellHeight);
		g.drawRect(cellWidth * 3, cellHeight, cellWidth, cellHeight);
		g.drawString("Names"     , PAD				  , cellHeight + (cellHeight + textHeight) / 2);
		g.drawString("FCosts"    , cellWidth + PAD    , cellHeight + (cellHeight + textHeight) / 2);
		g.drawString("GCosts"    , cellWidth * 2 + PAD, cellHeight + (cellHeight + textHeight) / 2);
		g.drawString("Heuristics", cellWidth * 3 + PAD, cellHeight + (cellHeight + textHeight) / 2);
		
		if (indexes == null) {
			return;
		}
		
		for (int i = 0; i < Math.min(names.length, MAX_ROWS); ++i) {
			if (i % 2 == 0) g.setColor(CELL_COLOR2);
			else g.setColor(CELL_COLOR);
			g.fillRect(0			, cellHeight * (i + 2), cellWidth * MAX_COLS, cellHeight);
			g.setColor(TEXT_COLOR);
			g.drawString(names[indexes[i]] 				       , PAD				, cellHeight * (i + 2) + (cellHeight + textHeight) / 2);
			g.drawString(FORMAT.format(fcosts[indexes[i]])     , cellWidth + PAD    , cellHeight * (i + 2) + (cellHeight + textHeight) / 2);
			g.drawString(FORMAT.format(gcosts[indexes[i]])     , cellWidth * 2 + PAD, cellHeight * (i + 2) + (cellHeight + textHeight) / 2);
			g.drawString(FORMAT.format(heuristics[indexes[i]]) , cellWidth * 3 + PAD, cellHeight * (i + 2) + (cellHeight + textHeight) / 2);
		}
	}
	
	private void sort() {
		//insertion sort
		int[] indexes = new int[names.length];
		int sortedsize = 0;
		
		for (int i = 0; i < names.length; ++i) {
			for (int j = 0; j < sortedsize; ++j) {
				if (fcosts[i] < fcosts[indexes[j]]) {
					//shift by one
					for (int k = sortedsize; k > j; k--) {
						indexes[k] = indexes[k-1];
					}
					indexes[j] = i;
					break;
				}
				
				if (j == sortedsize - 1)
					indexes[sortedsize] = i;
			}
			sortedsize++;
		}
		this.indexes = indexes;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.status = statusMessage;
		repaint();
	}
	
	public void set(String[] names, float[] fcosts, float[] gcosts, float[] heuristics) {
		this.names = names;
		this.fcosts = fcosts;
		this.gcosts = gcosts;
		this.heuristics = heuristics;
		sort();
		repaint();
	}
}