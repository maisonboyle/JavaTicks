package uk.ac.cam.dab80.oop.tick1;

public class ArrayLife {
	
	private boolean[][] world;
	private int width;
	private int height;
	private Pattern pattern;
	
	public ArrayLife(String format){
		pattern = new Pattern(format);
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
	
	public void print() { 
		System.out.println("-"); 
		for (int row = 0; row < height; row++) { 
			for (int col = 0; col < width; col++) {
				System.out.print(getCell(col, row) ? "#" : "_"); 
			}
		System.out.println(); 
		}	 
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
	
	public void play() throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
				print();
				userResponse = System.in.read();
				nextGeneration();
		}
	}
	// Could check if (userResponse = 10) as windows newline is \r\n which is 13 then 10
	// Skipping any 10 input means only iterated once per enter
	
	
	public static void main(String[] args) throws Exception {
		ArrayLife al = new ArrayLife(args[0]);
		al.play();
	}
	
	
}