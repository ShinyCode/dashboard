package dashboard;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.*;

public class DashboardProgram extends GraphicsProgram
{
	private Map<String, GObject> widgets;
	private Map<String, GRect> sizes;
	private Map<String, StringUpdatable> commandOutputs;
	private GCompound union;
	private GRect background;
	private double x;
	private double y;
	
	protected DashboardProgram()
	{
		super();
		widgets = new HashMap<String, GObject>();
		sizes = new HashMap<String, GRect>();
		commandOutputs = new HashMap<String, StringUpdatable>();
		union = new GCompound();
		calculateXY();
	}
	
	public final void addWidget(String key, GObject widget)
	{
		if(widgets.containsKey(key)) return;
		widgets.put(key, widget);
		GRect size = new GRect(widget.getWidth(), widget.getHeight());
		sizes.put(key, size);
		union.add(size);
		add(widget);
		checkXY(widget);
	}
	
	public final void addWidget(String key, GObject widget, double x, double y)
	{
		if(widgets.containsKey(key)) return;
		widgets.put(key, widget);
		GRect size = new GRect(widget.getWidth(), widget.getHeight());
		sizes.put(key, size);
		union.add(size, x, y);
		add(widget, x, y);
		checkXY(widget);
	}
	
	public final void addWidget(String key, GObject widget, GPoint pt)
	{
		if(widgets.containsKey(key)) return;
		widgets.put(key, widget);
		GRect size = new GRect(widget.getWidth(), widget.getHeight());
		sizes.put(key, size);
		union.add(size, pt);
		add(widget, pt);
		checkXY(widget);
	}
	
	public final void removeWidget(String key)
	{
		if(!widgets.containsKey(key)) return;
		GObject widget = widgets.get(key);
		remove(widget);
		widgets.remove(widget);
		GRect size = sizes.get(key);
		union.remove(size);
		calculateXY();
	}
	
	public final GObject getWidget(String key)
	{
		if(!widgets.containsKey(key)) return null;
		return widgets.get(key);
	}
	
	public final double getTotalWidgetWidth()
	{
		return union.getWidth();
	}
	
	public final double getTotalWidgetHeight()
	{
		return union.getHeight();
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		if(cmd != null) updateAllCommandOutputs(cmd);
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		if(cmd != null) updateAllCommandOutputs(cmd);
	}
	
	protected void addBorder(GObject o, Color c, double borderWidth)
	{
		GRect background = new GRect(o.getWidth() + 2 * borderWidth, o.getHeight() + 2 * borderWidth);
		background.setFilled(true);
		background.setFillColor(c);
		add(background, o.getX() - borderWidth, o.getY() - borderWidth);
		o.sendToFront();
	}
	
	public double getWidgetLeftX()
	{
		return x;
	}
	
	public double getWidgetTopY()
	{
		return y;
	}
	
	private void updateAllCommandOutputs(String cmd)
	{
		for(String key : commandOutputs.keySet())
		{
			commandOutputs.get(key).update(cmd);
		}
	}
	
	private void calculateXY()
	{
		if(widgets.isEmpty())
		{
			x = 0;
			y = 0;
			return;
		}
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		for(String key : widgets.keySet())
		{
			GObject obj = widgets.get(key);
			if(obj.getX() < minX) minX = obj.getX();
			if(obj.getY() < minY) minY = obj.getY();
		}
		x = minX;
		y = minY;
	}
	
	private void checkXY(GObject obj)
	{
		if(obj.getX() < x) x = obj.getX();
		if(obj.getY() < y) y = obj.getY();
	}
	
	public final void addCommandOutput(String key, StringUpdatable su)
	{
		if(commandOutputs.containsKey(key)) return;
		commandOutputs.put(key, su);
	}
	
	public final void removeCommandOutput(String key)
	{
		if(!commandOutputs.containsKey(key)) return;
		commandOutputs.remove(key);
	}
	
	public final void addBackground(double spacing, Color baseColor)
	{
		if(background != null) remove(background);
		background = new GRect(union.getWidth() + 2 * spacing, union.getHeight() + 2 * spacing);
		System.out.println(background.getWidth() + " " + background.getHeight());
		background.setFilled(true);
		background.setFillColor(baseColor);
		System.out.println(getWidgetLeftX() + " " + getWidgetTopY());
		add(background, getWidgetLeftX() - spacing, getWidgetTopY() - spacing);
		background.sendToBack();
	}
	
	public final void removeBackground()
	{
		if(background == null) return;
		remove(background);
	}
}
