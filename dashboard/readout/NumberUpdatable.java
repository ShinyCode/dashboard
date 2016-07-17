package dashboard.readout;

/**
 * Defines the functionality of a class that can be updated via a double. The double should correspond
 * to a value of raw data, which the implementing class will then interpret.
 * 
 * @author Mark Sabini
 *
 */
public interface NumberUpdatable
{
	/**
	 * Updates the class with the specified double
	 * 
	 * @param value the raw value, interpreted by the implementing class
	 */
	public void update(double value);
}
