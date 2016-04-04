import java.awt.Color;
import acm.graphics.*;


public class LevelReadout extends GCompound implements Incrementable
{
	public LevelReadout(double width, double height, double spacing, int numDivisions, Color baseColor, Color backColor, Color barColor)
	{
		
	}
	
	public void increment()
	{
		
	}
	
	public void decrement()
	{
		
	}
	
	public boolean setLevel(int level)
	{
		if(level < 0 || level >= numDivisions) return false;
		this.level = level;
		return true;
	}
	
	int level;
	int numDivisions;
}
