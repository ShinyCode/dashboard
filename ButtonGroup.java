
import java.awt.Color;
import java.util.List;

import acm.graphics.*;

public class ButtonGroup extends GCompound
{
	public ButtonGroup(double width, double height)
	{
		add(new TouchButton(100, 50, Color.RED, "RCL"), 10, 10);
		add(new TouchButton(100, 50, Color.RED, "RCL"), 10, 70);
	}
	
	private List<GenericButton> buttons;
}
