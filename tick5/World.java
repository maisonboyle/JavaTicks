package uk.ac.cam.dab80.oop.tick5;

public abstract class World implements Cloneable{
	private int mGeneration = 0;
	private Pattern mPattern;

	World(String format) throws PatternFormatException{
		mPattern = new Pattern(format);
	}
	World(Pattern p) {
		mPattern = p;
	}
	
	@Override
	public World clone() throws CloneNotSupportedException{
		return (World)super.clone();
	}
	
	World(World toCopy){
		mGeneration = toCopy.mGeneration;
		mPattern = toCopy.mPattern;
	}

	public int getWidth() {
		return mPattern.getWidth();
	}

	public int getHeight() {
		return mPattern.getHeight();
	}

	public int getGenerationCount() {
		return mGeneration;
	}

	protected void incrementGenerationCount() {
		mGeneration++;
	}

	protected Pattern getPattern() {
		return mPattern;
	}

	protected abstract void nextGenerationImpl();

	public void nextGeneration(){
		// does not require child classes to increment generation
		nextGenerationImpl();
		mGeneration++;
	}

	public abstract boolean getCell(int col, int row);

	public abstract void setCell(int col, int row, boolean v);

	protected int countNeighbours(int col, int row) {
		int count = 0;
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				// cells in 3x3 grid around (x,y) ignoring itself
				if (!(x == 0 && y == 0)) {
					count += getCell(col + x, row + y) ? 1 : 0;
				}
			}
		}
		return count;
	}

	protected boolean computeCell(int col, int row) {
		boolean liveCell = getCell(col, row);
		int neighbours = countNeighbours(col, row);
		boolean nextCell = false;
		// alive and 2 or 3 neighbours
		if (liveCell) {
			if (neighbours > 1 && neighbours < 4) {
				nextCell = true;
			}
		// dead and exactly 3 neighbours
		} else if (neighbours == 3) {
			nextCell = true;
		}
		return nextCell;
	}
}