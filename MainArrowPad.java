import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GObject;


public class MainArrowPad extends ButtonGrid
{
	public MainArrowPad(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 3, 3, spacing, baseColor);
		aillButton = new TouchButton(width, height, buttonColor, "AILL");
		addButton(aillButton, 0, 0);
		fwdButton = new TouchButton(width, height, buttonColor, "FWD");
		addButton(fwdButton, 0, 1);
		ailrButton = new TouchButton(width, height, buttonColor, "AILR");
		addButton(fwdButton, 0, 2);
		bnlButton = new TouchButton(width, height, buttonColor, "BNL");
		addButton(bnlButton, 1, 0);
		revButton = new TouchButton(width, height, buttonColor, "REV");
		addButton(revButton, 1, 1);
		bnrButton = new TouchButton(width, height, buttonColor, "BNR");
		addButton(bnrButton, 1, 2);
		rudlButton = new TouchButton(width, height, buttonColor, "RUDL");
		addButton(rudlButton, 2, 0);
		mimButton = new ToggleButton(width, height, buttonColor, "MIM");
		addButton(mimButton, 2, 1);
		rudrButton = new TouchButton(width, height, buttonColor, "RUDR");
		addButton(rudrButton, 2, 2);
		auxArrowPads = new ArrayList<AuxArrowPad>();
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
		if(mimButton.isOn() && o != mimButton)
		{
			
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mouseReleased(e);
		if(mimButton.isOn() && o != mimButton)
		{
			
		}
	}
	
	private TouchButton aillButton, fwdButton, ailrButton, bnlButton, revButton, bnrButton, rudlButton, rudrButton;
	private ToggleButton mimButton;
	private List<AuxArrowPad> auxArrowPads;
}
