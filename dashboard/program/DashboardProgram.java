package dashboard.program;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import dashboard.control.Control;
import dashboard.readout.StringUpdatable;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.*;

/**
 * Implements a subclass of GraphicsProgram with specialized methods for drawing dashboards.
 * 
 * @author Mark Sabini
 *
 */
public abstract class DashboardProgram extends GraphicsProgram
{
	/**
	 * The map from keys (Strings) to the widgets (GObjects) that make up the dashboard
	 */
	private Map<String, GObject> widgets;
	
	/**
	 * The map from keys (Strings) to the bounding boxes (GRects) of the dashboard's widgets
	 */
	private Map<String, GRect> sizes;
	
	/**
	 * The map from keys (Strings) to command outputs (StringUpdatables), which will print out
	 * command hierarchies in real time
	 */
	private Map<String, StringUpdatable> commandOutputs;
	
	/**
	 * A GCompound containing all the bounding boxes of the dashboard's widgets. Used for calculating
	 * the dashboard's left x, top y, total width, and total height
	 */
	private GCompound union;
	
	/**
	 * The background (colored base) of the dashboard
	 */
	private GRect background;
	
	/**
	 * The dashboard's leftmost x coordinate, cached to prevent unnecessary recalculation
	 */
	private double x;
	
	/**
	 * The dashboard's topmost y coordinate, cached to prevent unnecessary recalculation
	 */
	private double y;
	
	/**
	 * Creates a DashboardProgram and initializes/allocates all of its fields
	 */
	protected DashboardProgram()
	{
		super();
		widgets = new HashMap<String, GObject>();
		sizes = new HashMap<String, GRect>();
		commandOutputs = new HashMap<String, StringUpdatable>();
		union = new GCompound();
		calculateXY();
	}
	
	/**
	 * Adds the specified widget to the DashboardProgram's widget roster and draws it
	 * on the canvas
	 * 
	 * @param key a handle that refers to the widget
	 * @param widget the widget to be added
	 */
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
	
	/**
	 * Adds the specified widget to the DashboardProgram's widget roster and draws it
	 * on the canvas at the specified (x, y) coordinates
	 * 
	 * @param key a handle that refers to the widget
	 * @param widget the widget to be added
	 * @param x the x-coordinate of the widget
	 * @param y the y-coordinate of the widget
	 */
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
	
	/**
	 * Adds the specified widget to the DashboardProgram's widget roster and draws it
	 * on the canvas at the specified point
	 * 
	 * @param key a handle that refers to the widget
	 * @param widget the widget to be added
	 * @param pt a point corresponding to the widget's location
	 */
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
	
	/**
	 * Removes the widget associated with the specified key from the DashboardProgram's
	 * widget roster and the canvas. If the widget could not be located, no action is
	 * taken.
	 * 
	 * @param key the key that is bound to the widget
	 */
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
	
	/**
	 * Returns the widget associated with the specified key. If the widget could not be
	 * located, null is returned.
	 * 
	 * @param key the key that is bound to the widget
	 * @return the widget associated with the specified key
	 */
	public final GObject getWidget(String key)
	{
		if(!widgets.containsKey(key)) return null;
		return widgets.get(key);
	}
	
	/**
	 * Returns the width of the bounding box surrounding all widgets on screen. The widgets
	 * must have been added using addWidget (as opposed to add) in order for them to
	 * contribute to the total widget width.
	 * 
	 * @return the width of the bounding box surrounding all widgets on screen
	 */
	public final double getTotalWidgetWidth()
	{
		return union.getWidth();
	}
	
	/**
	 * Returns the height of the bounding box surrounding all widgets on screen. The widgets
	 * must have been added using addWidget (as opposed to add) in order for them to
	 * contribute to the total widget height.
	 * 
	 * @return the height of the bounding box surrounding all widgets on screen
	 */
	public final double getTotalWidgetHeight()
	{
		return union.getHeight();
	}
	
	/**
	 * Handles the mousePressed event, transmits the MouseEvent to any underlying
	 * subwidgets, and updates all command outputs with the command hierarchy.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) cmd = ((Control) o).mousePressed(e);
		if(cmd != null) updateAllCommandOutputs(cmd);
	}
	
	/**
	 * Handles the mouseReleased event, transmits the MouseEvent to any underlying
	 * subwidgets, and updates all command outputs with the command hierarchy.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof Control) cmd = ((Control) o).mouseReleased(e);
		if(cmd != null) updateAllCommandOutputs(cmd);
	}
	
	/**
	 * Draws a border of the specified color and width around the specified widget (GObject)
	 * 
	 * @param widget the widget around which to draw the border
	 * @param color the color of the border
	 * @param borderWidth the width of the border
	 */
	protected void addBorder(GObject widget, Color color, double borderWidth)
	{
		GRect background = new GRect(widget.getWidth() + 2 * borderWidth, widget.getHeight() + 2 * borderWidth);
		background.setFilled(true);
		background.setFillColor(color);
		add(background, widget.getX() - borderWidth, widget.getY() - borderWidth);
		widget.sendToFront();
	}
	
	/**
	 * Returns the left x-coordinate of the bounding box surrounding all widgets on screen.
	 * 
	 * @return the left x-coordinate of the bounding box surrounding all widgets on screen
	 */
	public double getWidgetLeftX()
	{
		return x;
	}
	
	/**
	 * Returns the top y-coordinate of the bounding box surrounding all widgets on screen.
	 * 
	 * @return the top y-coordinate of the bounding box surrounding all widgets on screen
	 */
	public double getWidgetTopY()
	{
		return y;
	}
	
	/**
	 * Updates all command outputs with the specified command.
	 * 
	 * @param cmd the command to print to all command outputs
	 */
	private void updateAllCommandOutputs(String cmd)
	{
		for(String key : commandOutputs.keySet())
		{
			commandOutputs.get(key).update(cmd);
		}
	}
	
	/**
	 * Manually calculates the coordinates of the bounding box surrounding all widgets
	 * on screen by examining every single widget.
	 */
	private void calculateXY()
	{
		if(widgets.isEmpty())
		{
			x = Double.POSITIVE_INFINITY;
			y = Double.POSITIVE_INFINITY;
			return;
		}
		for(String key : widgets.keySet())
		{
			GObject obj = widgets.get(key);
			if(obj.getX() < x) x = obj.getX();
			if(obj.getY() < y) y = obj.getY();
		}
	}
	
	/**
	 * Updates the coordinates of the bounding box surrounding all widgets after
	 * the addition of a single widget.
	 * 
	 * @param obj the widget that was added
	 */
	private void checkXY(GObject obj)
	{
		if(obj.getX() < x) x = obj.getX();
		if(obj.getY() < y) y = obj.getY();
	}
	
	/**
	 * Designates a StringUpdatable as a command output, which will print out command hierarchies
	 * in real time.
	 * 
	 * @param key a handle that refers to the command output
	 * @param commandOutput the command output to add
	 */
	public final void addCommandOutput(String key, StringUpdatable commandOutput)
	{
		if(commandOutputs.containsKey(key)) return;
		commandOutputs.put(key, commandOutput);
	}
	
	/**
	 * Frees and stops a StringUpdatable from receiving command hierarchies.
	 * 
	 * @param key the key that is bound to the widget
	 */
	public final void removeCommandOutput(String key)
	{
		if(!commandOutputs.containsKey(key)) return;
		commandOutputs.remove(key);
	}
	
	/**
	 * Draws a background behind the entire dashboard based on the bounding box
	 * surrounding all widgets on screen. This method should be called after all
	 * widgets have been added, as the background does not automatically resize.
	 * 
	 * @param spacing the amount of space between each edge of the bounding box and the background
	 * @param baseColor the color of the background
	 */
	public final void addBackground(double spacing, Color baseColor)
	{
		if(background != null) remove(background);
		background = new GRect(union.getWidth() + 2 * spacing, union.getHeight() + 2 * spacing);
		background.setFilled(true);
		background.setFillColor(baseColor);
		add(background, getWidgetLeftX() - spacing, getWidgetTopY() - spacing);
		background.sendToBack();
	}
	
	/**
	 * Removes the background (if any) from behind the dashboard.
	 */
	public final void removeBackground()
	{
		if(background == null) return;
		remove(background);
	}
}
