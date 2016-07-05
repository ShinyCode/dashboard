import java.util.Random;


public class RandomAddressSensor extends Sensor
{
	public RandomAddressSensor(int updateDelay)
	{
		super(updateDelay);
		r = new Random();
	}
	
	public String getReading()
	{
		String address = PREFIX;
		for(int i = 0; i < NUM_DIGITS; ++i)
		{
			address += r.nextInt(10);
		}
		return address;
	}
	
	private static final String PREFIX = "0x";
	private static final int NUM_DIGITS = 8;
	private Random r;
}
