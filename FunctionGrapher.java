/*
 * File: BlankClass.java
 * ---------------------
 * This class is a blank one that you can change at will. Remember, if you change
 * the class name, you'll need to change the filename so that it matches.
 * Then you can extend GraphicsProgram, ConsoleProgram, or DialogProgram as you like.
 */

import acm.graphics.GCanvas;
import acm.io.IOConsole;
import acm.program.*;

public class FunctionGrapher extends Program {
	public void run() {
		int width = getWidth();
		int height = getHeight();
		add(new GCanvas(), CENTER, null);
		add(new IOConsole(), CENTER, null);
	}
}

