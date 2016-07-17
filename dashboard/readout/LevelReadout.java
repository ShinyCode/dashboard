package dashboard.readout;


/**
 * Provides the base functionality for a Readout that has discretized levels. A LevelReadout
 * can also be updated with raw data, which is normalized with respect to a user-specified minimum
 * and maximum and then translated to a level.
 * 
 * @author Mark Sabini
 *
 */
public abstract class LevelReadout extends Readout implements Incrementable, NumberUpdatable, LevelUpdatable
{
	/**
	 * The current level of the LevelReadout
	 */
	protected int level;
	
	/**
	 * The total number of levels into which the LevelReadout is divided
	 */
	protected int numDivisions;
	
	/**
	 * The real value to which the lowest level corresponds
	 */
	protected double minValue;
	
	/**
	 * The real value to which the highest level corresponds
	 */
	protected double maxValue;
	
	/**
	 * Builder for the LevelReadout class, to be extended by all subclasses of LevelReadout.
	 * 
	 * @author Mark Sabini
	 *
	 * @param <T> dummy parameter to enable the subclasses to return their exact type
	 */
	@SuppressWarnings("rawtypes")
	protected static abstract class Builder<T extends Builder> extends Readout.Builder<T>
	{	
		/**
		 * The total number of levels into which the LevelReadout is divided, set to 0 by default.
		 * 0 acts as a sentinel value, signaling the LevelReadout to operate in continuous mode.
		 */
		protected int numDivisions = 0;
		
		/**
		 * The real value to which the lowest level corresponds, set to 0.0 by default
		 */
		protected double minValue = 0.0;
		
		/**
		 * The real value to which the highest level corresponds, set to 100.0 by default
		 */
		protected double maxValue = 100.0;

		/**
		 * Creates a Builder specifying a LevelReadout with the given dimensions and number of divisions.
		 * If the number of divisions is set to 0, the LevelReadout will operate in continuous mode,
		 * calculating the number of divisions based on the size of the LevelReadout itself. This allows
		 * each level to roughly correspond to a layer of pixels.
		 * 
		 * @param width the width of the LevelReadout
		 * @param height the height of the LevelReadout
		 * @param numDivisions the total number of levels into which the LevelReadout should be divided
		 * @throws IllegalArgumentException if the number of divisions is negative
		 */
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height);
			if(numDivisions < 0) throw new IllegalArgumentException();
			// In continuous mode here, we set numDivisions to the bar height, but since we don't know
			// the spacing until we build(), we defer exact calculation until then.
			this.numDivisions = numDivisions;
		}
		
		/**
		 * Specifies the minimum and maximum values of the LevelReadout.
		 * 
		 * @param minValue the real value to which the lowest level corresponds
		 * @param maxValue the real value to which the highest level corresponds
		 * @throws IllegalArgumentException if the maximum value is not greater than the minimum value
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withRange(double minValue, double maxValue)
		{
			if(minValue >= maxValue) throw new IllegalArgumentException();
			this.minValue = minValue;
			this.maxValue = maxValue;
			return (T)this;
		}
	}
	
	/**
	 * Increments the LevelReadout, increasing its level by one.
	 */
	@Override
	public void increment()
	{
		setLevel(level + 1);
	}
	
	/**
	 * Decrements the LevelReadout, decreasing its levels by one.
	 */
	@Override
	public void decrement()
	{
		setLevel(level - 1);
	}
	
	/**
	 * Updates the LevelReadout with the specified value, which is then translated into a level.
	 * If the value is too small or large, the LevelReadout will be set to the lowest or highest level,
	 * respectively.
	 */
	@Override
	public void update(double value)
	{
		if(isFrozen()) return;
		if(value > maxValue)
		{
			setLevel(numDivisions);
			return;
		}
		if(value < minValue)
		{
			setLevel(0);
			return;
		}
		double range = maxValue - minValue;
		// TODO: Check if want to make long
		setLevel((int)Math.round((value - minValue) * numDivisions / range));
	}
	
	/**
	 * Sets the LevelReadout to the specified level.
	 * If the level is too low or too high, the LevelReadout will be set
	 * to the lowest or highest level, respectively.
	 */
	@Override
	public void setLevel(int level)
	{
		if(level < 0) level = 0;
		if(level > numDivisions) level = numDivisions;
		this.level = level;
		redrawAtLevel(level);
	}
	
	@Override
	public int getNumDivisions()
	{
		return numDivisions;
	}
	
	/**
	 * Draws the LevelReadout at the specified level, which is guaranteed to be
	 * at least 0 and at most the number of divisions.
	 * 
	 * @param level the level at which to set the LevelReadout
	 */
	protected abstract void redrawAtLevel(int level);
}
