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
		// Set up power console
		pwr = new ButtonGrid(1 * BUTTON_WIDTH + 2 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING, 1, 1, BUTTON_SPACING, BASE_COLOR);
		ToggleButton mpwr = new ToggleButton(0, 0, BUTTON_COLOR_2, "MPWR");
		pwr.addButton(mpwr, 0, 0);
		add(pwr, COMPONENT_SPACING, COMPONENT_SPACING);
		
		// Set up aux console
		aux = new ButtonGrid(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING, 1, 3, BUTTON_SPACING, BASE_COLOR);
		TouchButton aux0 = new TouchButton(0, 0, BUTTON_COLOR_1, "AUX0");
		TouchButton aux1 = new TouchButton(0, 0, BUTTON_COLOR_1, "AUX1");
		TouchButton aux2 = new TouchButton(0, 0, BUTTON_COLOR_1, "AUX2");
		aux.addButton(aux0, 0, 0);
		aux.addButton(aux1, 0, 1);
		aux.addButton(aux2, 0, 2);
		add(aux, pwr.getX() + pwr.getWidth() + COMPONENT_SPACING, COMPONENT_SPACING);
		
		// Set up arrow pad
		aap = new AuxArrowPad(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING, BUTTON_SPACING, BASE_COLOR, BUTTON_COLOR_1);
		add(aap, aux.getX(), aux.getY() + aux.getHeight() + COMPONENT_SPACING);
		
		// Set up power control
		sinc = new SingleIncrementer(BUTTON_WIDTH / 2.0 + BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING, BUTTON_SPACING, BASE_COLOR, BUTTON_COLOR_2);
		lr = new LevelReadout(sinc.getWidth(), sinc.getHeight(), BUTTON_SPACING, 10, BASE_COLOR, BUTTON_COLOR_1, BUTTON_COLOR_2);
		sinc.setIncrementable(lr);
		add(sinc, pwr.getX(), aap.getY());
		add(lr, sinc.getX() + sinc.getWidth(), aap.getY());
		
		// Set up readouts
		double readoutWidth = BUTTON_WIDTH + 2 * BUTTON_SPACING;
		double readoutHeight = 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING + COMPONENT_SPACING;
		spd = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		rot = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		addr = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		add(spd, aux.getX() + aux.getWidth() + COMPONENT_SPACING, pwr.getY());
		add(rot, spd.getX() + spd.getWidth() + COMPONENT_SPACING, spd.getY());
		add(addr, rot.getX() + rot.getWidth() + COMPONENT_SPACING, rot.getY());
		
		// Set up readout controls
		spdCtrl = new InstrReadoutControl(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING, BUTTON_SPACING, BASE_COLOR, BUTTON_COLOR_1);
		add(spdCtrl, spd.getX(), spd.getY() + spd.getHeight() + COMPONENT_SPACING);
		rotCtrl = new InstrReadoutControl(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING, BUTTON_SPACING, BASE_COLOR, BUTTON_COLOR_1);
		add(rotCtrl, rot.getX(), rot.getY() + rot.getHeight() + COMPONENT_SPACING);
		addrCtrl = new InstrReadoutControl(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING, BUTTON_SPACING, BASE_COLOR, BUTTON_COLOR_1);
		add(addrCtrl, addr.getX(), addr.getY() + addr.getHeight() + COMPONENT_SPACING);
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
	
	private ButtonGrid pwr;
	private ButtonGrid aux;
	private SingleIncrementer sinc;
	private LevelReadout lr;
	private AuxArrowPad aap;
	private InstrReadout spd;
	private InstrReadout rot;
	private InstrReadout addr;
	private InstrReadoutControl spdCtrl;
	private InstrReadoutControl rotCtrl;
	private InstrReadoutControl addrCtrl;
	
	private RandomAddressSensor sensAdd;
	
	private static final double BUTTON_HEIGHT = 40;
	private static final double BUTTON_WIDTH = 80;
	private static final double BUTTON_SPACING = 5;
	private static final double COMPONENT_SPACING = 10;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color BUTTON_COLOR_1 = Color.BLUE.brighter();
	private static final Color BUTTON_COLOR_2 = Color.ORANGE;
	private static final Color READOUT_COLOR = Color.ORANGE;
	private static final double READOUT_BUTTON_HEIGHT = BUTTON_HEIGHT / 2.0;
}

