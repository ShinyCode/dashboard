package dashboard;

public abstract class Generator
{
	private boolean active = false;
	private Thread thr;
	private int interval;
	
	private static final int DEFAULT_INTERVAL = 500;
	
	public Generator()
	{
		this(DEFAULT_INTERVAL);
	}
	
	public Generator(int interval)
	{
		this.interval = interval;
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
	
	public void setInterval(int interval)
	{
		if(interval < 0) throw new IllegalArgumentException();
		this.interval = interval;
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
						generate();
						Thread.sleep(interval);
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
	
	public abstract void generate();
}
