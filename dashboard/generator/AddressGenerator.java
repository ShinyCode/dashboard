package dashboard.generator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dashboard.readout.StringUpdatable;

/**
 * Implements a Generator that generates random memory addresses in hexadecimal. Intended to be used
 * with {@link StringUpdatable StringUpdatables}.
 * 
 * @author Mark Sabini
 *
 */
public final class AddressGenerator extends Generator
{
	/**
	 * The prefix for the address
	 */
	private static final String PREFIX = "0x";
	
	/**
	 * The number of characters the address should have, not including the prefix
	 */
	private static final int NUM_CHARS = 8;
	
	/**
	 * The Random generator used to generate random hexadecimal digits
	 */
	private Random r;
	
	/**
	 * Maps keys to their associated StringUpdatables
	 */
	private Map<String, StringUpdatable> stringUpdatables;
	
	/**
	 * Creates an AddressGenerator with the specified timing interval.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public AddressGenerator(int interval)
	{
		super(interval);
		stringUpdatables = new HashMap<String, StringUpdatable>();
		r = new Random();
	}
	
	/**
	 * Sets the specified StringUpdatable to be updated by the AddressGenerator, and binds it to the 
	 * given key.
	 * 
	 * @param key a handle that refers to the StringUpdatable
	 * @param su the StringUpdatable to be updated
	 */
	public void addStringUpdatable(String key, StringUpdatable su)
	{
		if(stringUpdatables.containsKey(key)) return;
		stringUpdatables.put(key, su);
	}
	
	/**
	 * Removes the StringUpdatable bound to the specific key. If the StringUpdatable could not be located,
	 * no action is taken.
	 * @param key the key that was bound to the StringUpdatable in {@link #addStringUpdatable(String, StringUpdatable) addStringUpdatable}
	 */
	public void removeStringUpdatable(String key)
	{
		if(!stringUpdatables.containsKey(key)) return;
		stringUpdatables.remove(key);
	}
	
	/**
	 * Generates a random 8-byte hexadecimal address.
	 * @return the address that was randomly generated
	 */
	private String generateRandomAddress()
	{
		String address = PREFIX;
		for(int i = 0; i < NUM_CHARS; ++i)
		{
			int chosen = r.nextInt(16);
			if(chosen < 10) address += chosen;
			else address += (char)('A' + (chosen - 10));
		}
		return address;
	}
	
	/**
	 * Updates all the StringUpdatables associated with the AddressGenerator. Note that each
	 * StringUpdatable will receive a different random address.
	 */
	@Override
	public void generate()
	{
		for(String key : stringUpdatables.keySet())
		{
			stringUpdatables.get(key).update(generateRandomAddress());
		}
	}
}
