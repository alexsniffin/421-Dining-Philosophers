package main;

import java.awt.Color;
import java.util.Random;

import graphics.PhilosopherGraphic;

/**
 * A Philosopher who thinks and eats using two Forks,
 * the philosopher runs in his own thread concurrently
 * sharing forks with other philosophers beside him.
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 19, 2016
 */
public class Philosopher implements Runnable {

	/**
	 * Unique identifier for our Philosopher
	 */
	private final int id;
	
	/**
	 * The name of our Philosopher
	 */
	private final String name;

	/**
	 * Fork beside the Philosopher which he's using
	 */
	private Fork left, right;
	
	/**
	 * True if the thread is still running
	 */
	private boolean hungry;
	
	/**
	 * The the reference to the circle graphic object
	 */
	private PhilosopherGraphic circle;

	private Random random = new Random();

	/**
	 * Creates a new Philosopher in its own thread
	 * 
	 * @param id Unique ID
	 * @param name The name of our Philosopher
	 * @param hungry True if the thread is still running
	 * @param left Left fork
	 * @param right Right fork
	 */
	public Philosopher(int id, String name, boolean hungry, Fork left, Fork right) {
		this.id = id;
		this.name = name;
		this.hungry = hungry;
		this.left = left;
		this.right = right;
	}

	/**
	 * Run the Philosopher in a separate thread concurrently
	 */
	@Override
	public void run() {
		try {
			boolean coin;
			
			while (hungry) {
				//Flip a coin
				coin = random.nextBoolean();
				
				//Think about things
				think();
				
				//Attempt to pickup a fork
				chooseFork(coin);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Picks a fork to attempt picking up based on the coin flip
	 * 
	 * @param option The coin flip
	 * @throws InterruptedException Error in concurrency
	 */
	public void chooseFork(boolean option) throws InterruptedException {
		if (option) {
			if (left.pickUp()) {
				left.getFg().movePosi();
				if (right.pickUp()) {
					right.getFg().moveNeg();
					eat();
					right.putDown();
					right.getFg().original();
				}
				left.getFg().original();
				left.putDown();
			}
		} else {
			if (right.pickUp()) {
				right.getFg().moveNeg();
				if (left.pickUp()) {
					left.getFg().movePosi();
					eat();
					left.putDown();
					left.getFg().original();
				}
				right.putDown();
				right.getFg().original();
			}
		}
	}

	/**
	 * Eat for a random finite amount of time
	 * 
	 * @throws InterruptedException Error in concurrency
	 */
	public void eat() throws InterruptedException {
		System.out.println(this + " is eating...");
		circle.setColor(Color.RED);
	    Thread.sleep(random.nextInt(Config.TIME_TO_WAIT) + Config.BASE_TIME);   
	}

	/**
	 * Think for a random finite amount of time
	 * 
	 * @throws InterruptedException Error in concurrency
	 */
	public void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		circle.setColor(new Color(95, 95, 255));
	    Thread.sleep(random.nextInt(Config.TIME_TO_WAIT) + Config.BASE_TIME);
	}

	@Override
	public String toString() {
		return name + "[" + id + "]";
	}

	public int getId() {
		return id;
	}
	
	public boolean isHungry() {
		return hungry;
	}

	public void setHungry(boolean hungry) {
		this.hungry = hungry;
	}

	public PhilosopherGraphic getCircle() {
		return circle;
	}

	public void setCircle(PhilosopherGraphic circle) {
		this.circle = circle;
	}
	
}