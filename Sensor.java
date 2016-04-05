
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public abstract class Sensor
{
	public Sensor()
	{
		readouts = new ArrayList<Readout>();
	}
	
	public void setActive(boolean flag)
	{
		
	}
	
	List<Readout> readouts;
}
