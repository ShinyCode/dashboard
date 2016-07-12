package dashboard;

/**
 * Provides the base functionality for a data generator that creates data and transmits it
 * at regular intervals to a {@link Readout Readout}. This can be used to animate Readouts for a
 * cosmetic effect.
 * 
 * @author Mark Sabini
 *
 */
public abstract class Generator
{
	/**
	 * Whether the Generator is currently transmitting data to its Readouts
	 */
	private boolean active = false;
	
	/**
	 * The thread of execution in which the Generator updates its Readouts
	 */
	private Thread thr;
	
	/**
	 * The time interval between updates, measured in milliseconds
	 */
	private int interval;
	
	/**
	 * The default time interval between updates, if none is specified by the user
	 */
	private static final int DEFAULT_INTERVAL = 500;
	
	/**
	 * Creates a Generator with the default timing interval.
	 */
	public Generator()
	{
		this(DEFAULT_INTERVAL);
	}
	
	/**
	 * Creates a Generator with the timing interval specified by the user.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public Generator(int interval)
	{
		this.interval = interval;
	}
	
	/**
	 * Sets whether the Generator is active, i.e. transmitting data to its Readouts.
	 * 
	 * @param flag whether the Generator should be active
	 */
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
	
	/**
	 * Sets the time interval used by the Generator.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public void setInterval(int interval)
	{
		if(interval < 0) throw new IllegalArgumentException();
		this.interval = interval;
	}
	
	/**
	 * Turns on the Generator. Internally, the function starts a new thread that
	 * repeatedly calls {@link #generate() generate} until it is interrupted.
	 */
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
	
	/**
	 * Turns off the Generator. Internally, the function interrupts the generation thread.
	 */
	private void turnOff()
	{
		thr.interrupt();
	}
	
	/**
	 * Updates each Readout once with data of a certain form, dependent on the specific subclass.
	 */
	public abstract void generate();
}
