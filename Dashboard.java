/*
 * File: BlankClass.java
 * ---------------------
 * This class is a blank one that you can change at will. Remember, if you change
 * the class name, you'll need to change the filename so that it matches.
 * Then you can extend GraphicsProgram, ConsoleProgram, or DialogProgram as you like.
 */

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

import acm.graphics.*;
import acm.io.IOConsole;
import acm.program.*;

public class Dashboard extends GraphicsProgram
{
	public void run()
	{
		addMouseListeners();
		InstrReadout ir = new InstrReadout(BUTTON_WIDTH + 2 * BUTTON_SPACING, 300, BUTTON_SPACING, Color.BLACK, Color.ORANGE, Color.ORANGE.brighter());
		add(ir, 0, 0);
		InstrReadoutControl irc = new InstrReadoutControl(BUTTON_WIDTH + 2 * BUTTON_SPACING, 4 * BUTTON_HEIGHT + 5 * BUTTON_SPACING, BUTTON_SPACING, Color.BLACK, Color.ORANGE);
		irc.addInstrReadout(ir);
		add(irc, 0, 300);
		/*Random r = new Random();
		for(int i = 0; i < 10000; ++i)
		{
			ir.update(r.nextInt(1000000000) - 500000000);
			pause(100);
		}*/
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mouseReleased(e);
	}
	
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 80;
	private static final int BUTTON_SPACING = 5;
}

