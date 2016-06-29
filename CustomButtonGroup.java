import java.awt.Color;
import java.util.HashMap;

import acm.graphics.GRect;


public final class CustomButtonGroup extends ButtonGroup
{
	public static final class Builder extends ButtonGroup.Builder<Builder>
	{
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public CustomButtonGroup build()
		{
			return new CustomButtonGroup(width, height, baseColor);
		}		
	}
	
	private CustomButtonGroup(double width, double height, Color baseColor)
	{
		super(width, height, baseColor);
	}
}
