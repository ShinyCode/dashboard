import java.util.ArrayDeque;
import java.util.Queue;


public class Processor {

	public Processor(int updateDelay)
	{
		this.updateDelay = updateDelay;
		queue = new ArrayDeque<String>();
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
	
	// Issues a command to the processor, enqueueing it onto the processor's queue.
	public void issue(String instr)
	{
		queue.add(instr);
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
						// DO stuff
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
	
	private Thread thr;
	private boolean active = false;
	private Queue queue;
	private int updateDelay;
}
