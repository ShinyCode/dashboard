
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
	
	public void issue(String instr)
	{
		
	}
	
	private void turnOn()
	{
		thr = new Thread(new Runnable()
		{
			public void run()
			{
				while(!Thread.interrupted())
				{	
					// Execute code
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
