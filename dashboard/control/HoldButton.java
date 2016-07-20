package dashboard.control;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * Implements a Button that if held, repeatedly executes its onAction until it is released. Here,
 * only the onAction is repeated - the offAction should serve as a cleanup function if necessary.
 * 
 * @author Mark Sabini
 *
 */
public class HoldButton extends Button
{
	/**
	 * The number of milliseconds between executions of onAction
	 */
	private int interval;
	
	/**
	 * The default interval to use if not specified by the user
	 */
	private static final int DEFAULT_INTERVAL = 50;
	
	/**
	 * Creates a HoldButton with the specified dimensions, color, and label.
	 * 
	 * @param width the HoldButton's width in pixels
	 * @param height the HoldButton's height in pixels
	 * @param baseColor the HoldButton's color
	 * @param instr the HoldButton's label, as well as its name in returned call hierarchies
	 */
	public HoldButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
		interval = DEFAULT_INTERVAL;
	}
	
	/**
	 * Repeatedly executes onAction with the frequency set by {@link #setInterval(int) setInterval} until
	 * the button is released.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	@Override
	public String mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
		repeatOnAction(interval);
		return getInstr() + ".ON";
	}
	
	/**
	 * Executes offAction exactly once. For this button, offAction can serve as a cleanup
	 * function that is run after the button is released.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	@Override
	public String mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor);
		runOffAction();
		return getInstr() + ".OFF";
	}
	
	/**
	 * Sets the time in milliseconds between executions of onAction to the specified interval
	 * 
	 * @param interval the number of milliseconds between executions of onAction
	 * @throws IllegalArgumentException if the position and bearing are not both null or both non-null
	 */
	public void setInterval(int interval)
	{
		if(interval < 0) throw new IllegalArgumentException();
		this.interval = interval;
	}
}
