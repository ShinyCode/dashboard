package dashboard;
import java.awt.Color;

import acm.graphics.GCompound;

/**
 * Implements a custom group of widgets that can be constructed at runtime. Pre-fabricated widget groups
 * should subclass {@link Group} instead of CustomGroup.
 * 
 * @author Mark Sabini
 *
 */
public final class CustomGroup extends Group
{
	/**
	 * Builder for the CustomGroup class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Group.Builder<Builder>
	{
		/**
		 * Creates a Builder for a CustomGroup. The initial size is 0.0 by 0.0, and expands
		 * to accommodate widgets as they are added.
		 */
		public Builder()
		{
			super(0.0, 0.0);
		}
		
		/**
		 * Creates a Builder specifying a CustomGroup with the given minimum dimensions.
		 * 
		 * @param minWidth the minimum width of the CustomGroup
		 * @param minHeight the minimum height of the CustomGroup
		 */
		public Builder(double minWidth, double minHeight)
		{
			super(minWidth, minHeight);
		}
		
		/**
		 * Creates a new CustomGroup with the Builder's parameters.
		 */
		public CustomGroup build()
		{
			return new CustomGroup(minWidth, minHeight, baseColor, spacing);
		}		
	}
	
	/**
	 * Creates a CustomGroup with the specified minimum dimensions, base color, and spacing.
	 * 
	 * @param minWidth the CustomGroup's minimum width
	 * @param minHeight the CustomGroup's minimum height
	 * @param baseColor the CustomGroup's base color
	 * @param spacing the CustomGroup's spacing
	 */
	protected CustomGroup(double minWidth, double minHeight, Color baseColor, double spacing)
	{
		super(minWidth, minHeight, baseColor, spacing);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addWidget(String key, GCompound cmp, double x, double y)
	{
		return super.addWidget(key, cmp, x, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GCompound getWidget(String key)
	{
		return super.getWidget(key);
	}
}
