import java.awt.Color;
import java.util.ArrayList;


public class InstrReadoutControl extends ButtonGrid
{
	public InstrReadoutControl(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 4, 1, spacing, baseColor);
		clsButton = new TouchButton(width, height, buttonColor, "CLS");
		add(clsButton, 0, 0);
		frzButton = new ToggleButton(width, height, buttonColor, "FRZ");
		add(frzButton, 1, 0);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		add(incButton, 2, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		add(decButton, 3, 0);
	}
	
	private TouchButton incButton, decButton, clsButton;
	private ToggleButton frzButton;
}
