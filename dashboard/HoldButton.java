package dashboard;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class HoldButton extends Button
{
	private int interval;
	private static final int DEFAULT_INTERVAL = 50;
	
	public HoldButton(double width, double height, Color baseColor, String instr)
	{
		super(width, height, baseColor, instr);
		interval = DEFAULT_INTERVAL;
	}
	
	@Override
	public String mousePressed(MouseEvent e)
	{
		rect.setColor(baseColor.brighter());
		repeatOnAction(interval);
		return getInstr() + ".SET_ACTIVE_TRUE";
	}
	
	// NOTE: Only the onAction is repeated. This is so we can have the cleanup code called only once.
	@Override
	public String mouseReleased(MouseEvent e)
	{
		rect.setColor(baseColor);
		runOffAction();
		return getInstr() + ".SET_ACTIVE_FALSE";
	}
	
	public void setInterval(int interval)
	{
		if(interval < 0) throw new IllegalArgumentException();
		this.interval = interval;
	}
}
