
public final class CustomButtonGroup extends ButtonGroup
{
	public static final class Builder extends ButtonGroup.Builder<Builder>
	{
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public CustomButtonGroup build()
		{
			return new CustomButtonGrid(width, height, numRows, numCols, spacing, baseColor);
		}		
	}
}
