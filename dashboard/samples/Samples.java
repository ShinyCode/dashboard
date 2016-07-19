package dashboard.samples;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import dashboard.control.*;
import dashboard.program.DashboardProgram;
import dashboard.readout.*;

public class Samples extends DashboardProgram
{
	private static final double BUTTON_HEIGHT = 40.0;
	private static final double BUTTON_WIDTH = 80.0;
	private static final double SHORT_BUTTON_HEIGHT = 20.0;
	private static final double BUTTON_SPACING = 10.0;
	private static final double COMPONENT_SPACING = 10.0;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color COLOR = Color.RED.darker();
	private static final Color ACCENT_COLOR = Color.ORANGE;
	private static final int NUM_DIVISIONS = 100;
	private static final double WIDTH = 0.0;
	private static final double HEIGHT = 0.0;
	private static final Color BUTTON_COLOR = Color.GREEN.darker();
	private static final double BAR_READOUT_SHORTER = 40.0;
	private static final double BAR_READOUT_LONGER = 120.0;
	private static final double READOUT_SPACING = 10.0;
	private static final double BUFFER_READOUT_WIDTH = 400;
	private static final double BUFFER_READOUT_HEIGHT = 200;
	private static final double COMPASS_READOUT_WIDTH = 120;
	private static final double COMPASS_READOUT_HEIGHT = 120;
	private static final double COLOR_READOUT_WIDTH = 80;
	private static final double COLOR_READOUT_HEIGHT = 40;
	private static final double DIAL_READOUT_WIDTH = 120;
	private static final double DIAL_READOUT_HEIGHT = 70;
	private static final double IMAGE_READOUT_WIDTH = 200;
	private static final double IMAGE_READOUT_HEIGHT = 200;
	private static final double MINIMAP_READOUT_WIDTH = 200;
	private static final double MINIMAP_READOUT_HEIGHT = 200;
	
	public void init()
	{
		// Drawing buttons
		// TouchButton touchButton = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED.darker(), "TOUCH");
		ToggleButton b1 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED.darker(), "TOUCH");
		ToggleButton b2 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED.darker(), "TOUCH");
		b2.setOn(true);
		addWidget("B1", b1, COMPONENT_SPACING, COMPONENT_SPACING);
		addWidget("B2", b2, b1.getX() + b1.getWidth() + COMPONENT_SPACING, b1.getY());
		
		// HoldButton holdButton = new HoldButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.GREEN.darker(), "HOLD");
		ToggleButton b3 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.GREEN.darker(), "HOLD");
		ToggleButton b4 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.GREEN.darker(), "HOLD");
		b4.setOn(true);
		addWidget("B3", b3, COMPONENT_SPACING, b2.getY() + b2.getHeight() + COMPONENT_SPACING);
		addWidget("B4", b4, b3.getX() + b3.getWidth() + COMPONENT_SPACING, b3.getY());
		
		// ToggleButton toggleButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.BLUE.darker(), "TOGGLE");
		ToggleButton b5 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.BLUE.darker(), "TOGGLE");
		ToggleButton b6 = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.BLUE.darker(), "TOGGLE");
		b6.setOn(true);
		addWidget("B5", b5, COMPONENT_SPACING, b4.getY() + b4.getHeight() + COMPONENT_SPACING);
		addWidget("B6", b6, b5.getX() + b5.getWidth() + COMPONENT_SPACING, b5.getY());
		
		MainArrowPad mainArrowPad = new MainArrowPad.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 3 * BUTTON_HEIGHT + 4 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("MAP", mainArrowPad, b5.getX(), b5.getY() + b5.getHeight() + COMPONENT_SPACING);
		
		AuxArrowPad auxArrowPad = new AuxArrowPad.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("AAP", auxArrowPad, mainArrowPad.getX(), mainArrowPad.getY() + mainArrowPad.getHeight() + COMPONENT_SPACING);
		
		BufferReadoutControl bufferReadoutControl = new BufferReadoutControl.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, 4 * SHORT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("BRC", bufferReadoutControl, auxArrowPad.getX(), auxArrowPad.getY() + auxArrowPad.getHeight() + COMPONENT_SPACING);
		
		SingleIncrementer singleInc = new SingleIncrementer.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, 2 * SHORT_BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("SINC", singleInc, bufferReadoutControl.getX() + bufferReadoutControl.getWidth() + COMPONENT_SPACING, bufferReadoutControl.getY());
		
		MultiIncrementer multiInc = new MultiIncrementer.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, 3 * SHORT_BUTTON_HEIGHT + 4 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(BUTTON_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("MINC", multiInc, singleInc.getX() + singleInc.getWidth() + COMPONENT_SPACING, singleInc.getY());

		// Now plotting readouts
		// For BarReadout, also do horizontal version
		BarReadout barReadout = new BarReadout.Builder(BAR_READOUT_SHORTER, BAR_READOUT_LONGER, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withOrientation(BarReadout.VERTICAL)
			.withRange(0.0, 200.0)
			.build();
		addWidget("BR", barReadout, bufferReadoutControl.getX(), bufferReadoutControl.getY() + bufferReadoutControl.getHeight() + COMPONENT_SPACING);
		barReadout.update(132.0);
		
		BarReadout barReadoutHoriz = new BarReadout.Builder(BAR_READOUT_LONGER, BAR_READOUT_SHORTER, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withOrientation(BarReadout.HORIZONTAL)
			.withRange(0.0, 200.0)
			.build();
		addWidget("BRH", barReadoutHoriz, barReadout.getX() + barReadout.getWidth() + COMPONENT_SPACING, barReadout.getY());
		barReadoutHoriz.update(132.0);
		
		BufferReadout bufferReadout = new BufferReadout.Builder(BUFFER_READOUT_WIDTH, BUFFER_READOUT_HEIGHT)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withFont("Consolas-*-*")
			.build();
		addWidget("BFR", bufferReadout, mainArrowPad.getX() + mainArrowPad.getWidth() + COMPONENT_SPACING, COMPONENT_SPACING);
		bufferReadout.update("This is a BufferReadout, great for storing text!");
		
		ColorReadout.Builder colorReadoutBuilder = new ColorReadout.Builder(COLOR_READOUT_WIDTH, COLOR_READOUT_HEIGHT)
			.withBaseColor(BASE_COLOR)
			.withSpacing(READOUT_SPACING);
		Color[] colors = {Color.RED.darker(), Color.GREEN.darker(), Color.BLUE.darker()};
		for(int i = 0; i < colors.length; ++i)
		{
			ColorReadout colorReadout = colorReadoutBuilder.build();
			colorReadout.update(colors[i]);
			addWidget("CR" + i, colorReadout, bufferReadout.getX() + bufferReadout.getWidth() + COMPONENT_SPACING + i * (COLOR_READOUT_WIDTH + COMPONENT_SPACING), bufferReadout.getY());
		}
		
		CompassReadout compassReadout = new CompassReadout.Builder(COMPASS_READOUT_WIDTH, COMPASS_READOUT_HEIGHT)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withLocation(null, null)
			.withGoal(null)
			.build();
		addWidget("CMPR", compassReadout, bufferReadout.getX(), bufferReadout.getY() + bufferReadout.getHeight() + COMPONENT_SPACING);
		// After updating:
		compassReadout.updateGoal(new GPoint(1, 1));
		compassReadout.update(new GPoint(0, 0), new GPoint(1, 0));
		
		DialReadout dialReadout = new DialReadout.Builder(DIAL_READOUT_WIDTH, DIAL_READOUT_HEIGHT, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withStartAngle(0.0)
			.withSweepAngle(180.0)
			.withRange(0.0, 200.0)
			.build();
		addWidget("DR", dialReadout, compassReadout.getX(), compassReadout.getY() + compassReadout.getHeight() + COMPONENT_SPACING);
		dialReadout.update(150.0);
		
		ImageReadout imageReadout = new ImageReadout.Builder(IMAGE_READOUT_WIDTH, IMAGE_READOUT_HEIGHT)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withOffColor(Color.BLACK)
			.build();
		addWidget("IR", imageReadout, compassReadout.getX() + compassReadout.getWidth() + COMPONENT_SPACING, compassReadout.getY());
		imageReadout.update(new GImage("res/testimage.gif"));
		
		MinimapReadout minimapReadout = new MinimapReadout.Builder(MINIMAP_READOUT_WIDTH, MINIMAP_READOUT_HEIGHT)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(READOUT_SPACING)
			.withViewRadius(10.0)
			.build();
		addWidget("MMR", minimapReadout, imageReadout.getX(), imageReadout.getY() + imageReadout.getHeight() + COMPONENT_SPACING);
		minimapReadout.addPin("NORTHEAST", new GPoint(5.0, 5.0), Color.BLUE);
		minimapReadout.addPin("SOUTH", new GPoint(0.0, -4.0), Color.GREEN);
		minimapReadout.addPin("WEST", new GPoint(-7.0, 0.0), Color.PINK);
		minimapReadout.update(new GPoint(0, 0), new GPoint(0, 1));
		
		addMouseListeners();
	}
}
