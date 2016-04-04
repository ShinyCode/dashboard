import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GObject;


public class InstrReadoutControl extends ButtonGrid
{
	public InstrReadoutControl(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 4, 1, spacing, baseColor);
		clsButton = new TouchButton(width, height, buttonColor, "CLS");
		add(clsButton, 0, 0);
		frzButton = new ToggleButton(width, height, buttonColor, "FRZ");
		add(frzButton, 1, 0);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		add(incButton, 2, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		add(decButton, 3, 0);
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
		if(ir != null)
		{
			if(o == incButton)
			{
				ir.increment();
			}
			else if(o == decButton)
			{
				ir.decrement();
			}
			else if(o == clsButton)
			{
				ir.clear();
			}
			else if(o == frzButton)
			{
				ir.setFrozen(frzButton.isOn());
			}
		}
	}
	
	public void addInstructionReadout(InstrReadout ir)
	{
		this.ir = ir;
	}
	
	private TouchButton incButton, decButton, clsButton;
	private ToggleButton frzButton;
	private InstrReadout ir;
}
