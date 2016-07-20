package dashboard.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dashboard.readout.Readout;
import dashboard.readout.StringUpdatable;

/**
 * Implements a Generator that reads text from a file and simulates data transmissions.
 * Intended to be used with {@link StringUpdatable StringUpdatables}.
 * 
 * @author Mark Sabini
 *
 */
public class BufferGenerator extends Generator
{
	/**
	 * The internal buffer of all the lines in the transmission file
	 */
	private List<String> lines;
	
	/**
	 * The current line being displayed
	 */
	private int index;
	
	/**
	 * Creates a BufferGenerator with the specified timing interval
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public BufferGenerator(int interval)
	{
		super(interval);
		lines = new ArrayList<String>();
		index = 0;
	}
	
	/**
	 * Sets the source of the transmission for the BufferGenerator. If the file could
	 * not be found, an error message is printed to all StringUpdatables associated
	 * with the BufferGenerator.
	 * 
	 * @param filename the filename of the source of the transmission
	 */
	public void setSource(String filename)
	{
		lines.clear();
		index = 0;
		try
		{
			Scanner sc = new Scanner(new File(filename));
			while(sc.hasNextLine())
			{
				lines.add(sc.nextLine());
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			generate("Transmission error.");
		}
	}
	
	/**
	 * Resets the transmission to the very beginning.
	 */
	public void reset()
	{
		index = 0;
	}
	
	/**
	 * Helper method that prints a message to all StringUpdatables associated
	 * with the BufferGenerator
	 * 
	 * @param line the line of text to print
	 */
	private void generate(String line)
	{
		for(String key : readouts.keySet())
		{
			Readout readout = readouts.get(key);
			if(readout instanceof StringUpdatable) ((StringUpdatable)readout).update(line);
		}
	}
	
	/**
	 * Updates all the StringUpdatables associated with the BufferGenerator with the next line
	 * in the transmission. If the end of the transmission has been reached,
	 * the BufferGenerator will set itself to be inactive.
	 */
	@Override
	public void generate()
	{
		// If at end of buffer, stop the Generator
		if(index == lines.size())
		{
			setActive(false);
			return;
		}
		generate(lines.get(index));
		++index;
	}

}
