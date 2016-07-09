package dashboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public final class BufferReadout extends Readout implements Incrementable, StringUpdatable
{
	private List<String> messages;
	private List<GLabel> display;
	private GRect base, back, bar;
	private double charHeight;
	private double charWidth;
	private int maxLines;
	private int maxLineWidth;
	private static final int LINE_SPACING = 2;
	private boolean frozen = false;
	private int currIndex = 0;
	private String font;
	
	private static final int BUFFER_SIZE = 128;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private String font = "Consolas-*-*";
		
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withFont(String font)
		{
			this.font = font;
			return this;
		}
		
		public BufferReadout build()
		{
			return new BufferReadout(width, height, spacing, baseColor, color, accentColor, font);
		}
	}
	
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
	
	public void increment()
	{
		if(currIndex < messages.size() - 1) ++currIndex;
		refresh();
	}
	
	public void decrement()
	{
		if(currIndex > 0) --currIndex;
		refresh();
	}
	
	public void setFrozen(boolean flag)
	{
		frozen = flag;
	}
	
	public void clear()
	{
		messages.clear();
		currIndex = 0;
		refresh();
	}
	
	public void update(String msg)
	{
		if(frozen) return;
		if(messages.size() == BUFFER_SIZE) messages.remove(0);
		if(msg.length() > maxLineWidth) messages.add(msg.substring(0, maxLineWidth));
		else messages.add(msg);
		increment();
	}
	
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
	
	private void testResolution()
	{
		GLabel testLabel = new GLabel("W");
		testLabel.setFont(font);
		charWidth = testLabel.getWidth();
		testLabel.setLabel("W");
		charHeight = testLabel.getHeight();
	}
	
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
