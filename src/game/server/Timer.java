package game.server;

/**
 * @author georgesabourin
 * Class to countdown from set time to 0
 * TODO: Improve or replace this class to allow pausing and modifying start times
 */
public class Timer implements Runnable {
	
	private int time;
	private ServerGameState state;
	
	/**
	 * Constructor to set the timer duration
	 * @param duration Number of seconds for the game to last
	 */
	public Timer(int duration,ServerGameState state)
	{
		int time = duration;
		this.state = state;
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
			System.out.println("Time "+time);
			try {
				state.updateTime(time);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Thread Error in Timer! " + e.getMessage());
			}
		}
		
	}
}
