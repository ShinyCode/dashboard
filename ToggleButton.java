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
			return getPath() + ".SET_ACTIVE_FALSE";
		}
		else
		{
			on = true;
			rect.setColor(baseColor.brighter());
			return getPAth() + ".SET_ACTIVE_TRUE";
		}
	}
	
	public boolean isOn()
	{
		return on;
	}
	
	private boolean on = false;
	
}
