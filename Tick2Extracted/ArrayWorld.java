

public class ArrayWorld extends World {

    private boolean[][] world;

    public ArrayWorld(String serial)  {
        super(serial);
        world = new boolean[getPattern().getHeight()][getPattern().getWidth()];
        getPattern().initialise(this);
    }

    protected void nextGenerationImpl(){
        boolean[][] nextGeneration = new boolean[getPattern().getHeight()][];
        for (int y = 0; y < getPattern().getHeight(); ++y) {
            nextGeneration[y] = new boolean[getPattern().getWidth()];
            for (int x = 0; x < getPattern().getWidth(); ++x) {
                boolean nextCell = computeCell(x, y);
                nextGeneration[y][x] = nextCell;
            }
        }
        world = nextGeneration;
    }

    public boolean getCell(int col, int row){
        if (row < 0 || row >= getPattern().getHeight()) return false;
        if (col < 0 || col >= getPattern().getWidth()) return false;
        return world[row][col];
    }

    public void setCell(int col, int row, boolean value){
        if (row < 0 || row >= getPattern().getHeight()) return;
        if (col < 0 || col >= getPattern().getWidth()) return;
        world[row][col] = value;
        return;
    }


}