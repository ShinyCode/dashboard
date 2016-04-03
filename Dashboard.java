/*
 * File: BlankClass.java
 * ---------------------
 * This class is a blank one that you can change at will. Remember, if you change
 * the class name, you'll need to change the filename so that it matches.
 * Then you can extend GraphicsProgram, ConsoleProgram, or DialogProgram as you like.
 */

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.io.IOConsole;
import acm.program.*;

public class Dashboard extends GraphicsProgram
{
	public void run()
	{
		addMouseListeners();
		rcl = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED, "RCL");
		dst = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED, "DST");
		src = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED, "SRC");
		add(rcl, BUTTON_SPACING, BUTTON_SPACING);
		add(dst, BUTTON_SPACING, BUTTON_HEIGHT + 2 * BUTTON_SPACING);
		add(src, BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING);
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof TouchButton)
		{
			((TouchButton) o).pressButton();
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof TouchButton)
		{
			((TouchButton) o).releaseButton();
		}
	}
	
	private TouchButton rcl;
	private TouchButton dst;
	private TouchButton src;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_SPACING = 10;
}

