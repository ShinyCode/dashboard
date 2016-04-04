import java.awt.Color;


public class AuxArrowPad extends ButtonGrid
{
	public AuxArrowPad(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 2, 3, spacing, baseColor);
		fwdButton = new TouchButton(width, height, buttonColor, "FWD");
		addButton(fwdButton, 0, 1);
		revButton = new TouchButton(width, height, buttonColor, "REV");
		addButton(revButton, 1, 1);
		bnlButton = new TouchButton(width, height, buttonColor, "BNL");
		addButton(bnlButton, 0, 1);
		bnrButton = new TouchButton(width, height, buttonColor, "BNR");
		addButton(bnrButton, 0, 2);
	}
	
	private TouchButton fwdButton, revButton, bnlButton, bnrButton;
}
