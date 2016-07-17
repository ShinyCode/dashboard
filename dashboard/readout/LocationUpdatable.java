package dashboard;

import acm.graphics.GPoint;

/**
 * Defines the functionality of a class that can be updated via two GPoints. Normally,
 * the first GPoint is a position relative to a datum point, and the second represents
 * a vector from the origin which points in the desired orientation.
 * 
 * @author Mark Sabini
 *
 */
public interface LocationUpdatable
{
	/**
	 * Updates the class with the specified GPoints (position and bearing).
	 * 
	 * @param position the position relative to a datum point
	 * @param bearing the endpoint of a vector from the origin pointing in the desired orientation
	 */
	public void update(GPoint position, GPoint bearing);
}
