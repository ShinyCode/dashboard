import java.awt.Color;
import java.awt.event.MouseEvent;

public class ToggleButton extends GenericButton
{
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(on)
		{
			on = false;
			rect.setColor(baseColor.darker());
		}
		else
		{
			on = true;
			rect.setColor(baseColor.brighter());
		}
	}
	
	public boolean isOn()
	{
		return on;
	}
	
	private boolean on = false;
	
}
