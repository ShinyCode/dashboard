package dashboard.readout;

/**
 * Defines the functionality of a class that can be set to various discretized levels.
 * 
 * @author Mark Sabini
 *
 */
public interface LevelUpdatable {
	/**
	 * Sets the class to the given level.
	 * @param level the level at which to set the class
	 */
	public void setLevel(int level); // The internal discretized level
	
	/**
	 * Returns the number of divisions, i.e. the total number of levels that the class has.
	 * @return the total number of levels that the class has
	 */
	public int getNumDivisions(); // Gets the number of divisions within
}
