
import acm.graphics.*;

import java.awt.event.*;

public abstract class MouseWidget extends GCompound
{	
	public String mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMousePressed(o);
		return cmd;
	}
	
	public String mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseReleased(o);
		return cmd;
	}
	
	public String mouseExited(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseExited(o);
		return cmd;
	}
	
	public String mouseClicked(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseClicked(o);
		return cmd;
	}
	
	public String mouseEntered(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseEntered(o);
		return cmd;
	}
	
	public String mouseDragged(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseDragged(o);
		return cmd;
	}
	
	public String mouseMoved(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMouseMoved(o);
		return cmd;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void onMousePressed(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseReleased(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseExited(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseClicked(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseEntered(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseDragged(GObject o)
	{
		// Do nothing
	}
	
	public void onMouseMoved(GObject o)
	{
		// Do nothing
	}
	
	private String name = "";
}
