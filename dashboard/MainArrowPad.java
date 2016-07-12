package dashboard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GObject;

/**
 * Implements a main arrow pad for controlling a vehicle. The main arrow pad can transmit and copy
 * its button presses to multiple {@link AuxArrowPad AuxArrowPads}.
 * The functions of the constituent buttons are:
 * <p>MIM - if active, transmits and copies button presses to the controlled AuxArrowPads
 * <p>FWD - move the vehicle forwards [cosmetic]
 * <p>REV - move the vehicle backwards [cosmetic]
 * <p>BNL - bank the vehicle to the left [cosmetic]
 * <p>BNR - bank the vehicle to the right [cosmetic]
 * <p>AILL - use the ailerons to roll the vehicle to the left [cosmetic]
 * <p>AILR - use the ailerons to roll the vehicle to the right [cosmetic]
 * <p>RUDL - turn the rudder to the left [cosmetic]
 * <p>RUDR - turn the rudder to the right [cosmetic]
 * 
 * @author Mark Sabini
 *
 */
public final class MainArrowPad extends ButtonGrid
{
	/**
	 * One of the cosmetic buttons used in the MainArrowPad
	 */
	private TouchButton aillButton, fwdButton, ailrButton, bnlButton, revButton, bnrButton, rudlButton, rudrButton;
	
	/**
	 * The button used to toggle control of the AuxArrowPads
	 */
	private ToggleButton mimButton;
	
	/**
	 * The list of the AuxArrowPads being controlled
	 */
	private List<AuxArrowPad> auxArrowPads;
	
	/**
	 * Builder for the MainArrowPad class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * The color of the MainArrowPad's buttons, set to black by default
		 */
		private Color buttonColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a MainArrowPad with the given dimensions.
		 * 
		 * @param width the width of the MainArrowPad
		 * @param height the height of the MainArrowPad
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of all the buttons in the MainArrowPad.
		 * 
		 * @param buttonColor the color of the MainArrowPad's buttons
		 * @return the current Builder
		 */
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		/**
		 * Creates a new MainArrowPad with the Builder's parameters.
		 * 
		 * @return a new MainArrowPad with the Builder's parameters
		 */
		public MainArrowPad build()
		{
			return new MainArrowPad(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	/**
	 * Creates a MainArrowPad with the specified dimensions, spacing, base color, and button color.
	 * 
	 * @param width the width of the MainArrowPad
	 * @param height the height of the MainArrowPad
	 * @param spacing the spacing of the MainArrowPad
	 * @param baseColor the base color of the MainArrowPad
	 * @param buttonColor the button color of the MainArrowPad
	 */
	protected MainArrowPad(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 3, 3, spacing, baseColor);
		aillButton = new TouchButton(width, height, buttonColor, "AILL");
		addButton(aillButton, 0, 0);
		fwdButton = new TouchButton(width, height, buttonColor, "FWD");
		addButton(fwdButton, 0, 1);
		ailrButton = new TouchButton(width, height, buttonColor, "AILR");
		addButton(ailrButton, 0, 2);
		bnlButton = new TouchButton(width, height, buttonColor, "BNL");
		addButton(bnlButton, 1, 0);
		revButton = new TouchButton(width, height, buttonColor, "REV");
		addButton(revButton, 1, 1);
		bnrButton = new TouchButton(width, height, buttonColor, "BNR");
		addButton(bnrButton, 1, 2);
		rudlButton = new TouchButton(width, height, buttonColor, "RUDL");
		addButton(rudlButton, 2, 0);
		mimButton = new ToggleButton(width, height, buttonColor, "MIM");
		addButton(mimButton, 2, 1);
		rudrButton = new TouchButton(width, height, buttonColor, "RUDR");
		addButton(rudrButton, 2, 2);
		auxArrowPads = new ArrayList<AuxArrowPad>();
	}
	
	/**
	 * Callback function that, if the mouse is pressed and MIM is active, transmits
	 * the events to the controlled AuxArrowPads.
	 */
	@Override
	public void onMousePressed(GObject o)
	{
		if(mimButton.isOn() && o != mimButton && o instanceof Button)
		{
			for(AuxArrowPad aap : auxArrowPads)
			{
				aap.setButtonState(((Button) o).getInstr(), true);
			}
		}
	}
	
	/**
	 * Callback function that, if the mouse is released and MIM is active, transmits
	 * the events to the controlled AuxArrowPads.
	 */
	@Override
	public void onMouseReleased(GObject o)
	{
		if(mimButton.isOn() && o != mimButton && o instanceof Button)
		{
			for(AuxArrowPad aap : auxArrowPads)
			{
				aap.setButtonState(((Button) o).getInstr(), false);
			}
		}
	}
	
	/**
	 * Adds the specified AuxArrowPad to the list of AuxArrowPads controlled by the MainArrowPad.
	 * If the AuxArrowPad passed in is null, no action is taken.
	 * 
	 * @param aap an AuxArrowPad to be controlled by the MainArrowPad
	 */
	public void addAuxArrowPad(AuxArrowPad aap)
	{
		if(aap != null) auxArrowPads.add(aap);
	}
}
