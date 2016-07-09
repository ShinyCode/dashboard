package dashboard;
import java.awt.Color;

import acm.graphics.GCompound;


public final class CustomGroup extends Group
{
	public static final class Builder extends Group.Builder<Builder>
	{
		public Builder()
		{
			super(0.0, 0.0);
		}
		
		public Builder(double minWidth, double minHeight)
		{
			super(minWidth, minHeight);
		}
		
		public CustomGroup build()
		{
			return new CustomGroup(minWidth, minHeight, baseColor, spacing);
		}		
	}
	
	protected CustomGroup(double width, double height, Color baseColor, double spacing)
	{
		super(width, height, baseColor, spacing);
	}
	
	@Override
	public boolean addWidget(String key, GCompound cmp, double x, double y)
	{
		return super.addWidget(key, cmp, x, y);
	}
	
	@Override
	public GCompound getWidget(String key)
	{
		return super.getWidget(key);
	}
}
