package game;

/**
 * @author georgesabourin
 * Class to countdown from set time to 0
 * TODO: Improve or replace this class to allow pausing and modifying start times
 */
public class Timer implements Runnable {
	
	public int time;
	public void run() {
		
		for(time = 5; time>0; time--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Thread Error in Timer! " + e.getMessage());
			}
		}
		
	}
}
