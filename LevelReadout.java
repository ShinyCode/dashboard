import java.awt.Color;
import acm.graphics.*;


public class LevelReadout extends GCompound implements Incrementable
{
	public LevelReadout(double width, double height, double spacing, Color baseColor, Color backColor, Color barColor)
	{
		
	}
	
	public void increment()
	{
		
	}
	
	public void decrement()
	{
		
	}
	
	public void setLevel(int level)
	{
		
	}
	
	public void setDiscrete(boolean flag)
	{
		isDiscrete = flag;
	}
	
	int level;
	int maxLevel;
	int interval;
	boolean isDiscrete = false;
}
