package game.client;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles the input of the game
 */
public class InputHandler implements KeyListener, MouseListener {

	private boolean[] keyArray, mouseButtonArray;
	private boolean mouseInside;
	private boolean clicked;
	private Client client;

	/**
	 * Create a new InputHandler
	 * @param client - The Client object
	 */
	public InputHandler(Client client) {
		keyArray = new boolean[256];
		mouseButtonArray = new boolean[MouseInfo.getNumberOfButtons()];
		mouseInside = false;
		this.client = client;
	}

	/**
	 * Return if the mouse was clicked
	 * @return Boolean whether mouse was clicked
	 */
	public boolean wasMouseClicked() {
		return clicked;
	}

	/**
	 * Set whether the mouse has been clicked
	 * @param bool - Boolean of whether is clicked
	 */
	public void setMouseClicked(boolean bool) {
		this.clicked = bool;
	}

	/**
	 * Get if a current key is down/pressed on the keyboard
	 * @param keyCode - Key to check
	 * @return Whether key is pressed on keyboard
	 */
	public boolean isKeyDown(int keyCode) {
		return keyArray[keyCode];
	}

	/**
	 * Get position of mouse pointer
	 * @return Position object of mouse pointer current location
	 */
	public Point getMousePos() {
		return client.getMousePosition();
	}

	/**
	 * Check if mouse button is down
	 * @param button - Button to check
	 * @return Whether mouse button is currently down/clicked
	 */ 
	public boolean isMouseButtonDown(int button) {
		return mouseButtonArray[button];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keyArray[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyArray[e.getKeyCode()] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked = true;
		try {
			Thread.sleep(50);
			clicked = false;
		} catch (InterruptedException e1) {
			System.out.println("Error in mouse detection: " + e1.getMessage());
		} {

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButtonArray[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButtonArray[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInside = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInside = false;
	}

	/**
	 * Check if mouse is inside of game frame
	 * @return Whether mouse inside game frame
	 */
	public boolean isMouseInside() {
		return mouseInside;
	}
}