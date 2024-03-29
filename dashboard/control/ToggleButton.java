package dashboard.control;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * Implements a Button that behaves like a switch or flip-flop. Here, onAction and offAction are executed
 * during mousePressed, rather than mouseReleased.
 * 
 * @author Mark Sabini
 *
 */
public class ToggleButton extends Button
{
	/**
	 * A boolean representing whether the ToggleButton is on
	 */
	private boolean on = false;
	
	/**
	 * Creates a ToggleButton with the specified dimensions, color, and label.
	 * 
	 * @param width the ToggleButton's width in pixels
	 * @param height the ToggleButton's height in pixels
	 * @param baseColor the ToggleButton's color
	 * @param instr the ToggleButton's label, as well as its name in returned call hierarchies
	 */
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	/**
	 * Toggles the ToggleButton's state in response to the button being pressed.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	@Override
	public String mousePressed(MouseEvent e)
	{
		if(on)
		{
			on = false;
			rect.setColor(baseColor);
			runOffAction();
			return getInstr() + ".OFF";
		}
		else
		{
			on = true;
			rect.setColor(baseColor.brighter());
			runOnAction();
			return getInstr() + ".ON";
		}
	}
	
	/**
	 * Returns null to prevent a call hierarchy from being created for the event.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return a null String
	 */
	@Override
	public String mouseReleased(MouseEvent e)
	{
		return null;
	}
	
	/**
	 * Returns whether the ToggleButton is on.
	 * 
	 * @return a boolean representing the state of the ToggleButton
	 */
	public boolean isOn()
	{
		return on;
	}
	
	/**
	 * Sets whether the ToggleButton is currently on. If one attempts to turn on
	 * an already-on ToggleButton or turn off an already-off ToggleButton,
	 * no action is taken.
	 * 
	 * @param on whether the ToggleButton should be on.
	 */
	public void setOn(boolean on)
	{
		if(this.on == on) return;
		this.on = on;
		if(on)
		{
			rect.setColor(baseColor.brighter());
			runOnAction();
		}
		else
		{
			rect.setColor(baseColor);
			runOffAction();
		}
	}
}
