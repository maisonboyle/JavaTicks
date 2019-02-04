package uk.ac.cam.dab80.oop.tick1star;


public class AnimatedLife {
	
	private boolean[][] world;
	private int width;
	private int height;
	private Pattern pattern;
	private OutputAnimatedGif gif;
	
	public AnimatedLife(String[] args) throws Exception{
		pattern = new Pattern(args[0]);
		gif = new OutputAnimatedGif(args[2]);
		width = pattern.getWidth();
		height = pattern.getHeight();
		world = new boolean[height][width];
		pattern.initialise(world);
	}
	
	public boolean getCell(int col, int row){
		if (row < 0 || row >= height) return false;
		if (col < 0 || col >= width) return false;
		return world[row][col];
	}
	
	public void setCell(int row, int col, boolean value){
		if (row < 0 || row >= height) return;
		if (col < 0 || col >= width) return;
		world[row][col] = value;
		return;
	}
	
	private int countNeighbours(int col, int row){
		int count = 0;
		for (int x = -1; x < 2; x++){
			for (int y = -1; y < 2; y ++){
				if (!(x == 0 && y == 0)){
					count += getCell(col+x,row+y) ? 1 : 0;
				}
			}
		}
		return count;
	}
	
	private boolean computeCell(int col,int row) {
		
		boolean liveCell = getCell(col, row);
	    int neighbours = countNeighbours(col, row);
	    boolean nextCell = false;
		
		if (liveCell){
			if (neighbours > 1 && neighbours < 4){
				nextCell = true;
			}
		} else if (neighbours == 3){
			nextCell = true;
		}
		
	    return nextCell;
	}
	
	public void nextGeneration(){
		boolean[][] nextGeneration = new boolean[height][];
		for (int y = 0; y < height; ++y) {
			nextGeneration[y] = new boolean[width];
			for (int x = 0; x < width; ++x) {
				boolean nextCell = computeCell(x, y);
				nextGeneration[y][x] = nextCell;
			}
		}
		world = nextGeneration;
	}
	
	public void play(String frames) throws Exception  {
		for (int i = Integer.parseInt(frames); i >= 0; i--){
			gif.addFrame(world);
			nextGeneration();
		}
		gif.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		AnimatedLife al = new AnimatedLife(args);
		al.play(args[1]);
	}
	
	
}