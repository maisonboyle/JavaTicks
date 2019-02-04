package uk.ac.cam.dab80.oop.tick5;

public class ArrayWorld extends World implements Cloneable{

    private boolean[][] world;
	private boolean[] deadRow;

	@Override
	public ArrayWorld clone() throws CloneNotSupportedException{
		ArrayWorld copyWorld = (ArrayWorld)super.clone();
		// deep copy of array
		copyWorld.world = new boolean[getHeight()][getWidth()];
		for (int i = 0; i < getHeight(); i++){
			// deadRow used where all cells set to false
			boolean allDead = true;
			for (int j = 0; j < getWidth(); j++){
				boolean alive = world[i][j];
				if (alive){
					allDead = false;
				}
				copyWorld.world[i][j] = alive;
			}
			if (allDead){
				copyWorld.world[i] = deadRow;
			}
		}
		return copyWorld;
	}
	
	private void setDead(){
		for (int i = 0; i < getHeight(); i++){
			// deadRow used where all cells set to false
			boolean allDead = true;
			for (int j = 0; j < getWidth(); j++){
				if (world[i][j]){
					allDead = false;
					break;
				}
			}
			if (allDead){
				world[i] = deadRow;
			}
		}
	}
	
    public ArrayWorld(String serial) throws PatternFormatException {
		// pattern set in parent class
        super(serial);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
		deadRow = new boolean[getWidth()];
		setDead();
    }
	
	public ArrayWorld(Pattern p) {
        super(p);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
		deadRow = new boolean[getWidth()];
		setDead();
    }

	public ArrayWorld(ArrayWorld toCopy) {
		super(toCopy);
		// deep copy of array for world
		world = new boolean[getHeight()][getWidth()];
		for (int i = 0; i < getHeight(); i++){
			for (int j = 0; j < getWidth(); j++){
					world[i][j] = toCopy.world[i][j];
			}
		}
		deadRow = toCopy.deadRow;
		setDead();
	}
	
    protected void nextGenerationImpl(){
		// create new array of cells for next generation
        boolean[][] nextGeneration = new boolean[getHeight()][];
        for (int y = 0; y < getHeight(); ++y) {
            nextGeneration[y] = new boolean[getWidth()];
            for (int x = 0; x < getWidth(); ++x) {
                boolean nextCell = computeCell(x, y);
                nextGeneration[y][x] = nextCell;
            }
        }
        world = nextGeneration;
    }

    public boolean getCell(int col, int row){
		// always false if outside board
        if (row < 0 || row >= getHeight()) return false;
        if (col < 0 || col >= getWidth()) return false;
        return world[row][col];
    }

    public void setCell(int col, int row, boolean value){
        if (row < 0 || row >= getHeight()) return;
        if (col < 0 || col >= getWidth()) return;
        world[row][col] = value;
        return;
    }


}