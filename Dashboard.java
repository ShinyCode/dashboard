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
		bg = new ButtonGroup(BUTTON_WIDTH + 2 * BUTTON_SPACING, 3 * BUTTON_HEIGHT + 4 * BUTTON_SPACING, Color.BLACK);
		bg.add(new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.GREEN, "INC"), BUTTON_SPACING, BUTTON_SPACING);
		bg.add(new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED, "DEC"), BUTTON_SPACING, 2 * BUTTON_SPACING + BUTTON_HEIGHT);
		bg.add(new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.ORANGE, "SEL"), BUTTON_SPACING, 3 * BUTTON_SPACING + 2 * BUTTON_HEIGHT);
		add(bg, 0, 4 * BUTTON_HEIGHT + 4 * BUTTON_SPACING);
		bgr = new ButtonGrid(2 * BUTTON_WIDTH + 3 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING, 2, 2, BUTTON_SPACING, Color.BLACK);
		bgr.addButton(rcl, 0, 0);
		bgr.addButton(dst, 1, 0);
		bgr.addButton(src, 0, 1);
		bgr.addButton(pwr, 1, 1);
		add(bgr, 0, 0);
		lr0 = new LevelReadout(30, 100, BUTTON_SPACING, 10, Color.BLACK, Color.GREEN, Color.RED);
		add(lr0, 300, 0);
		lr1 = new LevelReadout(30, 100, BUTTON_SPACING, 10, Color.BLACK, Color.GREEN, Color.RED);
		add(lr1, 330, 0);
		lr2 = new LevelReadout(30, 100, BUTTON_SPACING, 10, Color.BLACK, Color.GREEN, Color.RED);
		add(lr2, 360, 0);
		inc = new MultiIncrementer(BUTTON_WIDTH + 2 * BUTTON_SPACING, 3 * BUTTON_HEIGHT + 4 * BUTTON_SPACING, BUTTON_SPACING, Color.BLACK);
		add(inc, 200, 200);
		inc.addIncrementable(lr0);
		inc.addIncrementable(lr1);
		inc.addIncrementable(lr2);
		aap = new AuxArrowPad(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING, BUTTON_SPACING, Color.BLACK, Color.MAGENTA);
		add(aap, 300, 250);
		map = new MainArrowPad(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 3 * BUTTON_HEIGHT + 4 * BUTTON_SPACING, BUTTON_SPACING, Color.BLACK, Color.MAGENTA);
		add(map, 400, 100);
		map.addAuxArrowPad(aap);
		ir = new InstructionReadout(100, 400, BUTTON_SPACING, Color.BLACK, Color.ORANGE, Color.ORANGE.brighter());
		add(ir, 400, 200);
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
	
	private TouchButton rcl;
	private TouchButton dst;
	private TouchButton src;
	private ToggleButton pwr;
	private ButtonGroup bg;
	private MultiIncrementer inc;
	private ButtonGrid bgr;
	private LevelReadout lr0;
	private LevelReadout lr1;
	private LevelReadout lr2;
	private AuxArrowPad aap;
	private MainArrowPad map;
	private InstructionReadout ir;
	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_WIDTH = 80;
	private static final int BUTTON_SPACING = 5;
}

