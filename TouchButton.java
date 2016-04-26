import java.awt.Color;
import java.awt.event.MouseEvent;

public class TouchButton extends Button
{
	public TouchButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public String mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
		return getInstr() + ".SET_ACTIVE_TRUE";
	}
	
	public String mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor);
		return getInstr() + ".SET_ACTIVE_FALSE";
	}
}
