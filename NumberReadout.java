

public abstract class NumberReadout extends Readout implements Incrementable, NumberUpdatable
{
	protected int level;
	protected int numDivisions;
	protected double minValue;
	protected double maxValue;
	
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
