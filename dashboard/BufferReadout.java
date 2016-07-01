
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public final class BufferReadout extends GCompound implements Incrementable, Updatable
{
	public BufferReadout(double width, double height, double spacing, Color baseColor, Color backColor, Color barColor)
	{
		messages = new ArrayList<String>();
		display = new ArrayList<GLabel>();
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(backColor);
		add(back, spacing, spacing);
		
		testResolution();
		maxLineWidth = (int)((back.getWidth() - 2 * LINE_SPACING) / charWidth);
		maxLines = (int)(back.getHeight() / (charHeight + LINE_SPACING));
		
		bar = new GRect(width - 2 * spacing, charHeight + 2 * LINE_SPACING);
		bar.setFilled(true);
		bar.setFillColor(barColor);
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
		if(!frozen) refresh();
	}
	
	public void clear()
	{
		messages.clear();
		currIndex = 0;
		refresh();
	}
	
	public void update(String msg)
	{
		if(!frozen)
		{
			if(messages.size() == BUFFER_SIZE) messages.remove(0);
			if(msg.length() > maxLineWidth) messages.add(msg.substring(0, maxLineWidth));
			else messages.add(msg);
			increment();
		}
	}
	
	public void update(int data)
	{
		int numDigits = maxLineWidth - 2;
		if(numDigits < 1) return;
		String dispString = "";
		if(data < 0) dispString += "-";
		else dispString += "+";
		String dataString = Integer.toString(Math.abs(data));
		int numSpaces = maxLineWidth - 2 - dataString.length();
		if(numSpaces < 0) // Need to truncate
		{
			dispString += "*"; // Indicate that the value is inaccurate
			dispString += dataString.substring(dataString.length() - (maxLineWidth - 2));
		}
		else
		{
			dispString += " ";
			for(int i = 0; i < numSpaces; ++i)
			{
				dispString += " ";
			}
			dispString += dataString;
		}
		update(dispString);
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
		testLabel.setFont("Consolas-*-*");
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
			line.setFont("Consolas-*-*");
			display.add(line);
			add(line, back.getX() + LINE_SPACING, charHeight + back.getY() + LINE_SPACING + i * (LINE_SPACING + charHeight));
		}
	}
	
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
	private static final int BUFFER_SIZE = 100;
}
