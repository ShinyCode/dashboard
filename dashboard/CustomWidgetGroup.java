package dashboard;
import java.awt.Color;

import acm.graphics.GCompound;


public final class CustomWidgetGroup extends WidgetGroup
{
	public static final class Builder extends WidgetGroup.Builder<Builder>
	{
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public CustomWidgetGroup build()
		{
			return new CustomWidgetGroup(width, height, baseColor);
		}		
	}
	
	protected CustomWidgetGroup(double width, double height, Color baseColor)
	{
		super(width, height, baseColor);
	}
	
	public boolean addWidget(String key, GCompound cmp, double x, double y)
	{
		return super.addWidget(key, cmp, x, y);
	}
	
	public GCompound getWidget(String key)
	{
		return super.getWidget(key);
	}
}
