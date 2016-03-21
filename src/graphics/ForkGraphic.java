package graphics;

import main.Config;

/**
 * Holds the details for Fork Graphics
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 21, 2016
 */
public class ForkGraphic {
	
	private int idMultiplier;
	
	private double length;
	
	private double radian;
	
	private double x, y, x2, y2;
	
	private double cx, cy, r;
	
	private ForkGraphic backup;

	public ForkGraphic(ForkGraphic backup, double length, double radian, double x, double y, double x2, double y2,
			double cx, double cy, double r, int idMultiplier) {
		this.backup = backup;
		this.length = length;
		this.radian = radian;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.cx = cx;
		this.cy = cy;
		this.r = r;
		this.idMultiplier = idMultiplier;
	}

	/**
	 * Move the left fork in closer to the Philosopher
	 */
	public void movePosi() {
		radian = (double) 360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) * idMultiplier + 
				(360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) / 2  / 2) * 3;
		x = (double) cx + (r - 5 - (length/2)) * Math.cos(radian);
		y = (double) cy + (r - 5 - (length/2)) * Math.sin(radian);
		x2 = (double) cx + (r - length - (length/2)) * Math.cos(radian);
		y2 = (double) cy + (r - length - (length/2)) * Math.sin(radian);
	}

	/**
	 * Move the right fork in closer to the Philosopher
	 */
	public void moveNeg() {
		radian = (double) 360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) * idMultiplier + 
				(360 / Config.TOTAL_PHILOSOPHERS * (Math.PI / 180) / 2 / 2);
		x = (double) cx + (r - 5 - (length/2)) * Math.cos(radian);
		y = (double) cy + (r - 5 - (length/2)) * Math.sin(radian);
		x2 = (double) cx + (r - length - (length/2)) * Math.cos(radian);
		y2 = (double) cy + (r - length - (length/2)) * Math.sin(radian);
	}
	
	/**
	 * Revert back to the original position
	 */
	public void original() {
		this.length = backup.length;
		this.radian = backup.radian;
		this.x = backup.x;
		this.y = backup.y;
		this.x2 = backup.x2;
		this.y2 = backup.y2;
		this.cx = backup.cx;
		this.cy = backup.cy;
		this.r = backup.r;
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
	
}
