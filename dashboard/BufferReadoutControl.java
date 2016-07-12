package dashboard;
import java.awt.Color;


/**
 * Implements a controller for the {@link BufferReadout BufferReadout} class.
 * The functions of the constituent buttons are:
 * <p>CLS - clear the BufferReadout
 * <p>FRZ - freeze the BufferReadout, preventing updates from occurring
 * <p>INC - increment the BufferReadout, scrolling up the buffer
 * <p>DEC - decrement the BufferReadout, scrolling down the buffer
 * 
 * @author Mark Sabini
 *
 */
public final class BufferReadoutControl extends ButtonGrid
{
	/**
	 * The button used to clear the BufferReadout
	 */
	private TouchButton clsButton;
	
	/**
	 * The button used to freeze the BufferReadout
	 */
	private ToggleButton frzButton;
	
	/**
	 * The button used to increment the BufferReadout (scroll up)
	 */
	private TouchButton incButton;
	
	/**
	 * The button used to decrement the BufferReadout (scroll down)
	 */
	private TouchButton decButton;
	
	/**
	 * The BufferReadout controlled by the BufferReadoutControl
	 */
	private BufferReadout br;
	
	/**
	 * Builder for the BufferReadoutControl class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * The color of the BufferReadoutControl's buttons, set to black by default
		 */
		private Color buttonColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a BufferReadoutControl with the given dimensions.
		 * 
		 * @param width the width of the BufferReadoutControl
		 * @param height the height of the BufferReadoutControl
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of all the buttons in the BufferReadoutControl.
		 * 
		 * @param buttonColor the color of the BufferReadoutControl's buttons
		 * @return the current Builder
		 */
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		/**
		 * Creates a new BufferReadoutControl with the Builder's parameters.
		 * 
		 * @return a new BufferReadoutControl with the Builder's parameters
		 */
		@Override
		public BufferReadoutControl build()
		{
			return new BufferReadoutControl(width, height, spacing, baseColor, buttonColor);
		}		
	}
	
	/**
	 * Creates a BufferReadoutControl with the specified dimensions, spacing, base color, and button color.
	 * 
	 * @param width the width of the BufferReadoutControl
	 * @param height the height of the BufferReadoutControl
	 * @param spacing the spacing of the BufferReadoutControl
	 * @param baseColor the base color of the BufferReadoutControl
	 * @param buttonColor the button color of the BufferReadoutControl
	 */
	protected BufferReadoutControl(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 4, 1, spacing, baseColor);
		clsButton = new TouchButton(width, height, buttonColor, "CLS");
		clsButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(br != null) br.clear();
			}
		});
		addButton(clsButton, 0, 0);
		frzButton = new ToggleButton(width, height, buttonColor, "FRZ");
		frzButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(br != null) br.setFrozen(true);
			}
		});
		frzButton.setOffAction(new Runnable()
		{
			public void run()
			{
				if(br != null) br.setFrozen(false);
			}
		});
		addButton(frzButton, 1, 0);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		incButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(br != null) br.increment();
			}
		});
		addButton(incButton, 2, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		decButton.setOnAction(new Runnable()
		{
			public void run()
			{
				if(br != null) br.decrement();
			}
		});
		addButton(decButton, 3, 0);
	}
	
	/**
	 * Sets the BufferReadout controlled by the BufferReadoutControl to the specified BufferReadout.
	 * 
	 * @param br the BufferReadout to be controlled
	 */
	public void addBufferReadout(BufferReadout br)
	{
		this.br = br;
	}
}
