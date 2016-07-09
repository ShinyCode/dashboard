package dashboard;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class TouchButton extends Button
{
	public TouchButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	@Override
	public String mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
		runOnAction();
		return getInstr() + ".SET_ACTIVE_TRUE";
	}
	
	@Override
	public String mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor);
		runOffAction();
		return getInstr() + ".SET_ACTIVE_FALSE";
	}
}
