package dashboard.control;

import acm.graphics.*;

import java.awt.Color;

/**
 * Provides the base functionality for a rectangular button with which the user can interact.
 * The exact behavior of the button depends on both the specific subclass and the {@link Runnable Runnables}
 * provided by the user.
 * 
 * @author Mark Sabini
 *
 */
public abstract class Button extends Control
{

	/**
	 * The Button's colored base
	 */
	protected GRect rect;
	
	/**
	 * The Button's label
	 */
	protected GLabel label;
	
	/**
	 * The color of the Button's base
	 */
	protected Color baseColor;
	
	/**
	 * A Runnable representing code to be run when the Button is depressed
	 */
	private Runnable onAction;
	
	/**
	 * A Runnable representing code to be run when the Button is released
	 */
	private Runnable offAction;
	
	/**
	 * The thread of execution for the Runnables
	 */
	private Thread thread;
	
	/**
	 * Draws the Button's base and text, the latter of which is centered within the base.
	 * 
	 * @param width the Button's width in pixels
	 * @param height the Button's height in pixels
	 * @param baseColor the Button's color
	 * @param instr the Button's label, as well as its name in returned call hierarchies
	 */
	public Button(double width, double height, Color baseColor, String instr)
	{
		rect = new GRect(0, 0, width, height);
		rect.setFilled(true);
		setBaseColor(baseColor);
		label = new GLabel("");
		label.setFont("Consolas-*-16");
		setInstr(instr);
		add(rect);
		add(label);
	}
	
	/**
	 * Sets the Button's color to the specified Color.
	 * 
	 * @param baseColor the new color of the Button's rectangular base
	 */
	public void setBaseColor(Color baseColor)
	{
		this.baseColor = baseColor;
		rect.setColor(baseColor);
	}
	
	/**
	 * Sets the Button's label to the specified String.
	 * 
	 * @param instr the Button's label, as well as its name in returned call hierarchies
	 */
	public void setInstr(String instr)
	{
		label.setLocation(rect.getWidth() / 2, rect.getHeight() / 2);
		label.setLabel(instr.toUpperCase());
		label.move(-label.getWidth() / 2, label.getAscent() / 2);
	}
	
	/**
	 * Returns the Button's color.
	 * 
	 * @return the Button's color
	 */
	public Color getBaseColor()
	{
		return baseColor;
	}
	
	/**
	 * Returns the Button's label.
	 * 
	 * @return the Button's label
	 */
	public String getInstr()
	{
		return label.getLabel();
	}
	
	/**
	 * Resizes the Button to the specified dimensions. This is used in adaptive widgets like {@link ButtonGrid ButtonGrid} to 
	 * automatically resize the constituent buttons.
	 * 
	 * @param width the Button's new width
	 * @param height the Button's new height
	 */
	public void resize(double width, double height)
	{
		rect.setSize(width, height);
		setInstr(label.getLabel());
	}
	
	/**
	 * Sets the onAction of the Button to use the specified Runnable, to be run when the
	 * Button is depressed. When exactly onAction is executed depends on the specific subclass of Button.
	 * 
	 * @param onAction a Runnable specifying code to be executed when the Button is depressed
	 */
	public void setOnAction(Runnable onAction)
	{
		this.onAction = onAction;
	}
	
	/**
	 * Sets the offAction of the Button to use the specified Runnable, to be run when the
	 * Button is released. When exactly offAction is executed depends on the specific subclass of Button.
	 * 
	 * @param offAction a Runnable specifying code to be executed when the Button is released
	 */
	public void setOffAction(Runnable offAction)
	{
		this.offAction = offAction;
	}
	
	/**
	 * Interrupts any other action threads and executes the Runnable associated with onAction exactly once.
	 */
	protected void runOnAction()
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(onAction == null) return;
		thread = new Thread(onAction);
		thread.start();
	}
	
	/**
	 * Interrupts any other action threads and executes the Runnable associated with offAction exactly once.
	 */
	protected void runOffAction()
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(offAction == null) return;
		thread = new Thread(offAction);
		thread.start();
	}
	
	/**
	 * Interrupts any other action threads and repeatedly executes the Runnable associated with
	 * onAction until the thread is interrupted.
	 * @param interval the number of milliseconds between executions of onAction
	 */
	protected void repeatOnAction(int interval)
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(onAction == null) return;
		thread = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while(true)
					{
						onAction.run();
						Thread.sleep(interval);
					}
				}
				catch(InterruptedException ie)
				{
					return;
				}
			}
		});
		thread.start();
	}
	
	/**
	 * Interrupts any other action threads and repeatedly executes the Runnable associated with
	 * offAction until the thread is interrupted.
	 * @param interval the number of milliseconds between executions of offAction
	 */
	protected void repeatOffAction(int interval)
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(offAction == null) return;
		thread = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while(true)
					{
						offAction.run();
						Thread.sleep(interval);
					}
				}
				catch(InterruptedException ie)
				{
					return;
				}
			}
		});
		thread.start();
	}
}
