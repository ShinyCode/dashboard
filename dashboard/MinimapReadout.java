package dashboard;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GArc;
import acm.graphics.GRect;

public final class MinimapReadout extends Readout implements LocationUpdatable
{
	private GRect base;
	private GOval face;
	private Map<String, Pin> pins;
	private double viewRadius;
	private GCompound crosshair;
	
	private static final double PIN_DIAMETER = 10.0;
	private static final double EPSILON = 0.00001;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private double viewRadius = 50.0;
		
		
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withViewRadius(double viewRadius)
		{
			if(viewRadius < 0.0) throw new IllegalArgumentException();
			this.viewRadius = viewRadius;
			return this;
		}
		
		public MinimapReadout build()
		{
			return new MinimapReadout(width, height, spacing, baseColor, color, accentColor, viewRadius);
		}
	}
	
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
	
	private void clearDisplay() // Clear the display for redrawing.
	{
		for(String key : pins.keySet())
		{
			remove(pins.get(key).getMarker());
		}
	}
	
	public void clear() // Removes and deletes all the pins
	{
		clearDisplay();
		pins.clear();
	}
	
	// Will not be drawn until update is called
	public void addPin(String key, GPoint position, Color color)
	{
		if(pins.containsKey(key)) return; // Don't want to overwrite
		Pin pin = new Pin(position, color);
		pins.put(key, pin);
	}
	
	public void removePin(String key)
	{
		if(!pins.containsKey(key)) return;
		remove(pins.get(key).getMarker());
		pins.remove(key);
	}
	
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
			double dispX = pin.getPosition().getX() - position.getX();
			double dispY = pin.getPosition().getY() - position.getY();
			double dist = Math.sqrt(dispX * dispX + dispY * dispY);
			if(dist > viewRadius) continue; // Too far out to draw
			double pixelDist = dist * ((face.getWidth() - PIN_DIAMETER) / 2.0) / viewRadius;
			if((int)Math.round(pixelDist) == 0) // Edge case if points are in center
			{
				add(pin.getMarker(), face.getX() + (face.getWidth() - PIN_DIAMETER) / 2.0, face.getY() + (face.getHeight() - PIN_DIAMETER) / 2.0);
				continue;
			}
			// We find the angle: TODO: Make into a static method in a util class
			double dotProd = bearing.getX() * dispX + bearing.getY() * dispY;
			double angleCos = dotProd / (norm(bearing.getX(), bearing.getY()) * norm(dispX, dispY));
			double angle;
			if(Math.abs(angleCos + 1.0) < EPSILON) angle = Math.toRadians(180.0); // Edge case. TODO: improve for fp arithmetic.
			else
			{	
				double unsignedAngle = Math.acos(angleCos);
				double sign = Math.signum(bearing.getX() * dispY - bearing.getY() * dispX);
				angle = sign * unsignedAngle;
			}
			double newX = face.getX() + (face.getWidth() - PIN_DIAMETER) / 2.0 - pixelDist * Math.sin(angle);
			double newY = face.getY() + (face.getHeight() - PIN_DIAMETER) / 2.0 - pixelDist * Math.cos(angle);
			
			add(pin.getMarker(), newX, newY);	
		}
	}
	
	// TODO: Make into a helper method in an outside class
	private double norm(double x, double y)
	{
		return Math.sqrt(x * x + y * y);
	}
	
	public void setViewRadius(double viewRadius)
	{
		if(viewRadius < 0.0) throw new IllegalArgumentException();
		this.viewRadius = viewRadius;
	}
	
	private class Pin
	{
		private GOval marker;
		private GPoint position;
		
		public Pin(GPoint position, Color color)
		{
			this.position = position;
			marker = new GOval(PIN_DIAMETER, PIN_DIAMETER);
			marker.setFilled(true);
			marker.setFillColor(color);
		}
		
		public GOval getMarker()
		{
			return marker;
		}
		
		public GPoint getPosition()
		{
			return position;
		}
	}
}
