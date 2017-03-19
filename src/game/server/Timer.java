package game.server;

/**
 * Class to countdown from set time to 0
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
		this.time = duration;
		this.state = state;
	}

	/**
	 * Get time remaining in seconds
	 * @return Current time remaining in seconds
	 */
	public int getTimeRemaining() {
		return time;
	}

	// Start the thread
	public void run() {
		boolean running = true;
		for(time++; time>=0; time--) {
			if(!running){
				break;
			}
			// System.out.println("Time "+time);

			if (state.HasFinished()) {
				running = false; // Stop the countdown
			}
			try {
				state.updateTime(time);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Thread Error in Timer! " + e.getMessage());
			}
		}
	}
}