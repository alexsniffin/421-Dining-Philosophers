package main;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import graphics.ForkGraphic;

/**
 * A unique Fork with a lock used by the Philosopher, this
 * Fork may only be used once at any given time.
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 19, 2016
 */
public class Fork {

	/**
	 * Unique identifier for the Fork
	 */
	private final int id;

	/**
	 * Sets up a lock for this fork
	 */
	private Lock fork = new ReentrantLock();
	
	/**
	 * The reference to the fork graphic object
	 */
	private ForkGraphic fg;

	/**
	 * Creates a new Fork
	 * 
	 * @param id Unique ID
	 */
	public Fork(int id) {
		this.id = id;
	}

	/**
	 * Attempt to pickup the fork if isn't locked
	 * 
	 * @return True if successful
	 * @throws InterruptedException Error in concurrency
	 */
	public boolean pickUp() throws InterruptedException {
		if (fork.tryLock(Config.WAIT_FOR_FORK, TimeUnit.MILLISECONDS))
			return true;
		
		return false;
	}

	/**
	 * Put the fork down and unlock it
	 */
	public void putDown() {
		fork.unlock();
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Fork[" + id + "]";
	}

	public ForkGraphic getFg() {
		return fg;
	}

	public void setFg(ForkGraphic fg) {
		this.fg = fg;
	}
}