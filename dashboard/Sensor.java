
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public abstract class Sensor
{
	public Sensor(int updateDelay)
	{
		readouts = new ArrayList<Updatable>();
		this.updateDelay = updateDelay;
	}
	
	public void setActive(boolean flag)
	{
		if(flag && !active)
		{
			active = true;
			turnOn();
		}
		else if(!flag && active)
		{
			active = false;
			turnOff();
		}
	}
	
	public void addReadout(Updatable r)
	{
		readouts.add(r);
	}
	
	public void removeReadout(Updatable r)
	{
		readouts.remove(r);
	}
	
	private void turnOn()
	{
		thr = new Thread(new Runnable()
		{
			public void run()
			{
				while(!Thread.interrupted())
				{	
					try
					{
						updateReadouts(getReading());
						Thread.sleep(updateDelay);
					}
					catch(InterruptedException e)
					{
						return;
					}
					
				}
			}
		});
		thr.start();
	}
	
	private void turnOff()
	{
		thr.interrupt();
	}
	
	private void updateReadouts(String msg)
	{
		for(Updatable r : readouts)
		{
			r.update(msg);
		}
	}
	
	public abstract String getReading();
	
	protected List<Updatable> readouts;
	private boolean active = false;
	private Thread thr;
	private int updateDelay;
}