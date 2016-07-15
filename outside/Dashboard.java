package outside;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.io.IOConsole;
import acm.program.*;
import dashboard.*;

// Todo:
/* Make it possible to get the stack trace. - DONE
 * Implemented Builders in some classes, but try to use generics to allow inheritance of builder - DONE
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
		ToggleButton mpwr = new ToggleButton(0, 0, ACCENT_COLOR, "MPWR");
		pwr.addButton(mpwr, 0, 0);
		add(pwr, COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(pwr, ACCENT_COLOR);
		
		
		// Set up aux console
		aux = new CustomButtonGrid.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 3)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		aux.setName("AUX");
		ToggleButton com0 = new ToggleButton(0, 0, COLOR, "COM0");
		ToggleButton com1 = new ToggleButton(0, 0, COLOR, "COM1");
		ToggleButton com2 = new ToggleButton(0, 0, COLOR, "COM2");
		aux.addButton(com0, 0, 0);
		aux.addButton(com1, 0, 1);
		aux.addButton(com2, 0, 2);
		add(aux, pwr.getX() + pwr.getWidth() + COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(aux, COLOR);
		
		// Set up arrow pad
		aap = new AuxArrowPad.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		aap.setName("CTRL");
		add(aap, aux.getX(), aux.getY() + aux.getHeight() + COMPONENT_SPACING);
		addBorder(aap, COLOR);	
		
		// Set up readouts
		double readoutWidth = BUTTON_WIDTH + 2 * BUTTON_SPACING;
		double readoutHeight = 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING + COMPONENT_SPACING;
		BufferReadout.Builder bufferBuilder = new BufferReadout.Builder(readoutWidth, readoutHeight)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR);
		spd = bufferBuilder.build();
		rot = bufferBuilder.build();
		addr = bufferBuilder.build();
		add(spd, aux.getX() + aux.getWidth() + COMPONENT_SPACING, pwr.getY());
		add(rot, spd.getX() + spd.getWidth() + COMPONENT_SPACING, spd.getY());
		add(addr, rot.getX() + rot.getWidth() + COMPONENT_SPACING, rot.getY());
		addBorder(spd, ACCENT_COLOR);
		addBorder(rot, ACCENT_COLOR);
		addBorder(addr, ACCENT_COLOR);
		
		// Set up readout controls
		spdCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		add(spdCtrl, spd.getX(), spd.getY() + spd.getHeight() + COMPONENT_SPACING);
		spdCtrl.setName("SPD_CTRL");
		rotCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		add(rotCtrl, rot.getX(), rot.getY() + rot.getHeight() + COMPONENT_SPACING);
		rotCtrl.setName("ROT_CTRL");
		addrCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		add(addrCtrl, addr.getX(), addr.getY() + addr.getHeight() + COMPONENT_SPACING);
		addrCtrl.setName("ADDR_CTRL");
		addBorder(spdCtrl, COLOR);
		addBorder(rotCtrl, COLOR);
		addBorder(addrCtrl, COLOR);
		
		// Connect readouts to readout controls
		spdCtrl.addBufferReadout(spd);
		rotCtrl.addBufferReadout(rot);
		addrCtrl.addBufferReadout(addr);
		
		// Set up sensors
		sensAdd = new AddressGenerator(UPDATE_SPEED);
		sensAdd.addStringUpdatable("addr", addr);
		sensAdd.addStringUpdatable("rot", rot);
		sensAdd.setActive(true);
		
		// Set up power control
		sinc = new SingleIncrementer.Builder(BUTTON_WIDTH / 2.0 + BUTTON_SPACING - BORDER_WIDTH, spdCtrl.getY() + spdCtrl.getHeight() - aap.getY())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(ACCENT_COLOR)
			.build();
		sinc.setName("SINC");
		lr = new BarReadout.Builder(sinc.getWidth(), sinc.getHeight(), 0)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withRange(0.0, 100.0)
			.withOrientation(BarReadout.VERTICAL)
			.build();
		lr.update(25.0);
		sinc.setIncrementable(lr);
		add(sinc, pwr.getX(), aap.getY());
		add(lr, sinc.getX() + sinc.getWidth() + 2 * BORDER_WIDTH, aap.getY());
		addBorder(sinc, ACCENT_COLOR);
		addBorder(lr, COLOR);
		
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
		
		/*
		DialReadout dr = new DialReadout.Builder(2 * mpwr.getWidth(), mpwr.getWidth(), 300)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(BUTTON_COLOR_1)
			.withAccentColor(BUTTON_COLOR_2)
			.withRange(0.0, 100.0)
			.withStartAngle(0)
			.withSweepAngle(180.0)
			.build();
		*/
		/*
		GRectangle bounds = dr.getBounds();
		GRect r = new GRect(bounds.getWidth(), bounds.getHeight());
		r.setColor(Color.WHITE);
		add(r, aap.getX(), aap.getY() + aap.getHeight());
		*/
		/*
		add(dr, aap.getX(), aap.getY() + aap.getHeight());
		dr.setLevel(2);
		*/
		// sinc.setIncrementable(dr);
		
		/*
		CompassReadout cr = new CompassReadout.Builder(mpwr.getWidth(), mpwr.getWidth())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(BUTTON_COLOR_1)
			.withAccentColor(BUTTON_COLOR_2)
			.build();
		add(cr, dr.getX(), dr.getY() + dr.getHeight() + BUTTON_SPACING);
		cr.update(new GPoint(1, 1), new GPoint(0, 1));
		cr.updateGoal(new GPoint(-1, -1));
		pause(1000);
		cr.updateGoal(new GPoint(-1, 0));
		pause(1000);
		cr.updateGoal(new GPoint(-1, 1));
		pause(1000);
		cr.update(new GPoint(1, 1), new GPoint(1, 0));
		pause(1000);
		*/
		
		/*
		MinimapReadout mr = new MinimapReadout.Builder(2 * mpwr.getWidth(), 2 * mpwr.getWidth())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(BUTTON_COLOR_1)
			.withAccentColor(BUTTON_COLOR_2)
			.build();
		add(mr, dr.getX(), dr.getY() + dr.getHeight() + BUTTON_SPACING);
		mr.setViewRadius(2.5);
		mr.addPin("top", new GPoint(1, 1), Color.RED);
		mr.addPin("middle", new GPoint(1, 0), Color.YELLOW);
		mr.addPin("bottom", new GPoint(1, -1), Color.GREEN);
		for(int i = -1000; i < 1000; ++i)
		{
			mr.update(new GPoint(i * 0.003, i * 0.003), new GPoint(1, 1));
			pause(10);
		}
		*/
		
		/*
		HoldButton test1 = new HoldButton(BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_COLOR_1, "INC");
		test1.setOnAction(new Runnable()
		{
			public void run()
			{
				dr.increment();
			}
		});
		TouchButton test2 = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_COLOR_1, "DEC");
		test2.setOnAction(new Runnable()
		{
			public void run()
			{
				dr.decrement();
			}
		});
		add(test1, BUTTON_SPACING, dr.getY() + dr.getHeight() + BUTTON_SPACING);
		add(test2, BUTTON_SPACING, test1.getY() + test1.getHeight() + BUTTON_SPACING);
		*/
		/*
		ImageReadout ir = new ImageReadout.Builder(2 * mpwr.getWidth(), 2 * mpwr.getWidth())
		.withSpacing(BUTTON_SPACING)
		.withBaseColor(BASE_COLOR)
		.withColor(BUTTON_COLOR_1)
		.withAccentColor(BUTTON_COLOR_2)
		.withOffColor(Color.BLUE)
		.build();
		add(ir, dr.getX(), dr.getY() + dr.getHeight() + BUTTON_SPACING);
		pause(1000);
		ir.setOn(false);
		pause(1000);
		ir.setOn(true);
		pause(1000);
		ir.update(new GImage("res/testimage.gif"));
		pause(1000);
		ir.clear();
		pause(1000);
		ir.update(new GImage("res/testimage.gif"));
		pause(1000);
		ir.setOn(false);
		pause(1000);
		ir.update(new GImage("res/testimage.gif"));
		pause(1000);
		ir.setOn(true);
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
		if(cmd != null && prc != null) prc.issue(cmd);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		if(cmd != null && prc != null) prc.issue(cmd);
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
	private BufferReadout spd;
	private BufferReadout rot;
	private BufferReadout addr;
	private BufferReadoutControl spdCtrl;
	private BufferReadoutControl rotCtrl;
	private BufferReadoutControl addrCtrl;
	private Processor prc;
	
	private AddressGenerator sensAdd;
	
	private static final double BUTTON_HEIGHT = 40;
	private static final double BUTTON_WIDTH = 80;
	private static final double BUTTON_SPACING = 10;
	private static final double COMPONENT_SPACING = 10;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color COLOR = Color.RED.darker();
	private static final Color ACCENT_COLOR = Color.ORANGE;
	private static final double READOUT_BUTTON_HEIGHT = BUTTON_HEIGHT / 2.0;
	private static final int UPDATE_SPEED = 500;
	private static final double BORDER_WIDTH = 2;
}

