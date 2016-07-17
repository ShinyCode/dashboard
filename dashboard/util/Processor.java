package dashboard;
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
		if(active) queue.add(instr);
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
						exec();
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
	
	// Executes the instruction at the top of the queue
	private void exec()
	{
		if(queue.isEmpty()) return;
		// In the future, parse this. But for now, we'll print it out.
		System.out.println(queue.remove());
	}
	
	private Thread thr;
	private boolean active = false;
	private Queue<String> queue;
	private int updateDelay;
}
