package uk.ac.cam.dab80.oop.tick3;

public class ArrayWorld extends World {

    private boolean[][] world;

    public ArrayWorld(String serial) throws PatternFormatException  {
        super(serial);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }
	
	public ArrayWorld(Pattern p) throws PatternFormatException  {
        super(p);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }

    protected void nextGenerationImpl(){
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