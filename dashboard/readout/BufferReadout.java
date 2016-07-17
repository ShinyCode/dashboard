package dashboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

/**
 * Implements a Readout that represents a scrollable graphical buffer of text. The {@link BufferReadoutControl BufferReadoutControl}
 * is specially designed to control the BufferReadout, although custom controllers can also be created.
 * 
 * @author Mark Sabini
 *
 */
public final class BufferReadout extends Readout implements Incrementable, StringUpdatable
{
	/**
	 * The buffer of all the lines of text associated with the BufferReadout
	 */
	private List<String> messages;
	
	/**
	 * The list of all all the GLabels that are currently being displayed on the BufferReadout's screen
	 */
	private List<GLabel> display;
	
	/**
	 * The colored base of the BufferReadout
	 */
	private GRect base;
	
	/**
	 * The screen of the BufferReadout
	 */
	private GRect back;
	
	/**
	 * A cursor selecting a single line of the BufferReadout
	 */
	private GRect bar;
	
	/**
	 * The maximum height of a character block, as calculated by the BufferReadout
	 */
	private double charHeight;
	
	/**
	 * The maximum width of a character block, as calculated by the BufferReadout
	 */
	private double charWidth;
	
	/**
	 * The maximum number of lines that can fit on the BufferReadout's screen
	 */
	private int maxLines;
	
	/**
	 * The maximum number of the widest possible characters that can fit on a single line
	 */
	private int maxLineWidth;
	
	/**
	 * The spacing between adjacent lines on the BufferReadout's screen
	 */
	private static final int LINE_SPACING = 2;
	
	/**
	 * The index of the line to which the cursor currently points
	 */
	private int currIndex = 0;
	
	/**
	 * The font to use for the BufferReadout's text
	 */
	private String font;
	
	/**
	 * The maximum number of lines that can be stored in the BufferReadout's buffer
	 */
	private static final int BUFFER_SIZE = 128;
	
	/**
	 * Builder for the BufferReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Readout.Builder<Builder>
	{	
		/**
		 * The font of the BufferReadout, set to Consolas by default.
		 */
		private String font = "Consolas-*-*";
		
		/**
		 * Creates a Builder specifying a BufferReadout with the given dimensions.
		 * 
		 * @param width the width of the BufferReadout
		 * @param height the height of the BufferReadout
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the font to use for the BufferReadout's text.
		 * 
		 * @param font the font to use for the BufferReadout's text
		 * @return the current Builder
		 */
		public Builder withFont(String font)
		{
			this.font = font;
			return this;
		}
		
		/**
		 * Creates a new BufferReadout with the Builder's parameters.
		 * 
		 * @return a new BufferReadout with the Builder's parameters
		 */
		@Override
		public BufferReadout build()
		{
			return new BufferReadout(width, height, spacing, baseColor, color, accentColor, font);
		}
	}
	
	/**
	 * Creates a BufferReadout with the specified dimensions, spacing, base color, color, accent color, and font.
	 * 
	 * @param width the width of the BufferReadout
	 * @param height the width of the BufferReadout
	 * @param spacing the spacing of the BufferReadout
	 * @param baseColor the base color of the BufferReadout
	 * @param color the color of the BufferReadout's screen
	 * @param accentColor the color of the BufferReadout's cursor
	 * @param font the font of the BufferReadout
	 */
	protected BufferReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, String font)
	{
		messages = new ArrayList<String>();
		display = new ArrayList<GLabel>();
		this.font = font;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(color);
		add(back, spacing, spacing);
		
		testResolution();
		maxLineWidth = (int)((back.getWidth() - 2 * LINE_SPACING) / charWidth);
		maxLines = (int)(back.getHeight() / (charHeight + LINE_SPACING));
		
		bar = new GRect(width - 2 * spacing, charHeight + 2 * LINE_SPACING);
		bar.setFilled(true);
		bar.setFillColor(accentColor);
		add(bar, spacing, spacing);
		
		// Set up the display and set all chars to 0
		initDisplay();
		
		// Clear and update the display
		refresh();
		
		update("DISP:" + maxLines + "X" + maxLineWidth);
	}
	
	/**
	 * Increments the BufferReadout, scrolling the display up one line.
	 */
	@Override
	public void increment()
	{
		if(currIndex < messages.size() - 1) ++currIndex;
		refresh();
	}
	
	/**
	 * Decrements the BufferReadout, scrolling the display down one line.
	 */
	@Override
	public void decrement()
	{
		if(currIndex > 0) --currIndex;
		refresh();
	}
	
	/**
	 * Clears the BufferReadout's screen and internal buffer
	 */
	public void clear()
	{
		messages.clear();
		currIndex = 0;
		refresh();
	}
	
	/**
	 * Updates the BufferReadout with the specified message. Internally,
	 * the BufferReadout will only store what can fit on its screen.
	 */
	@Override
	public void update(String msg)
	{
		if(isFrozen()) return;
		if(messages.size() == BUFFER_SIZE) messages.remove(0);
		if(msg.length() > maxLineWidth) messages.add(msg.substring(0, maxLineWidth));
		else messages.add(msg);
		increment();
	}
	
	/**
	 * Clears the entire display and redraws all the text. Called after
	 * updating, incrementing, or decrementing the BufferReadout.
	 */
	private void refresh()
	{
		int numPrinted = 0;
		for(GLabel l : display)
		{
			l.setLabel("");
		}
		if(!messages.isEmpty())
		{
			for(int i = currIndex; i >= 0; --i)
			{
				if(numPrinted >= maxLines) break;
				display.get(numPrinted).setLabel(messages.get(i));
				++numPrinted;
			}
		}
	}
	
	/**
	 * Finds the maximum width and height of a character based on the
	 * size of the letter 'W' in the user-specified font.
	 */
	private void testResolution()
	{
		GLabel testLabel = new GLabel("W");
		testLabel.setFont(font);
		charWidth = testLabel.getWidth();
		testLabel.setLabel("W");
		charHeight = testLabel.getHeight();
	}
	
	/**
	 * Sets up the display and creates all the GLabels that will
	 * display the text.
	 */
	private void initDisplay()
	{
		String testString = "";
		for(int i = 0; i < maxLineWidth; ++i) testString += "0";
		for(int i = 0; i < maxLines; ++i)
		{
			GLabel line = new GLabel(testString);
			line.setFont(font);
			display.add(line);
			add(line, back.getX() + LINE_SPACING, charHeight + back.getY() + LINE_SPACING + i * (LINE_SPACING + charHeight));
		}
	}
}
