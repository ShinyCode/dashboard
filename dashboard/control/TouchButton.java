package dashboard.control;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * Implements a Button that executes its onAction exactly once when pressed, and its offAction exactly
 * once when released.
 * 
 * @author Mark Sabini
 *
 */
public class TouchButton extends Button
{
	/**
	 * Creates a TouchButton with the specified dimensions, color, and label.
	 * 
	 * @param width the TouchButton's width in pixels
	 * @param height the TouchButton's height in pixels
	 * @param baseColor the TouchButton's color
	 * @param instr the TouchButton's label, as well as its name in returned call hierarchies
	 */
	public TouchButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
	}
	
	/**
	 * Executes onAction exactly once.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	@Override
	public String mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
		runOnAction();
		return getInstr() + ".SET_ACTIVE_TRUE";
	}
	
	/**
	 * Executes offAction exactly once.
	 * 
	 * @param e a {@link MouseEvent MouseEvent} that represents the associated mouse action
	 * @return the end action being executed and the associated call hierarchy
	 */
	@Override
	public String mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor);
		runOffAction();
		return getInstr() + ".SET_ACTIVE_FALSE";
	}
}
