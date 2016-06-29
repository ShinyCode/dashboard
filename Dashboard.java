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
import acm.program.*;

// Todo:
/* Make it possible to get the stack trace. - DONE
 * Implemented Builders in some classes, but try to use generics to allow inheritance of builder
 * 
 */

public class Dashboard extends GraphicsProgram
{
	public void run()
	{
		addMouseListeners();
		// Set up power console
		// pwr = new ButtonGrid(1 * BUTTON_WIDTH + 2 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING, 1, 1, BUTTON_SPACING, BASE_COLOR);
		pwr = new CustomButtonGrid.Builder(1 * BUTTON_WIDTH + 2 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 1)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		pwr.setName("PWR");
		ToggleButton mpwr = new ToggleButton(0, 0, BUTTON_COLOR_2, "MPWR");
		pwr.addButton(mpwr, 0, 0);
		add(pwr, COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(pwr, BUTTON_COLOR_2);
		
		
		// Set up aux console
		aux = new CustomButtonGrid.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 3)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		aux.setName("AUX");
		ToggleButton com0 = new ToggleButton(0, 0, BUTTON_COLOR_1, "COM0");
		ToggleButton com1 = new ToggleButton(0, 0, BUTTON_COLOR_1, "COM1");
		ToggleButton com2 = new ToggleButton(0, 0, BUTTON_COLOR_1, "COM2");
		aux.addButton(com0, 0, 0);
		aux.addButton(com1, 0, 1);
		aux.addButton(com2, 0, 2);
		add(aux, pwr.getX() + pwr.getWidth() + COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(aux, BUTTON_COLOR_1);
		
		// Set up arrow pad
		aap = new AuxArrowPad.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR_1)
			.build();
		aap.setName("CTRL");
		add(aap, aux.getX(), aux.getY() + aux.getHeight() + COMPONENT_SPACING);
		addBorder(aap, BUTTON_COLOR_1);	
		
		// Set up readouts
		double readoutWidth = BUTTON_WIDTH + 2 * BUTTON_SPACING;
		double readoutHeight = 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING + COMPONENT_SPACING;
		spd = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		rot = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		addr = new InstrReadout(readoutWidth, readoutHeight, BUTTON_SPACING, BASE_COLOR, READOUT_COLOR, READOUT_COLOR.brighter());
		add(spd, aux.getX() + aux.getWidth() + COMPONENT_SPACING, pwr.getY());
		add(rot, spd.getX() + spd.getWidth() + COMPONENT_SPACING, spd.getY());
		add(addr, rot.getX() + rot.getWidth() + COMPONENT_SPACING, rot.getY());
		addBorder(spd, BUTTON_COLOR_2);
		addBorder(rot, BUTTON_COLOR_2);
		addBorder(addr, BUTTON_COLOR_2);
		
		// Set up readout controls
		spdCtrl = new InstrReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR_1)
			.build();
		add(spdCtrl, spd.getX(), spd.getY() + spd.getHeight() + COMPONENT_SPACING);
		spdCtrl.setName("SPD_CTRL");
		rotCtrl = new InstrReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR_1)
			.build();
		add(rotCtrl, rot.getX(), rot.getY() + rot.getHeight() + COMPONENT_SPACING);
		rotCtrl.setName("ROT_CTRL");
		addrCtrl = new InstrReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR_1)
			.build();
		add(addrCtrl, addr.getX(), addr.getY() + addr.getHeight() + COMPONENT_SPACING);
		addrCtrl.setName("ADDR_CTRL");
		addBorder(spdCtrl, BUTTON_COLOR_1);
		addBorder(rotCtrl, BUTTON_COLOR_1);
		addBorder(addrCtrl, BUTTON_COLOR_1);
		
		// Connect readouts to readout controls
		spdCtrl.addInstrReadout(spd);
		rotCtrl.addInstrReadout(rot);
		addrCtrl.addInstrReadout(addr);
		
		// Set up sensors
		sensAdd = new RandomAddressSensor(UPDATE_SPEED);
		sensAdd.addReadout(addr);
		sensAdd.setActive(true);
		
		// Set up power control
		sinc = new SingleIncrementer.Builder(BUTTON_WIDTH / 2.0 + BUTTON_SPACING - BORDER_WIDTH, spdCtrl.getY() + spdCtrl.getHeight() - aap.getY())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR_2)
			.build();
		sinc.setName("SINC");
		lr = new BarReadout.Builder(sinc.getWidth(), sinc.getHeight(), 0)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(BUTTON_COLOR_1)
			.withAccentColor(BUTTON_COLOR_2)
			.build();
		lr.update(100);
		sinc.setIncrementable(lr);
		add(sinc, pwr.getX(), aap.getY());
		add(lr, sinc.getX() + sinc.getWidth() + 2 * BORDER_WIDTH, aap.getY());
		addBorder(sinc, BUTTON_COLOR_2);
		addBorder(lr, BUTTON_COLOR_1);
		
		// Testing
		/*
		ColorReadout cr = new ColorReadout.Builder(aap.getWidth(), sinc.getHeight())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(Color.GREEN)
			.build();
		add(cr, sinc.getX(), sinc.getY() + sinc.getHeight() + 20);
		pause(1000);
		cr.update(Color.RED);
		*/
		
		// Add a background
		GRect background = new GRect(addr.getX() + addr.getWidth() + 2 * COMPONENT_SPACING - pwr.getX(),
				addrCtrl.getY() + addrCtrl.getHeight() + 2 * COMPONENT_SPACING - addr.getY());
		background.setFilled(true);
		background.setFillColor(BASE_COLOR);
		add(background, 0, 0);
		background.sendToBack();
		
		prc = new Processor(0);
		prc.setActive(true);
	}
	
	public void mousePressed(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		if(cmd != null) prc.issue(cmd);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		if(cmd != null) prc.issue(cmd);
	}
	
	private void addBorder(GObject o, Color c)
	{
		GRect background = new GRect(o.getWidth() + 2 * BORDER_WIDTH, o.getHeight() + 2 * BORDER_WIDTH);
		background.setFilled(true);
		background.setFillColor(c);
		add(background, o.getX() - BORDER_WIDTH, o.getY() - BORDER_WIDTH);
		o.sendToFront();
	}
	
	private CustomButtonGrid pwr;
	private CustomButtonGrid aux;
	private SingleIncrementer sinc;
	private BarReadout lr;
	private AuxArrowPad aap;
	private InstrReadout spd;
	private InstrReadout rot;
	private InstrReadout addr;
	private InstrReadoutControl spdCtrl;
	private InstrReadoutControl rotCtrl;
	private InstrReadoutControl addrCtrl;
	private Processor prc;
	
	private RandomAddressSensor sensAdd;
	
	private static final double BUTTON_HEIGHT = 40;
	private static final double BUTTON_WIDTH = 80;
	private static final double BUTTON_SPACING = 10;
	private static final double COMPONENT_SPACING = 10;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color BUTTON_COLOR_1 = Color.RED.darker();
	private static final Color BUTTON_COLOR_2 = Color.ORANGE;
	private static final Color READOUT_COLOR = Color.ORANGE;
	private static final double READOUT_BUTTON_HEIGHT = BUTTON_HEIGHT / 2.0;
	private static final int UPDATE_SPEED = 100;
	private static final double BORDER_WIDTH = 2;
}

