package dashboard;
import java.awt.Color;

/**
 * Implements a controller for a single {@link Incrementable Incrementable}.
 * The functions of the constituent buttons are:
 * <p>INC - increment the Incrementable one level
 * <p>DEC - decrement the Incrementable one level
 * 
 * @author Mark Sabini
 *
 */
public final class SingleIncrementer extends ButtonGrid
{
	/**
	 * The Incrementable to be controlled by the SingleIncrementer
	 */
	private Incrementable inc;
	
	/**
	 * The button that increments the Incrementable
	 */
	private TouchButton incButton;
	
	/**
	 * The button that decrements the Incrementable
	 */
	private TouchButton decButton;
	
	/**
	 * Builder for the SingleIncrementer class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * The color of the SingleIncrementer's buttons, set to black by default
		 */
		private Color buttonColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a SingleIncrementer with the given dimensions.
		 * 
		 * @param width the width of the SingleIncrementer
		 * @param height the height of the SingleIncrementer
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of all the buttons in the SingleIncrementer.
		 * 
		 * @param buttonColor the color of the SingleIncrementer's buttons
		 * @return the current Builder
		 */
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		/**
		 * Creates a new SingleIncrementer with the Builder's parameters.
		 * 
		 * @return a new SingleIncrementer with the Builder's parameters
		 */
		public SingleIncrementer build()
		{
			return new SingleIncrementer(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	/**
	 * Creates a SingleIncrementer with the specified dimensions, spacing, base color, and button color.
	 * 
	 * @param width the width of the SingleIncrementer
	 * @param height the height of the SingleIncrementer
	 * @param spacing the spacing of the SingleIncrementer
	 * @param baseColor the base color of the SingleIncrementer
	 * @param buttonColor the button color of the SingleIncrementer
	 */
	protected SingleIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		incButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(inc != null) inc.increment();
			}
		});
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		decButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(inc != null) inc.decrement();
			}
		});
		addButton(decButton, 1, 0);
	}
	
	/**
	 * Sets the Incrementable controlled by the SingleIncrementer to the specified Incrementable.
	 * 
	 * @param inc the Incrementable to be controlled
	 */
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
}
