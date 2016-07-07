package dashboard;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.*;

public abstract class WidgetGroup extends MouseWidget
{
	private Map<String, GCompound> compounds;
	private GRect base;
	
	protected abstract static class Builder<T extends Builder>
	{
		protected final double width;
		protected final double height;
		
		protected Color baseColor = Color.BLACK; // Default color
		
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		// First of all, width should be minWidth, height should be minHeight.
		// Also, need to add withSpacing for the border
		
		public abstract WidgetGroup build();
	}
	
	protected WidgetGroup(double width, double height, Color baseColor)
	{
		compounds = new HashMap<String, GCompound>();
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	protected boolean addWidget(String key, GCompound cmp, double x, double y)
	{
		if(compounds.containsKey(key)) return false;
		compounds.put(key, cmp);
		add(cmp, x, y);
		return true;
	}
	
	protected GCompound getWidget(String key)
	{
		if(!compounds.containsKey(key)) return null;
		return compounds.get(key);
	}
	
	
}
