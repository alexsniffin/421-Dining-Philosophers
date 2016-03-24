package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Driver;
import main.Fork;
import main.Philosopher;

public class GPanel extends JPanel {
	
	/**
	 * Reference to the static Philosopher List
	 */
	private static ArrayList<Philosopher> philosophers = Driver.philosophers;
	
	/**
	 * Reference to the static Fork List
	 */
	private static ArrayList<Fork> forks = Driver.forks;
	
	/**
	 * Diameter of the table
	 */
	private int d;
	
	/**
	 * Load boolean
	 */
	private boolean load = false;
	
	private Graphics2D g2d;

	/**
	 * Create a new graphics panel
	 * 
	 * @param d Diameter of the table
	 */
	public GPanel(int d) {
		this.d = d;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 setBackground(Color.WHITE);
		 g2d = (Graphics2D)g;
		 
	     drawTable(g2d);
	}
	
	/**
	 * Draws the graphics
	 * 
	 * @param g2d
	 */
	public void drawTable(Graphics2D g2d) {		
		//Table
		{
			double x = this.getWidth()/2 - (d/2), y = this.getHeight()/2 - (d/2);
			Ellipse2D.Double circle = new Ellipse2D.Double(x, y, d, d);
			g2d.fill(circle);
		}
		
		//Philosophers and Forks
		if (load) {
			for (Philosopher philosopher : philosophers) {
				Ellipse2D.Double circle = new Ellipse2D.Double(
						philosopher.getCircle().getX(), 
						philosopher.getCircle().getY(), 
						philosopher.getCircle().getDiameter(), 
						philosopher.getCircle().getDiameter());
				g2d.setColor(philosopher.getCircle().getColor());
				g2d.fill(circle);
				
				g2d.drawString(philosopher.getId() + "", (int) philosopher.getCircle().getX(), (int) philosopher.getCircle().getY());
			}
			
			for (Fork fork : forks) {
				Shape l = new Line2D.Double(
						fork.getFg().getX(),
						fork.getFg().getY(),
						fork.getFg().getX2(),
						fork.getFg().getY2());
				g2d.setColor(Color.GRAY);
	            g2d.draw(l);
	            
	            g2d.drawString(fork.getId() + "", (int) fork.getFg().getX(), (int) fork.getFg().getY());
			}
		}
	}

	/**
	 * Create all of the Philosopher circle drawings
	 * 
	 * @param g2d
	 * @param cx
	 * @param cy
	 * @param r
	 */
	public void createPhilosophers(Graphics2D g2d, double cx, double cy, double r, int total) {
		for (Philosopher philosopher : philosophers) {
			double d = (double) this.d / total;
			double rad = (double) 360 / total * (Math.PI / 180) * philosopher.getId();
			double x = (double) cx + (r + 10) * Math.cos(rad) - d/2, y = (double) cy + (r + 10) * Math.sin(rad) - d/2;
			
			Ellipse2D.Double circle = new Ellipse2D.Double(x, y, d, d);
			g2d.setColor(new Color(95, 95, 255));
			g2d.fill(circle);
			
			philosopher.setCircle(new PhilosopherGraphic(d, rad, x, y));
		}
	}
	
	/**
	 * Create all of the Fork drawings
	 * 
	 * @param g2d
	 * @param cx
	 * @param cy
	 * @param r
	 */
	public void createForks(Graphics2D g2d, double cx, double cy, double r, int total) {
		for (Fork fork : forks) {
			double length = (double) this.d / total;
			
			//Min+Max lengths
			if (length > 50)
				length = 50;
			else if (length < 15)
				length = 15;
			
			double rad = (double) 360 / total * (Math.PI / 180) * fork.getId() + 
					(360 / total * (Math.PI / 180) / 2);
			double x = (double) cx + (r - 5) * Math.cos(rad), y = (double) cy + (r - 5) * Math.sin(rad);
			double x2 = (double) cx + (r - length) * Math.cos(rad), y2 = (double) cy + (r - length) * Math.sin(rad);
			
			Shape l = new Line2D.Double(x, y, x2, y2);
			g2d.setColor(Color.GRAY);
            g2d.draw(l);
			
			fork.setFg(new ForkGraphic(new ForkGraphic(null, length, rad, x, y, x2, y2, cx, cy, r, fork.getId()),
					length, rad, x, y, x2, y2, cx, cy, r, fork.getId()));
		}
	}
	
	public boolean getLoad() {
		return load;
	}

	public void setLoad(boolean load) {
		this.load = load;
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}
}
