package graphics;

import java.awt.Color;

/**
 * Hold the details for the Philosopher's graphics
 *
 * @author alexander
 * @project DiningPhilosophers
 * @date Mar 20, 2016
 */
public class PhilosopherGraphic {

	private double diameter;
	
	private double radian;
	
	private double x, y;
	
	private Color color = new Color(95, 95, 255);
	
	public PhilosopherGraphic(double diameter, double radian, double x, double y) {
		this.diameter = diameter;
		this.radian = radian;
		this.x = x;
		this.y = y;
	}
	
	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getRadian() {
		return radian;
	}

	public void setRadian(double radian) {
		this.radian = radian;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
