package dashboard;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class AddressGenerator extends Generator
{
	public AddressGenerator(int updateDelay)
	{
		super(updateDelay);
		stringUpdatables = new HashMap<String, StringUpdatable>();
		r = new Random();
	}
	
	public void addStringUpdatable(String key, StringUpdatable su)
	{
		if(stringUpdatables.containsKey(key)) return;
		stringUpdatables.put(key, su);
	}
	
	public void removeStringUpdatable(String key)
	{
		if(!stringUpdatables.containsKey(key)) return;
		stringUpdatables.remove(key);
	}
	
	private String generateRandomAddress()
	{
		String address = PREFIX;
		for(int i = 0; i < NUM_DIGITS; ++i)
		{
			int chosen = r.nextInt(16);
			if(chosen < 10) address += chosen;
			else address += (char)('A' + (chosen - 10));
		}
		return address;
	}
	
	public void generate()
	{
		for(String key : stringUpdatables.keySet())
		{
			stringUpdatables.get(key).update(generateRandomAddress());
		}
	}
	
	private static final String PREFIX = "0x";
	private static final int NUM_DIGITS = 8;
	private Random r;
	private Map<String, StringUpdatable> stringUpdatables;
}
