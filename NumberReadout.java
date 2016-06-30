import BarReadout.Builder;



public abstract class NumberReadout extends Readout implements Incrementable, NumberUpdatable
{
	protected int level;
	protected int numDivisions;
	protected double minValue;
	protected double maxValue;
	
	public abstract static class Builder extends Readout.Builder<Builder>
	{	
		private int numDivisions = 0;
		private double minValue = 0.0;
		private double maxValue = 100.0;
		private int orientation = VERTICAL;

		// If numDivisions is 0, operate in "continuous" mode.
		// Here, we set numDivisions to the bar height, but since we don't know
		// the spacing until we build(), we defer exact calculation until then.
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height);
			if(numDivisions < 0) throw new IllegalArgumentException();
			this.numDivisions = numDivisions;
		}
		
		public Builder withRange(double minValue, double maxValue)
		{
			if(minValue >= maxValue) throw new IllegalArgumentException();
			this.minValue = minValue;
			this.maxValue = maxValue;
			return this;
		}
		
		public Builder withOrientation(int orientation)
		{
			if(orientation == VERTICAL || orientation == HORIZONTAL) this.orientation = orientation;
			return this;
		}
		
		public BarReadout build()
		{
			return new BarReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, orientation);
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
