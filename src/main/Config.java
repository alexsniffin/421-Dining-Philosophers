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
	 * The total amount of philosophers which are hungry
	 */
	public static final int TOTAL_PHILOSOPHERS = NAMES.length;
	
	/**
	 * The time range from 0 to n ms in which to wait between thinking any eating
	 */
	public static final int TIME_TO_WAIT = 1500;
	
	/**
	 * The time to wait for a fork lock to open up in ms
	 */
	public static final int WAIT_FOR_FORK = 1000;
	
	/**
	 * A fixed time in which any philosopher must wait between eating and thinking
	 */
	public static final int BASE_TIME = 3000;
	
	/**
	 * The size of the round table in pixels
	 */
	public static final int TABLE_DIAMETER = 300;

}
