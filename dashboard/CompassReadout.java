package dashboard;

import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;


/**
 * Implements a Readout that represents a compass which always points in the direction of a
 * user-specified goal.
 * 
 * @author Mark Sabini
 *
 */
public final class CompassReadout extends Readout implements LocationUpdatable
{
	/**
	 * The colored base of the CompassReadout
	 */
	private GRect base;
	
	/**
	 * The circular face of the CompassReadout
	 */
	private GOval face;
	
	/**
	 * The triangular needle of the CompassReadout
	 */
	private GPolygon needle;
	
	/**
	 * The circle that will appear in the center of the CompassReadout when no goal is set
	 */
	private GOval center;
	
	/**
	 * The color of the CompassReadout's needle and center
	 */
	private Color accentColor;
	
	/**
	 * The current goal towards which the CompassReadout's needle should point
	 */
	private GPoint goal;
	
	/**
	 * The position of the most recent update
	 */
	private GPoint position;
	
	/**
	 * The bearing of the most recent upate
	 */
	private GPoint bearing;
	
	/**
	 * The ratio of the width of the needle's base to the CompassReadout's face diameter
	 */
	private static final double NEEDLE_BASE_RATIO = 0.25;
	
	/**
	 * Builder for the CompassReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Readout.Builder<Builder>
	{	
		/**
		 * The initial position of the CompassReadout
		 */
		private GPoint position = null;
		
		/**
		 * The initial bearing of the CompassReadout
		 */
		private GPoint bearing = null;
		
		/**
		 * The goal towards which the CompassReadout's needle should point
		 */
		private GPoint goal = null;
		
		/**
		 * Creates a Builder specifying a CompassReadout with the given dimensions.
		 * 
		 * @param width the width of the CompassReadout
		 * @param height the height of the CompassReadout
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the goal of the CompassReadout. The goal is allowed to be null,
		 * as that will "clear" the CompassReadout.
		 * 
		 * @param goal the goal towards which the CompassReadout's needle should point
		 * @return the current Builder
		 */
		public Builder withGoal(GPoint goal)
		{
			this.goal = goal;
			return this;
		}
		
		/**
		 * Specifies the initial position and bearing from which the CompassReadout
		 * should orient itself and its needle. The position and bearing must be
		 * either both null or both non-null.
		 * 
		 * @param position the initial position of the CompassReadout
		 * @param bearing the initial bearing of the CompassReadout
		 * @return
		 */
		public Builder withLocation(GPoint position, GPoint bearing)
		{
			if((position == null && bearing != null) || (position != null && bearing == null)) throw new IllegalStateException();
			this.position = position;
			this.bearing = bearing;
			return this;
		}
		
		/**
		 * Creates a new CompassReadout with the Builder's parameters.
		 * 
		 * @return a new CompassReadout with the Builder's parameters
		 */
		public CompassReadout build()
		{
			return new CompassReadout(width, height, spacing, baseColor, color, accentColor, position, bearing, goal);
		}
	}
	
	/**
	 * Creates a CompassReadout with the specified dimensions, spacing, base color, color, accent color, 
	 * position, bearing, and goal.
	 * 
	 * @param width the width of the CompassReadout
	 * @param height the width of the CompassReadout
	 * @param spacing the spacing of the CompassReadout
	 * @param baseColor the base color of the CompassReadout
	 * @param color the color of the CompassReadout's face
	 * @param accentColor the color of the CompassReadout's needle and center
	 * @param position the initial position of the CompassReadout
	 * @param bearing the initial bearing of the CompassReadout
	 * @param goal the initial goal of the CompassReadout
	 */
	protected CompassReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, GPoint position, GPoint bearing, GPoint goal)
	{
		this.accentColor = accentColor;
		this.position = position;
		this.bearing = bearing;
		this.goal = goal;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		double faceDiameter = Math.min(width, height) - 2 * spacing;
		face = new GOval(faceDiameter, faceDiameter);
		face.setFilled(true);
		face.setFillColor(color);
		add(face, (width - faceDiameter) / 2.0, (height - faceDiameter) / 2.0);
		
		// Initialize the center, but only draw it if goal is null.
		double centerDiameter = faceDiameter * NEEDLE_BASE_RATIO;
		center = new GOval(centerDiameter, centerDiameter);
		center.setFilled(true);
		center.setFillColor(accentColor);
		center.setVisible(false);
		add(center, (base.getWidth() - centerDiameter) / 2.0, (base.getHeight() - centerDiameter) / 2.0);
		
		// Initialize the needle to the 12 o'clock position.
		// Only draw the needle if all of goal, position, and bearing are not null.
		if(goal != null && position != null && bearing != null)
		{
			initNeedle(calculateAngle());
			add(needle, base.getWidth() / 2.0, base.getHeight() / 2.0);
		}
		else center.setVisible(true);
	}
	
	/**
	 * Updates the CompassReadout with the new position and bearing. The position and
	 * the bearing must be either both null or both non-null.
	 */
	@Override
	public void update(GPoint position, GPoint bearing)
	{
		if(isFrozen()) return;
		if((position == null && bearing != null) || (position != null && bearing == null)) throw new IllegalStateException();
		if(goal == null)
		{
			this.position = position;
			this.bearing = bearing;
			return;
		}
		if(needle != null) remove(needle);
		// If goal is not null, update the needle.
		if(position != null && bearing != null)
		{
			center.setVisible(false);
			this.position = position;
			this.bearing = bearing;
			initNeedle(calculateAngle());
			add(needle, base.getWidth() / 2.0, base.getHeight() / 2.0);
		}
		else
		{
			center.setVisible(true);
		}
	}
	
	/**
	 * Updates the CompassReadout's goal and immediately redraws the needle.
	 * 
	 * @param goal the new goal for the CompassReadout
	 */
	public void updateGoal(GPoint goal)
	{
		this.goal = goal;
		update(position, bearing);
	}
	
	/**
	 * Clears and resets the CompassReadout's goal, position, and bearing.
	 */
	public void clear()
	{
		goal = null;
		position = null;
		bearing = null;
		if(needle != null) remove(needle);
		center.setVisible(true);
	}
	
	/**
	 * Blindly creates the needle and draws it at the specified angle, measured
	 * counter-clockwise from the vertical. The generated needle needs to be manually
	 * added and removed.
	 * 
	 * @param angle the angle at which to draw the needle, measured counter-clockwise from the vertical
	 */
	private void initNeedle(double angle)
	{
		double needleBase = face.getWidth() * NEEDLE_BASE_RATIO;
		needle = new GPolygon();
		needle.addVertex(-needleBase / 2.0, needleBase / 2.0);
		needle.addVertex(needleBase / 2.0, needleBase / 2.0);
		needle.addVertex(0.0, (-face.getWidth() + needleBase) / 2.0);
		needle.setFilled(true);
		needle.setFillColor(accentColor);
		needle.rotate(angle);
	}
	
	/**
	 * Calculates and returns the angle at which the needle should be drawn in {@link #initNeedle(double) initNeedle}.
	 * 
	 * @return the angle at which the needle should be drawn
	 */
	private double calculateAngle()
	{
		GPoint disp = GPointMath.diff(goal, position);
		double angleCos = GPointMath.dot(bearing, disp) / (GPointMath.norm(bearing) * GPointMath.norm(disp));
		if(angleCos == -1.0) return 180.0; // Edge case. TODO: improve for fp arithmetic.
		double unsignedAngle = Math.acos(angleCos);
		double sign = Math.signum(GPointMath.cross(bearing, disp));
		return sign * Math.toDegrees(unsignedAngle);
	}
}
