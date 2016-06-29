import java.awt.Color;


public final class CustomButtonGrid extends ButtonGrid
{
	protected CustomButtonGrid(double width, double height, int numRows, int numCols, double spacing, Color baseColor)
	{
		super(width, height, numRows, numCols, spacing, baseColor);
	}
	
	public boolean addButton(Button gb, int row, int col)
	{
		return super.addButton(gb, row, col);
	}
	
	public boolean addButton(Button gb, int row, int col, int rowSpan, int colSpan)
	{
		return super.addButton(gb, row, col, rowSpan, colSpan);
	}
	
	public Button getButton(int row, int col)
	{
		return super.getButton(row, col);
	}
}
