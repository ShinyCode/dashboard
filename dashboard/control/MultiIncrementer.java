package dashboard.control;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import dashboard.readout.Incrementable;

/**
 * Implements a controller for multiple {@link Incrementable Incrementables}.
 * The functions of the constituent buttons are:
 * <p>INC - increment the currently-selected Incrementable one level
 * <p>DEC - decrement the currently-selected Incrementable one level
 * <p>SEL - set the currently-selected Incrementable to the one after the current, wrapping around if necessary
 * 
 * @author Mark Sabini
 *
 */
public final class MultiIncrementer extends ButtonGrid
{
	/**
	 * The index of the currently-selected Incrementable in the list of Incrementables
	 */
	private int index = 0;
	
	/**
	 * The button that increments the currently-selected Incrementable
	 */
	private TouchButton incButton;
	
	/**
	 * The button that decrements the currently-selected Incrementable
	 */
	private TouchButton decButton;
	
	/**
	 * The button that selects the next Incrementable in the list
	 */
	private TouchButton selButton;
	
	/**
	 * The list of all the Incrementables controlled by the MultiIncrementer
	 */
	private List<Incrementable> incrementables;
	
	/**
	 * Builder for the MultiIncrementer class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * The color of the MultiIncrementer's buttons, set to black by default
		 */
		private Color buttonColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a MultiIncrementer with the given dimensions.
		 * 
		 * @param width the width of the MultiIncrementer
		 * @param height the height of the MultiIncrementer
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of all the buttons in the MultiIncrementer.
		 * 
		 * @param buttonColor the color of the MultiIncrementer's buttons
		 * @return the current Builder
		 */
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		/**
		 * Creates a new MultiIncrementer with the Builder's parameters.
		 * 
		 * @return a new MultiIncrementer with the Builder's parameters
		 */
		@Override
		public MultiIncrementer build()
		{
			return new MultiIncrementer(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	/**
	 * Creates a MultiIncrementer with the specified dimensions, spacing, base color, and button color.
	 * 
	 * @param width the width of the MultiIncrementer
	 * @param height the height of the MultiIncrementer
	 * @param spacing the spacing of the MultiIncrementer
	 * @param baseColor the base color of the MultiIncrementer
	 * @param buttonColor the button color of the MultiIncrementer
	 */
	protected MultiIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 3, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		incButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) incrementables.get(index).increment();
			}
		});
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		decButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) incrementables.get(index).decrement();
			}
		});
		addButton(decButton, 1, 0);
		selButton = new TouchButton(width, height, buttonColor, "SEL");
		selButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(!incrementables.isEmpty()) index = (index + 1) % incrementables.size();
			}
		});
		addButton(selButton, 2, 0);
		incrementables = new ArrayList<Incrementable>();
	}
	
	/**
	 * Adds the specified Incrementable to the list of Incrementables controlled by the MultiIncrementer.
	 * If the Incrementable passed in is null, no action is taken.
	 * 
	 * @param inc an Incrementable to be controlled by the MultiIncrementer
	 */
	public void addIncrementable(Incrementable inc)
	{
		if(inc != null) incrementables.add(inc);
	}
}
