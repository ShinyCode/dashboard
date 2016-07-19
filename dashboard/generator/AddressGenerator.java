package dashboard.generator;

import java.util.Random;

import dashboard.readout.Readout;
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
	 * Creates an AddressGenerator with the specified timing interval.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public AddressGenerator(int interval)
	{
		super(interval);
		r = new Random();
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
		for(String key : readouts.keySet())
		{
			Readout readout = readouts.get(key);
			if(readout instanceof StringUpdatable) ((StringUpdatable)readout).update(generateRandomAddress());
		}
	}
}
