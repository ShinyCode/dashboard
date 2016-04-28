
public class Processor {

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
	
	private void turnOn()
	{
		thr = new Thread(new Runnable()
		{
			public void run()
			{
				while(!Thread.interrupted())
				{	
					
				}
			}
		});
		thr.start();
	}
	
	private void turnOff()
	{
		thr.interrupt();
	}
	
	private Thread thr;
	private boolean active = false;
}
