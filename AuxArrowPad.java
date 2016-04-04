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
		addButton(bnlButton, 1, 0);
		bnrButton = new TouchButton(width, height, buttonColor, "BNR");
		addButton(bnrButton, 1, 2);
	}
	
	public GenericButton getTouchButton(String instr) // Bad, since it exposes the internals.
	{
		if(instr.equals(fwdButton.getInstr())) return fwdButton;
		else if(instr.equals(revButton.getInstr())) return revButton;
		else if(instr.equals(bnlButton.getInstr())) return bnlButton;
		else if(instr.equals(bnrButton.getInstr())) return bnrButton;
		return null;
	}
	
	private TouchButton fwdButton, revButton, bnlButton, bnrButton;
}
