import java.awt.Color;

import acm.graphics.GObject;


public final class InstrReadoutControl extends ButtonGrid
{
	private TouchButton incButton, decButton, clsButton;
	private ToggleButton frzButton;
	private InstrReadout ir;
	
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
		
		public InstrReadoutControl build()
		{
			return new InstrReadoutControl(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	public InstrReadoutControl(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 4, 1, spacing, baseColor);
		clsButton = new TouchButton(width, height, buttonColor, "CLS");
		addButton(clsButton, 0, 0);
		frzButton = new ToggleButton(width, height, buttonColor, "FRZ");
		addButton(frzButton, 1, 0);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		addButton(incButton, 2, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		addButton(decButton, 3, 0);
	}
	
	public void onMousePressed(GObject o)
	{
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
	
	public void addInstrReadout(InstrReadout ir)
	{
		this.ir = ir;
	}
}
