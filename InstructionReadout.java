
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public class InstructionReadout extends GCompound implements Incrementable
{
	public InstructionReadout(double width, double height, double spacing, Color baseColor, Color backColor, Color barColor)
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
		appendMessage("DISP: " + maxLines + "X" + maxLineWidth);
		
		bar = new GRect(width - 2 * spacing, charHeight + 2 * LINE_SPACING);
		bar.setFilled(true);
		bar.setFillColor(barColor);
		add(bar, spacing, spacing);
		
		initDisplay();
	}
	
	public void increment()
	{
		
	}
	
	public void decrement()
	{
		
	}
	
	public void setFrozen(boolean flag)
	{
		
	}
	
	public void clear()
	{
		
	}
	
	public void appendMessage(String msg)
	{
		
	}
	
	private void refresh()
	{
		
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
}
