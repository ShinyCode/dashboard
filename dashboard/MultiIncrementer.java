package dashboard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GObject;


public final class MultiIncrementer extends ButtonGrid
{
	private int index = 0;
	private TouchButton incButton;
	private TouchButton decButton;
	private TouchButton selButton;
	private List<Incrementable> incrementables;
	
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
		
		public MultiIncrementer build()
		{
			return new MultiIncrementer(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	protected MultiIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 3, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		incButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) incrementables.get(index).increment();
			}
		});
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		decButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) incrementables.get(index).decrement();
			}
		});
		addButton(decButton, 1, 0);
		selButton = new TouchButton(width, height, buttonColor, "SEL");
		selButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) index = (index + 1) % incrementables.size();
			}
		});
		addButton(selButton, 2, 0);
		incrementables = new ArrayList<Incrementable>();
	}
	
	public void addIncrementable(Incrementable inc)
	{
		if(inc != null) incrementables.add(inc);
	}
}
