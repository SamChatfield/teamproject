package game;

/**
 * @author georgesabourin
 * Class to countdown from set time to 0
 * TODO: Improve or replace this class to allow pausing and modifying start times
 */
public class Timer implements Runnable {
	
	private int time;
	
	/**
	 * Constructor to set the timer duration
	 * @param duration Number of seconds for the game to last
	 */
	public Timer(int duration) {
		int time = duration;
	}
	
	/**
	 * Get time remaining in seconds
	 * @return Current time remaining in seconds
	 */
	public int getTimeRemaining() {
		return time;
	}
	
	public void run() {
		
		for(time = 180; time>0; time--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Thread Error in Timer! " + e.getMessage());
			}
		}
		
	}
}
