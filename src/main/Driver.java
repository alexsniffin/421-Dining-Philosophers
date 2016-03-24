package main;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple program to demonstrate The Dining Philosophers using
 * the coin flip method. No need for an extra object (waiter) or
 * timestamp are needed.
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 19, 2016
 */
public class Driver {
	
	/**
	 * A list of our Philosophers acting as Threads
	 */
	public static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
	
	/**
	 * A list of Forks used as acting as Locks
	 */
	public static ArrayList<Fork> forks = new ArrayList<Fork>();

	/**
	 * Initialize the Dining Philosophers
	 * 
	 * @param args Nil
	 * @throws InterruptedException Error with concurrency
	 */
	public static void main(String[] args) throws InterruptedException {
		//Do nothing if we haven't loaded any Philosophers in from ./names.dat
		if (Config.TOTAL_PHILOSOPHERS > 0) {	
			//Create a separate thread for drawing
			Executors.newSingleThreadExecutor().execute(
				new Runnable() {
				    @Override 
				    public void run() {
				        GUI gui = new GUI("Dining Philosophers", new Dimension(800, 650));
				        
				        while(true) {
				        	gui.getDrawing().repaint();
				        	
				        	if (GUI.getEs() != null)
				        		if (!GUI.getEs().isShutdown()) {
				        			gui.updateDetails();
				        			gui.repaint();
				        		}
				        }
				    }
				}
			);
		} else
			System.err.println("No Philosophers were loaded from ./names.dat");
	}
	
	/**
	 * Load in the names.dat file and returns an array of N names
	 * 
	 * @return String array of names
	 */
	public static String[] loadNames() {
		File file = new File("./names.dat");
		
		if (file.exists()) {
			ArrayList<String> names = new ArrayList<String>();
			
			try (Scanner scanner = new Scanner(file)) {
	
				while (scanner.hasNextLine()) {
					names.add(scanner.nextLine());
				}
				scanner.close();
				
				return names.toArray(new String[names.size()]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.err.println("Error loading ./names.dat file!");
		return new String[0];
	}
}
