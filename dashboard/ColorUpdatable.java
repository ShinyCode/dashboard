package dashboard;
import java.awt.Color;

/**
 * Defines the functionality of a class that can be updated via a Color.
 * 
 * @author Mark Sabini
 *
 */
public interface ColorUpdatable
{
	/**
	 * Updates the class with the specified Color.
	 * 
	 * @param color the color with which to update the class
	 */
	public void update(Color color);
}
