
import acm.graphics.*;

import java.awt.event.*;

/**
 * Provides the base functionality for all user-interactable widgets.
 * 
 * @author Mark Sabini
 *
 */
public abstract class MouseWidget extends GCompound
{	
	public final String mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMousePressed(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		onMouseReleased(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseExited(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseExited(e);
		onMouseExited(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseClicked(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseClicked(e);
		onMouseClicked(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseEntered(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseEntered(e);
		onMouseEntered(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseDragged(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseDragged(e);
		onMouseDragged(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	public String mouseMoved(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseMoved(e);
		onMouseMoved(o);
		if(cmd == null) return null;
		return name + "." + cmd;
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
