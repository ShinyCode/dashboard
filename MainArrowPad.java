import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GObject;


public final class MainArrowPad extends ButtonGrid
{
	private TouchButton aillButton, fwdButton, ailrButton, bnlButton, revButton, bnrButton, rudlButton, rudrButton;
	private ToggleButton mimButton;
	private List<AuxArrowPad> auxArrowPads;
	
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		private Color buttonColor = Color.BLACK;
		
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		public MainArrowPad build()
		{
			return new MainArrowPad(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	protected MainArrowPad(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 3, 3, spacing, baseColor);
		aillButton = new TouchButton(width, height, buttonColor, "AILL");
		addButton(aillButton, 0, 0);
		fwdButton = new TouchButton(width, height, buttonColor, "FWD");
		addButton(fwdButton, 0, 1);
		ailrButton = new TouchButton(width, height, buttonColor, "AILR");
		addButton(ailrButton, 0, 2);
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
	
	public void onMousePressed(GObject o)
	{
		if(mimButton.isOn() && o != mimButton && o instanceof Button)
		{
			for(AuxArrowPad aap : auxArrowPads)
			{
				aap.setButtonState(((Button) o).getInstr(), true);
			}
		}
	}
	
	public void onMouseReleased(GObject o)
	{
		if(mimButton.isOn() && o != mimButton && o instanceof Button)
		{
			for(AuxArrowPad aap : auxArrowPads)
			{
				aap.setButtonState(((Button) o).getInstr(), false);
			}
		}
	}
	
	public void addAuxArrowPad(AuxArrowPad aap)
	{
		auxArrowPads.add(aap);
	}
}
