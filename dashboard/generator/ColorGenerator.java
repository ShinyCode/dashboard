package dashboard.generator;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dashboard.readout.ColorUpdatable;

public final class ColorGenerator extends Generator
{
	private static final int MAX_COLOR_VALUE = 256;
	
	private Random random;
	private Map<String, ColorUpdatable> colorUpdatables;

	public ColorGenerator(int interval)
	{
		super(interval);
		random = new Random();
		colorUpdatables = new HashMap<String, ColorUpdatable>();
	}
	
	public void addColorUpdatable(String key, ColorUpdatable cu)
	{
		if(colorUpdatables.containsKey(key)) return;
		colorUpdatables.put(key, cu);
	}
	
	public void removeColorUpdatable(String key)
	{
		if(!colorUpdatables.containsKey(key)) return;
		colorUpdatables.remove(key);
	}
	
	private Color generateRandomColor()
	{
		int r = random.nextInt(MAX_COLOR_VALUE);
		int g = random.nextInt(MAX_COLOR_VALUE);
		int b = random.nextInt(MAX_COLOR_VALUE);
		return new Color(r, g, b);
	}
	
	@Override
	public void generate()
	{
		for(String key : colorUpdatables.keySet())
		{
			colorUpdatables.get(key).update(generateRandomColor());
		}
	}

}
