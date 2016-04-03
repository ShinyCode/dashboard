import java.awt.Color;

public class ToggleButton extends GenericButton
{
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public void toggleButton()
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
	
	private boolean on = false;
	
}
