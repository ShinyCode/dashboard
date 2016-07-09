package dashboard;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.*;

public abstract class Group extends MouseWidget
{
	private Map<String, GCompound> compounds;
	private GRect base;
	private double spacing;
	
	protected abstract static class Builder<T extends Builder>
	{
		protected double spacing = 0.0;
		
		protected final double minWidth;
		protected final double minHeight;
		
		protected Color baseColor = Color.BLACK; // Default color
		
		public Builder(double minWidth, double minHeight)
		{
			this.minWidth = minWidth;
			this.minHeight = minHeight;
		}
		
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		public T withSpacing(double spacing)
		{
			this.spacing = spacing;
			return (T)this;
		}
		
		public abstract Group build();
	}
	
	protected Group(double minWidth, double minHeight, Color baseColor, double spacing)
	{
		this.spacing = spacing;
		compounds = new HashMap<String, GCompound>();
		base = new GRect(minWidth, minHeight);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	protected boolean addWidget(String key, GCompound cmp, double x, double y)
	{
		if(compounds.containsKey(key)) return false;
		compounds.put(key, cmp);
		add(cmp, x, y);
		// Redraw the base box
		remove(base);
		GRectangle bounds = getBounds();
		double newWidth = base.getWidth();
		if(bounds.getWidth() + 2 * spacing > base.getWidth()) newWidth = bounds.getWidth() + 2 * spacing;
		double newHeight = base.getHeight();
		if(bounds.getHeight() + 2 * spacing > base.getHeight()) newHeight = bounds.getHeight() + 2 * spacing;
		base.setBounds(bounds.getX() - spacing, bounds.getY() - spacing, newWidth, newHeight);
		add(base);
		sendToBack(base);
		return true;
	}
	
	protected GCompound getWidget(String key)
	{
		if(!compounds.containsKey(key)) return null;
		return compounds.get(key);
	}
}
