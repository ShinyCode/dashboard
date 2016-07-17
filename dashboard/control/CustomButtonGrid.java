package dashboard.control;
import java.awt.Color;

/**
 * Implements a custom grid of {@link Button Buttons} that can be constructed at runtime. Pre-fabricated button grid widgets
 * should subclass {@link ButtonGrid ButtonGrid} instead of CustomButtonGrid.
 * 
 * @author Mark Sabini
 *
 */
public final class CustomButtonGrid extends ButtonGrid
{
	/**
	 * Builder for the CustomButtonGrid class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends ButtonGrid.Builder<Builder>
	{
		/**
		 * Creates a Builder specifying a CustomButtonGrid with the given dimensions.
		 * 
		 * @param width the width of the CustomButtonGrid
		 * @param height the height of the CustomButtonGrid
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Creates a new CustomButtonGrid with the Builder's parameters.
		 * @return a new CustomButtonGrid with the Builder's parameters
		 */
		@Override
		public CustomButtonGrid build()
		{
			return new CustomButtonGrid(width, height, numRows, numCols, spacing, baseColor);
		}		
	}
	
	/**
	 * Creates a CustomButtonGrid with the specified dimensions, number of rows, number of columns, spacing, and base color.
	 * 
	 * @param width the width of the CustomButtonGrid
	 * @param height the height of the CustomButtonGrid
	 * @param numRows the number of rows in the CustomButtonGrid
	 * @param numCols the number of columns in the CustomButtonGrid
	 * @param spacing the spacing of the CustomButtonGrid
	 * @param baseColor the base color of the CustomButtonGrid
	 */
	protected CustomButtonGrid(double width, double height, int numRows, int numCols, double spacing, Color baseColor)
	{
		super(width, height, numRows, numCols, spacing, baseColor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addButton(Button gb, int row, int col)
	{
		return super.addButton(gb, row, col);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addButton(Button gb, int row, int col, int rowSpan, int colSpan)
	{
		return super.addButton(gb, row, col, rowSpan, colSpan);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Button getButton(int row, int col)
	{
		return super.getButton(row, col);
	}
}
