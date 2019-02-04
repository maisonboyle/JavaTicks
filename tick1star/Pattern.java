package uk.ac.cam.dab80.oop.tick1star;

public class Pattern {

	private String name;
	private String author;
	private int width;
	private int height;
	private int startCol;
	private int startRow;
	private String cells;
    
	public String getName() {
		return name;
	}
  
	public String getAuthor() {
		return author;
	}
  
	public int getWidth(){
		return width;
	}
  
	public int getHeight(){
		return height;
	}
  
	public int getStartCol(){
		return startCol;
	}
  
	public int getStartRow(){
		return startRow;
	}
  
	public String getCells(){
		return cells;
	}

	public Pattern(String format) {
		String[] inputs = format.split(":"); 
		// [name, author, width, height, startcol, startrow, cells]
		
		name = inputs[0];
		author = inputs[1];
		width = Integer.parseInt(inputs[2]);
		height = Integer.parseInt(inputs[3]);
		startCol = Integer.parseInt(inputs[4]);
		startRow = Integer.parseInt(inputs[5]);
		cells = inputs[6];	
	}

	public void initialise(boolean[][] world) {
		
		String[] cellRows = cells.split(" ");
		for (int y = 0; y < cellRows.length; y++){
			char[] row = cellRows[y].toCharArray();
			for (int x = 0; x < row.length; x++){
				if (row[x] == '1'){
					setCell(world, startRow + y, startCol + x, true);
				}
			}
		}
	}
	
	public void setCell(boolean[][] world, int row, int col, boolean value){
		if (row < 0 || row >= height) return;
		if (col < 0 || col >= width) return;
		world[row][col] = value;
	}
	
	public static void main(String[] args) {
		Pattern pat = new Pattern(args[0]);
		System.out.println("Name: " + pat.getName());
		System.out.println("Author: " + pat.getAuthor());
		System.out.println("Width: " + pat.getWidth());
		System.out.println("Height: " + pat.getHeight());
		System.out.println("StartCol: " + pat.getStartCol());
		System.out.println("StartRow: " + pat.getStartRow());
		System.out.println("Pattern: " + pat.getCells());
	}
}