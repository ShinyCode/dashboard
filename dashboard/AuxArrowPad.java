package dashboard;
import java.awt.Color;

/**
 * Implements an auxiliary arrow pad for controlling a vehicle.
 * The functions of the constituent buttons are:
 * <p>FWD - move the vehicle forwards [cosmetic]
 * <p>REV - move the vehicle backwards [cosmetic]
 * <p>BNL - bank the vehicle to the left [cosmetic]
 * <p>BNR - bank the vehicle to the right [cosmetic]
 * 
 * @author Mark Sabini
 *
 */
public final class AuxArrowPad extends ButtonGrid
{
	/**
	 * One of the cosmetic buttons used in the AuxArrowPad
	 */
	private TouchButton fwdButton, revButton, bnlButton, bnrButton;
	
	/**
	 * Builder for the AuxArrowPad class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * The color of the AuxArrowPad's buttons, set to black by default
		 */
		private Color buttonColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying an AuxArrowPad with the given dimensions.
		 * 
		 * @param width the width of the AuxArrowPad
		 * @param height the height of the AuxArrowPad
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of all the buttons in the AuxArrowPad.
		 * 
		 * @param buttonColor the color of the AuxArrowPad's buttons
		 * @return the current Builder
		 */
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		/**
		 * Creates a new AuxArrowPad with the Builder's parameters.
		 * 
		 * @return a new AuxArrowPad with the Builder's parameters
		 */
		public AuxArrowPad build()
		{
			return new AuxArrowPad(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	/**
	 * Creates a AuxArrowPad with the specified dimensions, spacing, base color, and button color.
	 * 
	 * @param width the width of the AuxArrowPad
	 * @param height the height of the AuxArrowPad
	 * @param spacing the spacing of the AuxArrowPad
	 * @param baseColor the base color of the AuxArrowPad
	 * @param buttonColor the button color of the AuxArrowPad
	 */
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
	
	/**
	 * Sets the state of a button in the AuxArrowPad. This is used by other widgets such as the 
	 * {@link MainArrowPad} to control the AuxArrowPad.
	 * 
	 * @param instr the label/instruction of the button to control
	 * @param turnOn true if the button should be pressed, false if the button should be released
	 * @return whether both the button exists and the operation was successful
	 */
	public boolean setButtonState(String instr, boolean turnOn)
	{
		Button gb = null;
		if(instr.equals(fwdButton.getInstr())) gb = fwdButton;
		else if(instr.equals(revButton.getInstr())) gb = revButton;
		else if(instr.equals(bnlButton.getInstr())) gb = bnlButton;
		else if(instr.equals(bnrButton.getInstr())) gb = bnrButton;
		if(gb == null) return false;
		// Simulate the mouse interactions with the AuxArrowPad's buttons
		if(turnOn) gb.mousePressed(null);
		else gb.mouseReleased(null);
		return true;
	}
}
