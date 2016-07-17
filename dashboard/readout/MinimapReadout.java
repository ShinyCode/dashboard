package dashboard.readout;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import dashboard.util.GPointMath;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GArc;
import acm.graphics.GRect;

/**
 * Implements a Readout representing a minimap with pins to mark various locations. The minimap scale
 * can be varied during runtime using the method {@link #setViewRadius(double) setViewRadius}.
 * 
 * @author Mark Sabini
 *
 */
public final class MinimapReadout extends Readout implements LocationUpdatable
{
	/**
	 * The colored base of the MinimapReadout
	 */
	private GRect base;
	
	/**
	 * The circular minimap face of the MinimapReadout
	 */
	private GOval face;
	
	/**
	 * Maps keys to their respective pins
	 */
	private Map<String, Pin> pins;
	
	/**
	 * The distance in real units that the radius of the face should represent
	 */
	private double viewRadius;
	
	/**
	 * The cosmetic crosshair of the MinimapReadout
	 */
	private GCompound crosshair;
	
	/**
	 * The diameter of the pins on the MinimapReadout
	 */
	private static final double PIN_DIAMETER = 10.0;
	
	/**
	 * A small positive double to mitigate floating point errors in vector calculations
	 */
	private static final double EPSILON = 0.00001;
	
	/**
	 * Builder for the MinimapReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Readout.Builder<Builder>
	{	
		/**
		 * The view radius for the MinimapReadout, set to 50.0 by default
		 */
		private double viewRadius = 50.0;
		
		/**
		 * Creates a Builder specifying a MinimapReadout with the given dimensions.
		 * 
		 * @param width the width of the MinimapReadout
		 * @param height the height of the MinimapReadout
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the view radius of the MinimapReadout, i.e.
		 * the distance in real units that the radius of the face should represent
		 * 
		 * @param viewRadius the view radius of the MinimapReadout
		 * @throws IllegalArgumentException if the view radius is negative
		 * @return the current Builder
		 */
		public Builder withViewRadius(double viewRadius)
		{
			if(viewRadius < 0.0) throw new IllegalArgumentException();
			this.viewRadius = viewRadius;
			return this;
		}
		
		/**
		 * Creates a new MinimapReadout with the Builder's parameters.
		 * 
		 * @return a new MinimapReadout with the Builder's parameters
		 */
		@Override
		public MinimapReadout build()
		{
			return new MinimapReadout(width, height, spacing, baseColor, color, accentColor, viewRadius);
		}
	}
	
	/**
	 * Creates a MinimapReadout with the specified dimensions, spacing, base color, color, accent color, and view radius.
	 * 
	 * @param width the width of the MinimapReadout
	 * @param height the height of the MinimapReadout
	 * @param spacing the spacing of the MinimapReadout
	 * @param baseColor the base color of the MinimapReadout
	 * @param color the color of the MinimapReadout's face
	 * @param accentColor the color of the MinimapReadout's crosshair
	 * @param viewRadius the view radius of the MinimapReadout
	 */
	protected MinimapReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, double viewRadius)
	{
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		double faceDiameter = Math.min(width, height) - 2 * spacing;
		face = new GOval(faceDiameter, faceDiameter);
		face.setFilled(true);
		face.setFillColor(color);
		
		GArc arrow = new GArc(faceDiameter + spacing, faceDiameter + spacing, 45.0, 90.0);
		arrow.setFilled(true);
		arrow.setFillColor(accentColor);
		add(arrow, (width - faceDiameter - spacing) / 2.0, (height - faceDiameter - spacing) / 2.0);
		add(face, (width - faceDiameter) / 2.0, (height - faceDiameter) / 2.0);
		
		crosshair = new GCompound();
		GLine vertical = new GLine(faceDiameter / 2.0, 0.0, faceDiameter / 2.0, faceDiameter);
		vertical.setColor(accentColor);
		crosshair.add(vertical, faceDiameter / 2.0, 0);
		GLine horizontal = new GLine(0.0, faceDiameter / 2.0, faceDiameter, faceDiameter / 2.0);
		horizontal.setColor(accentColor);
		crosshair.add(horizontal, 0, faceDiameter / 2.0);
		GOval half = new GOval(faceDiameter / 2.0, faceDiameter / 2.0);
		half.setColor(accentColor);
		crosshair.add(half, faceDiameter / 4.0, faceDiameter / 4.0);
		add(crosshair, face.getX(), face.getY());
		
		pins = new HashMap<String, Pin>();
		this.viewRadius = viewRadius;
	}
	
	/**
	 * Clears and resets the display of the MinimapReadout.
	 */
	private void clearDisplay()
	{
		for(String key : pins.keySet())
		{
			remove(pins.get(key).getMarker());
		}
	}
	
	/**
	 * Clears and deletes all the pins in the MinimapReadout, effectively
	 * resetting its state.
	 */
	public void clear()
	{
		clearDisplay();
		pins.clear();
	}
	
	/**
	 * Creates and adds a pin with the given key, position, and color. While
	 * the pin is added to the MinimapReadout's internal map immediately, it
	 * will not be drawn until the next time the MinimapReadout is updated.
	 * If a pin with the same key already exists, no action is taken.
	 * 
	 * @param key a handle that refers to the pin
	 * @param position the position of the pin
	 * @param color the color of the pin
	 */
	public void addPin(String key, GPoint position, Color color)
	{
		if(pins.containsKey(key)) return; // Don't want to overwrite
		Pin pin = new Pin(position, color);
		pins.put(key, pin);
	}
	
	/**
	 * Removes the specified pin from both the display and
	 * the MinimapReadout's internal map. If the specified pin could
	 * not be found, no action is taken.
	 * 
	 * @param key the key referring to the pin to be removed
	 */
	public void removePin(String key)
	{
		if(!pins.containsKey(key)) return;
		remove(pins.get(key).getMarker());
		pins.remove(key);
	}
	
	/**
	 * Updates the MinimapReadout and redraws the entire display relative to the specified
	 * position and bearing. If a pin lies outside the boundaries of the face, it will not
	 * be drawn.
	 */
	@Override
	public void update(GPoint position, GPoint bearing)
	{
		if(isFrozen()) return;
		clearDisplay();
		if(position == null || bearing == null) return;
		for(String key : pins.keySet())
		{
			Pin pin = pins.get(key);
			// Determine the distance to see if the pin should be drawn
			GPoint disp = GPointMath.diff(pin.getPosition(), position);
			double dist = GPointMath.norm(disp);
			if(dist > viewRadius) continue; // Too far out to draw
			double pixelDist = dist * ((face.getWidth() - PIN_DIAMETER) / 2.0) / viewRadius;
			if((int)Math.round(pixelDist) == 0) // Edge case if points are in center
			{
				add(pin.getMarker(), face.getX() + (face.getWidth() - PIN_DIAMETER) / 2.0, face.getY() + (face.getHeight() - PIN_DIAMETER) / 2.0);
				continue;
			}
			// We find the angle as follows:
			double angleCos = GPointMath.dot(bearing, disp) / (GPointMath.norm(bearing) * GPointMath.norm(disp));
			double angle;
			if(Math.abs(angleCos + 1.0) < EPSILON) angle = Math.toRadians(180.0); // Edge case
			else
			{	
				double unsignedAngle = Math.acos(angleCos);
				double sign = Math.signum(GPointMath.cross(bearing, disp));
				angle = sign * unsignedAngle;
			}
			double newX = face.getX() + (face.getWidth() - PIN_DIAMETER) / 2.0 - pixelDist * Math.sin(angle);
			double newY = face.getY() + (face.getHeight() - PIN_DIAMETER) / 2.0 - pixelDist * Math.cos(angle);
			
			add(pin.getMarker(), newX, newY);	
		}
	}
	
	/**
	 * Sets the MinimapReadout's view radius to the one specified by the user.
	 * The new view radius must be a nonnegative double.
	 * 
	 * @param viewRadius the new view radius of the MinimapReadout
	 * @throws IllegalArgumentException if the view radius is negative
	 */
	public void setViewRadius(double viewRadius)
	{
		if(viewRadius < 0.0) throw new IllegalArgumentException();
		this.viewRadius = viewRadius;
	}
	
	/**
	 * A small helper class to bundle together the data associated with a pin
	 * in the MinimapReadout.
	 * 
	 * @author Mark Sabini
	 *
	 */
	private class Pin
	{
		/**
		 * The actual graphical pin to be drawn in the MinimapReadout
		 */
		private GOval marker;
		
		/**
		 * The position of the pin
		 */
		private GPoint position;
		
		/**
		 * Creates a pin with the specified position and color.
		 * 
		 * @param position the position of the pin
		 * @param color the color of the pin
		 */
		public Pin(GPoint position, Color color)
		{
			this.position = position;
			marker = new GOval(PIN_DIAMETER, PIN_DIAMETER);
			marker.setFilled(true);
			marker.setFillColor(color);
		}
		
		/**
		 * Returns the graphical marker associated with the pin
		 * 
		 * @return the graphical marker associated with the pin.
		 */
		public GOval getMarker()
		{
			return marker;
		}
		
		/**
		 * Returns the position of the pin
		 * 
		 * @return the position of the pin
		 */
		public GPoint getPosition()
		{
			return position;
		}
	}
}
