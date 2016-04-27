
import acm.graphics.*;
import java.awt.event.*;

public abstract class MouseWidget extends GCompound
{	
	public final String mousePressed(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseReleased(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseExited(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseClicked(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseEntered(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseDragged(MouseEvent e)
	{
		return null;
	}
	
	public final String mouseMoved(MouseEvent e)
	{
		return null;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	private String name = "";
}
