package dashboard;
import java.awt.Color;

public final class AuxArrowPad extends ButtonGrid
{
	private TouchButton fwdButton, revButton, bnlButton, bnrButton;
	
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		private Color buttonColor = Color.BLACK;
		
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		public AuxArrowPad build()
		{
			return new AuxArrowPad(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	protected AuxArrowPad(double width, double height, double spacing, Color baseColor, Color buttonColor)
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
	
	public boolean setButtonState(String instr, boolean turnOn)
	{
		Button gb = null;
		if(instr.equals(fwdButton.getInstr())) gb = fwdButton;
		else if(instr.equals(revButton.getInstr())) gb = revButton;
		else if(instr.equals(bnlButton.getInstr())) gb = bnlButton;
		else if(instr.equals(bnrButton.getInstr())) gb = bnrButton;
		if(gb == null) return false;
		if(turnOn) gb.mousePressed(null); // Also bad? But it never tries to dereference the MouseEvent.
		else gb.mouseReleased(null);
		return true;
	}
}
