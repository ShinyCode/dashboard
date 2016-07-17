package dashboard;
import java.awt.Color;

import acm.graphics.GRect;

/**
 * Provides the base functionality for a grid of {@link Button Buttons}. As the number of rows and columns
 * are specified by the user, the ButtonGrid automatically resizes its constituent Buttons accordingly.
 * 
 * @author Mark Sabini
 *
 */
public abstract class ButtonGrid extends MouseWidget
{
	/**
	 * An array storing all the buttons contained in the ButtonGrid
	 */
	private Button[] buttons;
	
	/**
	 * The ButtonGrid's colored base
	 */
	private GRect base;
	
	/**
	 * The number of rows in the ButtonGrid
	 */
	private int numRows;
	
	/**
	 * The number of columns in the ButtonGrid
	 */
	private int numCols;
	
	/**
	 * The width of each button, as calculated by the ButtonGrid
	 */
	private double buttonWidth;
	
	/**
	 * The height of each button, as calculated by the ButtonGrid
	 */
	private double buttonHeight;
	
	/**
	 * The width of the spacing surrounding all the buttons
	 */
	private double spacing;
	
	/**
	 * Builder for the ButtonGrid class, to be extended by all subclasses of ButtonGrid.
	 * 
	 * @author Mark Sabini
	 *
	 * @param <T> dummy parameter to enable the subclasses to return their exact type
	 */
	@SuppressWarnings("rawtypes")
	protected static abstract class Builder<T extends Builder>
	{
		/**
		 * The width of the ButtonGrid
		 */
		protected final double width;
		
		/**
		 * The height of the ButtonGrid
		 */
		protected final double height;
		
		/**
		 * The number of rows in the ButtonGrid, set to 1 by default
		 */
		protected int numRows = 1;
		
		/**
		 * The number of columns in the ButtonGrid, set to 1 by default
		 */
		protected int numCols = 1;
		
		/**
		 * The spacing to use for the ButtonGrid, set to 0.0 by default
		 */
		protected double spacing = 0.0;
		
		/**
		 * The color of the ButtonGrid's base, set to black by default
		 */
		protected Color baseColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a ButtonGrid with the given dimensions.
		 * 
		 * @param width the width of the ButtonGrid
		 * @param height the height of the ButtonGrid
		 */
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Specifies the number of rows and columns that the ButtonGrid should have.
		 * 
		 * @param numRows the number of rows in the ButtonGrid
		 * @param numCols the number of columns in the ButtonGrid
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withRowsCols(int numRows, int numCols)
		{
			this.numRows = numRows;
			this.numCols = numCols;
			return (T)this;
		}
		
		/**
		 * Specifies the spacing of the ButtonGrid.
		 * 
		 * @param spacing the spacing of the ButtonGrid
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withSpacing(double spacing)
		{
			this.spacing = spacing;
			return (T)this;
		}
		
		/**
		 * Specifies the color of the ButtonGrid's base.
		 * 
		 * @param baseColor the color of the ButtonGrid's base
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		/**
		 * Creates a new ButtonGrid with the Builder's parameters. Internally, the function should call
		 * the Group's protected constructor.
		 * 
		 * @return a new ButtonGrid with the Builder's parameters
		 */
		public abstract ButtonGrid build();			
	}
	
	/**
	 * Creates a ButtonGrid with the specified dimensions, number of rows, number of columns, spacing, and base color.
	 * 
	 * @param width the width of the ButtonGrid
	 * @param height the height of the ButtonGrid
	 * @param numRows the number of rows in the ButtonGrid
	 * @param numCols the number of columns in the ButtonGrid
	 * @param spacing the spacing of the ButtonGrid
	 * @param baseColor the base color of the ButtonGrid
	 */
	protected ButtonGrid(double width, double height, int numRows, int numCols, double spacing, Color baseColor)
	{
		buttons = new Button[numRows * numCols];
		this.numRows = numRows;
		this.numCols = numCols;
		this.spacing = spacing;
		buttonWidth = (width - (numCols + 1) * spacing) / numCols;
		buttonHeight = (height - (numRows + 1) * spacing) / numRows;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	/**
	 * Adds a button to the ButtonGrid at the specified row and column. The row and column are 0-indexed.
	 * 
	 * @param gb the button to be added
	 * @param row the row in which the button is to be placed
	 * @param col the column in which the button is to be placed
	 * @return whether the button was successfully added
	 */
	protected boolean addButton(Button gb, int row, int col)
	{
		return addButton(gb, row, col, 1, 1);
	}
	
	/**
	 * Adds a button to the ButtonGrid at the specified row and column, spanning the specifed number of rows and columns.
	 * The row and column are 0-indexed.
	 * 
	 * @param gb the button to be added
	 * @param row the row in which the button is to be placed
	 * @param col the column in which the button is to be placed
	 * @param rowSpan how many rows the button should span
	 * @param colSpan how many columns the button should span
	 * @return whether the button was successfully added
	 */
	protected boolean addButton(Button gb, int row, int col, int rowSpan, int colSpan)
	{
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return false; //Invalid row, col dimension
		if(rowSpan <= 0 || colSpan <= 0 || rowSpan > numRows - row || colSpan > numCols - col) return false; //Invalid row/column Span
		gb.resize(colSpan * buttonWidth + (colSpan - 1) * spacing, rowSpan * buttonHeight + (rowSpan - 1) * spacing);
		add(gb, col * buttonWidth + (col + 1) * spacing, row * buttonHeight + (row + 1) * spacing);
		buttons[getIndex(row, col)] = gb;
		return true;
	}
	
	/**
	 * Returns the button at the specified row and column.
	 * 
	 * @param row the row of the button to return
	 * @param col the column of the button to return
	 * @return the button if it exists. If the button does not exist, the function returns null.
	 */
	protected Button getButton(int row, int col)
	{
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return null;
		return buttons[getIndex(row, col)];
	}
	
	/**
	 * Converts a 0-indexed row and column into the corresponding index in the list of buttons
	 * 
	 * @param row the row in the ButtonGrid
	 * @param col the column in the ButtonGrid
	 * @return the corresponding index in the list of buttons
	 */
	private int getIndex(int row, int col)
	{
		return row * numCols + col;
	}
}
