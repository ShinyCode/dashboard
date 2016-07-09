package dashboard;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class ToggleButton extends Button
{
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	@Override
	public String mousePressed(MouseEvent e)
	{
		if(on)
		{
			on = false;
			rect.setColor(baseColor);
			runOffAction();
			return getInstr() + ".SET_ACTIVE_FALSE";
		}
		else
		{
			on = true;
			rect.setColor(baseColor.brighter());
			runOnAction();
			return getInstr() + ".SET_ACTIVE_TRUE";
		}
	}
	
	@Override
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
