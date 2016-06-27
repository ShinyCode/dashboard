
import acm.graphics.*;

import java.awt.event.*;

/**
 * Provides the base functionality for all user-interactable widgets that require
 * mouse input.
 * 
 * @author Mark Sabini
 *
 */
public abstract class MouseWidget extends GCompound
{	
	/**
	 * Called by the client to simulate the mouse being pressed, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMousePressed}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		onMousePressed(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse being released, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseReleased}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		onMouseReleased(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse exiting the window, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseExited}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseExited(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseExited(e);
		onMouseExited(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse being clicked, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseClicked}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseClicked(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseClicked(e);
		onMouseClicked(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse entering the window, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseEntered}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseEntered(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseEntered(e);
		onMouseEntered(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse being dragged, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseDragged}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseDragged(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseDragged(e);
		onMouseDragged(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Called by the client to simulate the mouse being moved, and returns
	 * a string representing the end action being executed and the associated
	 * call hierarchy.
	 * <p>
	 * This method serves as a wrapper for {@link onMouseMoved}, which
	 * is overridden by subclasses of {@link MouseWidget} as an action callback.
	 * 
	 * @param e a {@link MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	public String mouseMoved(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		String cmd = null;
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseMoved(e);
		onMouseMoved(o);
		if(cmd == null) return null;
		return name + "." + cmd;
	}
	
	/**
	 * Sets the name of the {@link MouseWidget} that will be displayed
	 * in subsequent call hierarchies.
	 * 
	 * @param name a label for the MouseWidget
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the name of the {@link MouseWidget} that will be displayed
	 * in subsequent call hierarchies.
	 * 
	 * @return the name of the MouseWidget
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Called by {@link mousePressed} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mousePressed.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMousePressed(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseReleased} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseReleased.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseReleased(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseExited} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseExited.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseExited(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseClicked} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseClicked.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseClicked(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseEntered} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseEntered.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseEntered(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseDragged} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseDragged.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseDragged(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * Called by {@link mouseMoved} to execute code specific to this {@link MouseWidget}.
	 * The client should override this function to implement a callback for the specific MouseWidget.
	 * As the method exists within a wrapper function, the client should not explicitly call
	 * subwidgets' versions of mouseMoved.
	 * 
	 * @param o the exact element within the MouseWidget that the client interacted with
	 */
	public void onMouseMoved(GObject o)
	{
		// Do nothing
	}
	
	/**
	 * the name of the that will be displayed
	 * in subsequent call hierarchies
	 */
	private String name = "";
}
