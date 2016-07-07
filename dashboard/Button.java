package dashboard;

import acm.graphics.*;

import java.awt.Color;

public abstract class Button extends MouseWidget
{
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
	
	public void setBaseColor(Color baseColor)
	{
		this.baseColor = baseColor;
		rect.setColor(baseColor);
	}
	
	public void setInstr(String instr)
	{
		label.setLocation(rect.getWidth() / 2, rect.getHeight() / 2);
		label.setLabel(instr.toUpperCase());
		label.move(-label.getWidth() / 2, label.getAscent() / 2);
	}
	
	public Color getBaseColor()
	{
		return baseColor;
	}
	
	public String getInstr()
	{
		return label.getLabel();
	}
	
	public void resize(double width, double height)
	{
		rect.setSize(width, height);
		setInstr(label.getLabel());
	}
	
	public void setOnAction(Runnable onAction)
	{
		this.onAction = onAction;
	}
	
	public void setOffAction(Runnable offAction)
	{
		this.offAction = offAction;
	}
	
	protected void runOnAction()
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(onAction == null) return;
		thread = new Thread(onAction);
		thread.start();
	}
	
	protected void runOffAction()
	{
		if(thread != null && thread.isAlive()) thread.interrupt();
		if(offAction == null) return;
		thread = new Thread(offAction);
		thread.start();
	}
	
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
	
	protected GRect rect;
	protected GLabel label;
	protected Color baseColor;
	private Runnable onAction;
	private Runnable offAction;
	private Thread thread;
}
