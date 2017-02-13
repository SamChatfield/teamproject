package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Sam on 20/01/2017.
 */
public class InputHandler implements KeyListener, MouseListener {

    private boolean[] keyArray, mouseButtonArray;
    private boolean mouseInside;
    private boolean clicked;
    private Game game;

    public InputHandler(Game game) {
        keyArray = new boolean[256];
        mouseButtonArray = new boolean[MouseInfo.getNumberOfButtons()];
        mouseInside = false;
        this.game = game;
    }
    
    public boolean wasMouseClicked() {
    	return clicked;
    }
    
    public void setMouseClicked(boolean bool) {
    	this.clicked = bool;
    }

    public boolean isKeyDown(int keyCode) {
        return keyArray[keyCode];
    }

    public Point getMousePos() {
        return game.getMousePosition();
    }

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
    	System.out.println("TEST");
    	try {
			Thread.sleep(5);
			System.out.println("FALSE");
			this.setMouseClicked(false);
		} catch (InterruptedException e1) {
			System.out.println("ERROR");
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

    public boolean isMouseInside() {
        return mouseInside;
    }

}
