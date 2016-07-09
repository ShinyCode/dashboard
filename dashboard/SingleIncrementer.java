package dashboard;
import java.awt.Color;

import acm.graphics.GObject;


public final class SingleIncrementer extends ButtonGrid
{
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
	
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
		
		public SingleIncrementer build()
		{
			return new SingleIncrementer(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	protected SingleIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		incButton.setOnAction(new Runnable()
		{
			public void run()
			{
				inc.increment();
			}
		});
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		decButton.setOnAction(new Runnable()
		{
			public void run()
			{
				inc.decrement();
			}
		});
		addButton(decButton, 1, 0);
	}
	
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
}
