package uk.ac.cam.dab80.oop.tick5;

import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
  
	private World world = null;
  
  
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		// Paint the background white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (world == null){
			return;
		}
		// Limited by most tighly packed dimension
		int gridWidth = world.getWidth();
		int gridHeight = world.getHeight();
		double scale = Math.min(((double)getWidth())/gridWidth,((double)getHeight())/gridHeight);
	
		// lines
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x <= gridWidth; x++){
			g.drawRect((int)(x*scale),0,0,(int)(gridHeight*scale));
		}
		for (int y = 0; y <= gridHeight; y++){
			g.drawRect(0,(int)(y*scale),(int)(gridWidth*scale),0);
		}
	
		// draw live cells
		g.setColor(java.awt.Color.BLACK);
		for (int x = 0; x < gridWidth; x++){
			for (int y = 0; y < gridHeight; y++){
				if (world.getCell(x,y)){
					// corners calculated to get width and height without leaving uneven gaps
					int x0 = (int)(x*scale);
					int y0 = (int)(y*scale);
					int x1 = (int)((x+1)*scale);
					int y1 = (int)((y+1)*scale);
					// leave line between each tile
					g.fillRect(x0+1,y0+1,x1-x0-1,y1-y0-1);
				}
			}
		}
		// write generation count
		g.drawString("Generation: "+world.getGenerationCount(),20, getHeight() - 20);
	}

	public void display(World w) {
		world = w;
		repaint();
	}
}