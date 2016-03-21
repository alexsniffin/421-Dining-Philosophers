package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Config;
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
	
	private int d;
	
	private boolean load = false;
	
	public GPanel(int d) {
		this.d = d;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 setBackground(Color.WHITE);
		 Graphics2D g2d = (Graphics2D)g;
		 
	     drawTable(g2d);
	}
	
	public void drawTable(Graphics2D g2d) {
		if (!load) {
			createPhilosophers(g2d, this.getWidth()/2, this.getHeight()/2, d/2);
			createForks(g2d, this.getWidth()/2, this.getHeight()/2, d/2);
			load = true;
		}
		
		//Table
		{
			double x = this.getWidth()/2 - (d/2), y = this.getHeight()/2 - (d/2);
			Ellipse2D.Double circle = new Ellipse2D.Double(x, y, d, d);
			g2d.fill(circle);
		}
		
		//Philosophers and Forks
		{
			for (Philosopher philosopher : philosophers) {
				Ellipse2D.Double circle = new Ellipse2D.Double(
						philosopher.getCircle().getX(), 
						philosopher.getCircle().getY(), 
						philosopher.getCircle().getDiameter(), 
						philosopher.getCircle().getDiameter());
				g2d.setColor(philosopher.getCircle().getColor());
				g2d.fill(circle);
			}
			
			for (Fork fork : forks) {
				Shape l = new Line2D.Double(
						fork.getFg().getX(),
						fork.getFg().getY(),
						fork.getFg().getX2(),
						fork.getFg().getY2());
				g2d.setColor(Color.GRAY);
	            g2d.draw(l);
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
	public void createPhilosophers(Graphics2D g2d, double cx, double cy, double r) {
		for (Philosopher philosopher : philosophers) {
			double d = (double) this.d / Config.TOTAL_PHILOSOPHERS;
			double rad = (double) 360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) * philosopher.getId();
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
	public void createForks(Graphics2D g2d, double cx, double cy, double r) {
		for (Fork fork : forks) {
			double length = (double) this.d / Config.TOTAL_PHILOSOPHERS;
			
			//Min+Max lengths
			if (length > 50)
				length = 50;
			else if (length < 15)
				length = 15;
			
			double rad = (double) 360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) * fork.getId() + 
					(360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) / 2);
			double x = (double) cx + (r - 5) * Math.cos(rad), y = (double) cy + (r - 5) * Math.sin(rad);
			double x2 = (double) cx + (r - length) * Math.cos(rad), y2 = (double) cy + (r - length) * Math.sin(rad);
			
			Shape l = new Line2D.Double(x, y, x2, y2);
			g2d.setColor(Color.GRAY);
            g2d.draw(l);
			
			//g2d.drawpo
			
			fork.setFg(new ForkGraphic(new ForkGraphic(null, length, rad, x, y, x2, y2, cx, cy, r, fork.getId()),
					length, rad, x, y, x2, y2, cx, cy, r, fork.getId()));
		}
		
	}
}
