package dashboard.control;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.*;

/**
 * Provides the base functionality for a container widget that can hold other widgets. The purpose is
 * to group widgets so as to make them easier to manage/move, as well as make it easier to extend
 * the library with more widget types.
 * 
 * @author Mark Sabini
 *
 */
public abstract class Group extends Control
{
	/**
	 * Maps keys to their respective widgets (GCompounds)
	 */
	private Map<String, GCompound> compounds;
	
	/**
	 * The Group's colored base
	 */
	private GRect base;
	
	/**
	 * How far from the widgets' bounding box the base should extend on each side
	 */
	private double spacing;
	
	/**
	 * Builder for the Group class, to be extended by all subclasses of Group.
	 * 
	 * @author Mark Sabini
	 *
	 * @param <T> dummy parameter to enable the subclasses to return their exact type
	 */
	@SuppressWarnings("rawtypes")
	protected abstract static class Builder<T extends Builder>
	{
		/**
		 * The spacing to use for the Group, set to 0.0 by default
		 */
		protected double spacing = 0.0;
		
		/**
		 * The minimum width of the Group
		 */
		protected final double minWidth;
		
		/**
		 * The minimum height of the Group
		 */
		protected final double minHeight;
		
		/**
		 * The color of the Group's base, set to black by default
		 */
		protected Color baseColor = Color.BLACK; // Default color
		
		/**
		 * Creates a Builder specifying a Group with the given minimum dimensions.
		 * 
		 * @param minWidth the minimum width of the Group
		 * @param minHeight the minimum height of the Group
		 */
		public Builder(double minWidth, double minHeight)
		{
			this.minWidth = minWidth;
			this.minHeight = minHeight;
		}
		
		/**
		 * Specifies the color of the Group's base.
		 * 
		 * @param baseColor the color of the Group's base
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		/**
		 * Specifies the spacing of the Group.
		 * 
		 * @param spacing the spacing of the Group
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withSpacing(double spacing)
		{
			this.spacing = spacing;
			return (T)this;
		}
		
		/**
		 * Creates a new Group with the Builder's parameters. Internally, the function should call
		 * the Group's protected constructor.
		 * 
		 * @return a new Group with the Builder's parameters
		 */
		public abstract Group build();
	}
	
	/**
	 * Creates a Group with the specified minimum dimensions, base color, and spacing.
	 * 
	 * @param minWidth the Group's minimum width
	 * @param minHeight the Group's minimum height
	 * @param spacing the Group's spacing
	 * @param baseColor the Group's base color
	 */
	protected Group(double minWidth, double minHeight, double spacing, Color baseColor)
	{
		this.spacing = spacing;
		compounds = new HashMap<String, GCompound>();
		base = new GRect(minWidth, minHeight);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	/**
	 * Adds the specified widget to the Group at coordinates (x, y) and binds it to the 
	 * given key. The coordinates are expressed relative to the Group's datum.
	 * 
	 * @param key a handle that refers to the widget
	 * @param cmp the widget to be added
	 * @param x the widget's x-coordinate
	 * @param y the widget's y-coordinate
	 * @return whether the widget was successfully added
	 */
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
	
	/**
	 * Returns the widget bound to the specified key.
	 * 
	 * @param key the key that was bound to the widget in {@link #addWidget(String, GCompound, double, double) addWidget}
	 * @return the widget if it was found. If the widget could not be located, the function returns null.
	 */
	protected GCompound getWidget(String key)
	{
		if(!compounds.containsKey(key)) return null;
		return compounds.get(key);
	}
}
