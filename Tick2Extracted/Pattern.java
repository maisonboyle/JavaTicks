

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

	public void initialise(World world) {
		
		String[] cellRows = cells.split(" ");
		for (int y = 0; y < cellRows.length; y++){
			char[] row = cellRows[y].toCharArray();
			for (int x = 0; x < row.length; x++){
				if (row[x] == '1'){
					world.setCell(startCol + x, startRow + y, true);
				}
			}
		}
	}
}