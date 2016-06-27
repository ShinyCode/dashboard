import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GObject;


public class MultiIncrementer extends ButtonGrid
{

	public static class Builder
	{
		private final double width;
		private final double height;
		
		private double spacing = 0;
		private Color baseColor = Color.BLACK;
		private Color buttonColor = Color.BLACK;
		
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public Builder withSpacing(double spacing)
		{
			this.spacing = spacing;
			return this;
		}
		
		public Builder withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return this;
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
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		addButton(decButton, 1, 0);
		selButton = new ToggleButton(width, height, buttonColor, "SEL");
		addButton(selButton, 2, 0);
		incrementables = new ArrayList<Incrementable>();
	}
	
	public void onMousePressed(GObject o)
	{
		if(!incrementables.isEmpty())
		{
			if(o == incButton)
			{
				if(selButton.isOn()) index = (index + 1) % incrementables.size();
				else incrementables.get(index).increment();
			}
			else if(o == decButton)
			{
				if(selButton.isOn()) index = (index - 1 + incrementables.size()) % incrementables.size();
				else incrementables.get(index).decrement();
			}
		}
	}
	
	public void addIncrementable(Incrementable inc)
	{
		if(inc != null) incrementables.add(inc);
	}
	
	private int index = 0;
	private TouchButton incButton;
	private TouchButton decButton;
	private ToggleButton selButton;
	private List<Incrementable> incrementables;
}
