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
		pwr = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.BLUE, "PWR");
		add(rcl, BUTTON_SPACING, BUTTON_SPACING);
		add(dst, BUTTON_SPACING, BUTTON_HEIGHT + 2 * BUTTON_SPACING);
		add(src, BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING);
		add(pwr, BUTTON_SPACING, 3 * BUTTON_HEIGHT + 4 * BUTTON_SPACING);
		add(new ButtonGroup(0, 0), 0, 4 * BUTTON_HEIGHT + 5 * BUTTON_SPACING);
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof TouchButton)
		{
			((TouchButton) o).pressButton();
		}
		else if(o instanceof ToggleButton)
		{
			((ToggleButton) o).toggleButton();
		}
		else if(o instanceof ButtonGroup)
		{
			((ButtonGroup) o).mousePressed(e);
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof TouchButton)
		{
			((TouchButton) o).releaseButton();
		}
		else if(o instanceof ButtonGroup)
		{
			((ButtonGroup) o).mouseReleased(e);
		}
	}
	
	private TouchButton rcl;
	private TouchButton dst;
	private TouchButton src;
	private ToggleButton pwr;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_SPACING = 10;
}

