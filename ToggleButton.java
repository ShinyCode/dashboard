import java.awt.Color;
import java.awt.event.MouseEvent;

public class ToggleButton extends Button
{
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public String mousePressed(MouseEvent e)
	{
		if(on)
		{
			on = false;
			rect.setColor(baseColor);
			return getInstr() + ".SET_ACTIVE_FALSE";
		}
		else
		{
			on = true;
			rect.setColor(baseColor.brighter());
			return getInstr() + ".SET_ACTIVE_TRUE";
		}
	}
	
	public String mouseReleased(MouseEvent e)
	{
		return null;
	}
	
	public boolean isOn()
	{
		return on;
	}
	
	private boolean on = false;
	
}
