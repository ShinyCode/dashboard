package dashboard.readout;

/**
 * Defines the functionality of a class that can be incremented and decremented. 
 * The exact interpretation of incrementing and decrementing is left to the implementing subclass.
 * 
 * @author Mark Sabini
 *
 */
public interface Incrementable
{
	/**
	 * Increments the class.
	 */
	public void increment();
	
	/**
	 * Decrements the class.
	 */
	public void decrement();
}
