package dashboard.readout;

import acm.graphics.GImage;

/**
 * Defines the functionality of a class that can be updated via a GImage.
 * 
 * @author Mark Sabini
 *
 */
public interface ImageUpdatable
{
	/**
	 * Updates the class with the specified GImage.
	 * 
	 * @param image the image with which to update the class
	 */
	public void update(GImage image);
}
