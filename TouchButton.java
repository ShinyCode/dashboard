import java.awt.Color;
import java.awt.event.MouseEvent;

public class TouchButton extends Button
{
	public TouchButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public void mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
	}
	
	public void mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor.darker());
	}
}
