package main;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import graphics.GPanel;

/**
 *
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 20, 2016
 */
public class GUI extends JFrame {
	
	/**
	 * Reference to the static Philosopher List
	 */
	private static ArrayList<Philosopher> philosophers = Driver.philosophers;
	
	/**
	 * Reference to the static Fork List
	 */
	private static ArrayList<Fork> forks = Driver.forks;
	
	private GPanel graphics;

	public GUI(String title, Dimension size) {
		this.setTitle(title);
		this.setPreferredSize(size);
		this.setResizable(false);
		this.setVisible(true);
		
		graphics = new GPanel(Config.TABLE_DIAMETER);
        this.add(graphics);
		
		this.pack();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	//close threads
		        System.exit(0);
		    }
		});
		
		/*JOptionPane.showMessageDialog(this, 
				"Set the amount of Philosophers, any preferences, and then click start.", "Welcome",
				JOptionPane.INFORMATION_MESSAGE);*/
	}
	
	public GPanel getDrawing() {
		return graphics;
	}
	
}
