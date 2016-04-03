import java.awt.Color;

public class TouchButton extends GenericButton
{
	public TouchButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	public void pressButton()
	{
		rect.setColor(baseColor.brighter());
	}
	
	public void releaseButton()
	{
		rect.setColor(baseColor.darker());
	}
}
