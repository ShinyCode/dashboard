
public interface NumberUpdatable
{
	public void update(double value); // The raw data, scaled according to minValue and maxValue
	public void setLevel(int level); // The internal discretized level
}
