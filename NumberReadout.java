public abstract class NumberReadout extends Readout implements Incrementable, NumberUpdatable
{
	protected int level;
	protected int numDivisions;
	protected double minValue;
	protected double maxValue;
	
	public static abstract class Builder<T extends Builder> extends Readout.Builder<Builder>
	{	
		protected int numDivisions = 0;
		protected double minValue = 0.0;
		protected double maxValue = 100.0;

		// If numDivisions is 0, operate in "continuous" mode.
		// Here, we set numDivisions to the bar height, but since we don't know
		// the spacing until we build(), we defer exact calculation until then.
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height);
			if(numDivisions < 0) throw new IllegalArgumentException();
			this.numDivisions = numDivisions;
		}
		
		public T withRange(double minValue, double maxValue)
		{
			if(minValue >= maxValue) throw new IllegalArgumentException();
			this.minValue = minValue;
			this.maxValue = maxValue;
			return (T)this;
		}
	}
	
	public void increment()
	{
		setLevel(level + 1);
	}
	
	public void decrement()
	{
		setLevel(level - 1);
	}
	
	public void update(double value)
	{
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
	
	public void setLevel(int level)
	{
		if(level < 0 || level > numDivisions) return;
		this.level = level;
		redrawAtLevel(level);
	}
	
	public abstract void redrawAtLevel(int level);
}
