package main;

/**
 * Used to store configuration information
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 19, 2016
 */
public final class Config {
	
	/**
	 * The names of our philosophers stored in ./names.dat
	 */
	public static final String[] NAMES = Driver.loadNames();
	
	/**
	 * The total amount of philosophers which are hungry.
	 * The max is capped at 16, any extra will be ignored.
	 */
	public static final int TOTAL_PHILOSOPHERS = NAMES.length;
	
	/**
	 * The time to wait for a fork lock to open up, if the neighbor is also waiting
	 * this is where the two would flip a coin to decide.
	 */
	public static final int WAIT_FOR_FORK = 2500;
	
	/**
	 * A fixed time in which any philosopher must wait between eating and thinking
	 */
	public static final int BASE_TIME = 500;
	
	/**
	 * The size of the round table in pixels
	 */
	public static final int TABLE_DIAMETER = 300;
	
	/**
	 * The max amount of time to wait while eating
	 * 
	 * Total time will be BASE_TIME + Rand(TIME_TO_EAT)
	 */
	public static final int TIME_TO_EAT = 5000;
	
	/**
	 * The max amount of time wait while thinking
	 * 
	 * Total time will be BASE_TIME + Rand(TIME_TO_THINK)
	 */
	public static final int TIME_TO_THINK = 1500;

	/**
	 * About notification text
	 */
	public static final String ABOUT = "A simulation of The Dining Philosophers problem, using the coin flip approach."
			+ "\nThe colors of each philosopher represent the different states that they are in,"
			+ "\nwhere red means they are eatting, blue thinking, yellow waiting, and purple flipping a coin."
			+ "\n\n"
			+ "Written by: Alexander Sniffin";
}
