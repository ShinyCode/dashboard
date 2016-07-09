package dashboard;

import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

public final class CompassReadout extends Readout implements LocationUpdatable
{
	private GRect base;
	private GOval face;
	private GPolygon needle;
	private GOval center;
	private Color accentColor;
	private GPoint goal;
	private GPoint position;
	private GPoint bearing;
	
	private static final double NEEDLE_SIZE = 0.25; // Ratio of needle base to face diameter
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private GPoint position = null;
		private GPoint bearing = null;
		private GPoint goal = null;
		
		// The CompassReadout needs to be fed the location of the ship when it is created.
		// If location is null, then the CompassReadout will throw an IllegalStateException
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		// The user can set an initial goal. It is okay if the goal is null,
		// since that "clears" the CompassReadout
		public Builder withGoal(GPoint goal)
		{
			this.goal = goal;
			return this;
		}
		
		public Builder withLocation(GPoint position, GPoint bearing)
		{
			if((position == null && bearing != null) || (position != null && bearing == null)) throw new IllegalStateException();
			this.position = position;
			this.bearing = bearing;
			return this;
		}
		
		public CompassReadout build()
		{
			return new CompassReadout(width, height, spacing, baseColor, color, accentColor, position, bearing, goal);
		}
	}
	
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
		double centerDiameter = faceDiameter * NEEDLE_SIZE;
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
	
	public void update(GPoint position, GPoint bearing) // Need BOTH the direction and the position!
	{
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
	
	public void updateGoal(GPoint goal)
	{
		this.goal = goal;
		update(position, bearing);
	}
	
	public void clear()
	{
		goal = null;
		position = null;
		bearing = null;
		if(needle != null) remove(needle);
		center.setVisible(true);
	}
	
	// Blindly creates the needle. Need to manually add and remove the generated needle.
	// Angle measured ccw from vertical
	// TODO: Make this more efficient.
	private void initNeedle(double angle)
	{
		double needleBase = face.getWidth() * NEEDLE_SIZE;
		needle = new GPolygon();
		needle.addVertex(-needleBase / 2.0, needleBase / 2.0);
		needle.addVertex(needleBase / 2.0, needleBase / 2.0);
		needle.addVertex(0.0, (-face.getWidth() + needleBase) / 2.0);
		needle.setFilled(true);
		needle.setFillColor(accentColor);
		needle.rotate(angle);
	}
	
	private double calculateAngle()
	{
		double dispX = goal.getX() - position.getX();
		double dispY = goal.getY() - position.getY();
		double dotProd = bearing.getX() * dispX + bearing.getY() * dispY;
		double angleCos = dotProd / (norm(bearing.getX(), bearing.getY()) * norm(dispX, dispY));
		if(angleCos == -1.0) return 180.0; // Edge case. TODO: improve for fp arithmetic.
		double unsignedAngle = Math.acos(angleCos);
		double sign = Math.signum(bearing.getX() * dispY - bearing.getY() * dispX);
		return sign * Math.toDegrees(unsignedAngle);
	}
	
	private double norm(double x, double y)
	{
		return Math.sqrt(x * x + y * y);
	}
}
