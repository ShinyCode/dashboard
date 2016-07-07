package dashboard;

public interface LevelUpdatable {
	public void setLevel(int level); // The internal discretized level
	public int getNumDivisions(); // Get the number of divisions within // TODO: Maybe call this getNumLevels?
}
