package main;

import java.awt.Color;
import java.util.Random;

import graphics.PhilosopherGraphic;

/**
 * A Philosopher who thinks and eats using two Forks,
 * the philosopher runs in his own thread concurrently
 * sharing forks with other neighboring philosophers.
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
	 * Forks beside the Philosopher which he's using
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
	
	/**
	 * The Philsophers beside this Philsopher in which you must share forks with
	 * and flip coins
	 */
	private Philosopher leftNeighbor, rightNeighbor;
	
	/**
	 * Keep track of how much this Philosopher has eatten
	 */
	private int eatCount = 0;
	
	/**
	 * Possible statuses are: "Waiting", "Eating", "Thinking", "Away", and "Flipping"
	 * 
	 * Waiting: Waiting for a Fork to open up from our neighbor
	 * Eating: Eating with both Forks
	 * Thinking: Thinking for a little, using no forks
	 * Away: Currently not at the table
	 * Flipping: Flipping a coin against one of the neighbors
	 */
	private String status = "";

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
				//Think about things
				think();
				
				//Attempt to pickup a fork
				chooseFork(Driver.philosophers.size());
			}
			eatCount = 0;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts by trying to pick up the left fork, because
	 * the method is generic, option could be added to try
	 * the right fork.
	 * 
	 * @param total Total Philosophers at the table
	 * @throws InterruptedException Error in concurrency
	 */
	public void chooseFork(int total) throws InterruptedException {
		pickUpForks(left, right, total);
		
		status = "Thinking";
	}
	
	/**
	 * A generic method for picking up forks, can start with the left or right
	 * 
	 * @param f1 First fork to try picking up
	 * @param f2 Second fork to try picking up
	 * @param total Total Philosophers at the table
	 * @throws InterruptedException Error in concurrency
	 */
	public void pickUpForks(Fork f1, Fork f2, int total) throws InterruptedException {
		if (f1.pickUp()) {
			f1.getFg().moveFork(total, true);
			f1.setOwnerId(id);
			status = "Waiting";
			circle.setColor(Color.ORANGE);
			
			if (f2.pickUp()) {
				f2.getFg().moveFork(total, false);
				f2.setOwnerId(id);
				eat();
				f2.putDown();
				f2.getFg().original();
			//Check if someone else is waiting on us and flip a coin
			} else {
				//Is our left neighbor waiting on us?
				if (leftNeighbor.getStatus().equals("Waiting"))
					flipCoin(f1, f2, total, false);
			}
			
			//Should be done eating or waiting, put the fork back down
			if (f1.getOwnerId() == id) {
				f1.getFg().original();
				f1.putDown();
			}
		}
		//Do nothing and go back to thinking for a bit...
	}

	/**
	 * Flips a coin between this Philosopher and his left neighbor
	 * 
	 * @param f1 First fork to try picking up
	 * @param f2 Second fork to try picking up
	 * @param total Total Philosophers at the table
	 * @param option True is for the right fork, false for left fork
	 * @throws InterruptedException Error in concurrency
	 */
	public void flipCoin(Fork f1, Fork f2, int total, boolean option) throws InterruptedException {
		circle.setColor(Color.MAGENTA);
		leftNeighbor.getCircle().setColor(Color.MAGENTA);
		
		//Wait for a small amount to visually show the color change
		Thread.sleep(250);
		
		boolean coin = random.nextBoolean();
		System.out.println("Flipping: " + name + "[" + id + "] and " + leftNeighbor.getName() + "[" + leftNeighbor.getId() + "]");
		
		if (coin) {
			System.out.println(leftNeighbor.getName() + " wins the coin flip!");
			
			//He wins so we must put our fork down for him
			f1.getFg().original();
			f1.putDown();
			f1.setOwnerId(id - 1);
			//Now he should pick up our fork because he should still be waiting on it
			
		} else {
			circle.setColor(Color.ORANGE);
			//We win, lets try to get the right fork again
			if (f2.pickUp()) {
				f2.getFg().moveFork(total, option);
				f2.setOwnerId(id);
				eat();
				f2.putDown();
				f2.getFg().original();
			}
			//If not, go back to thinking
		}
	}

	/**
	 * Eat for a random finite amount of time
	 * 
	 * @throws InterruptedException Error in concurrency
	 */
	public void eat() throws InterruptedException {
		//System.out.println(this + " is eating...");
		status = "Eating";
		circle.setColor(Color.RED);
	    Thread.sleep(random.nextInt(Config.TIME_TO_EAT) + Config.BASE_TIME);
	    eatCount++;
	}

	/**
	 * Think for a random finite amount of time
	 * 
	 * @throws InterruptedException Error in concurrency
	 */
	public void think() throws InterruptedException {
		//System.out.println(this + " is thinking...");
		status = "Thinking";
		circle.setColor(new Color(95, 95, 255));
	    Thread.sleep(random.nextInt(Config.TIME_TO_THINK) + Config.BASE_TIME);
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
	
	public String getName() {
		return name;
	}

	public int getEatCount() {
		return eatCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Fork getLeft() {
		return left;
	}

	public void setLeft(Fork left) {
		this.left = left;
	}

	public Fork getRight() {
		return right;
	}

	public void setRight(Fork right) {
		this.right = right;
	}

	public Philosopher getLeftNeighbor() {
		return leftNeighbor;
	}

	public void setLeftNeighbor(Philosopher leftNeighbor) {
		this.leftNeighbor = leftNeighbor;
	}

	public Philosopher getRightNeighbor() {
		return rightNeighbor;
	}

	public void setRightNeighbor(Philosopher rightNeighbor) {
		this.rightNeighbor = rightNeighbor;
	}
}