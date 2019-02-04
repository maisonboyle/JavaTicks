package uk.ac.cam.dab80.oop.tick3;

public class Pattern implements Comparable<Pattern> {

	private String name;
	private String author;
	private int width;
	private int height;
	private int startCol;
	private int startRow;
	private String cells;
    
	@Override
	public int compareTo(Pattern o) {
		return name.compareTo(o.name);
	}
	
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

	public Pattern(String format) throws PatternFormatException{
		if (format.equals("")){throw new PatternFormatException("Please specify a pattern.");}
		String[] inputs = format.split(":");
		int fields = inputs.length;
		if (fields != 7){
			throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found "+fields+").");
		}
		// [name, author, width, height, startcol, startrow, cells]
		// Do remaining checks with regex. input[i].matches(pattern as string)
		// "\d+" = 1+ digits. Also [a-z] or [0-9]
		name = inputs[0];
		author = inputs[1];
		try{
			width = Integer.parseInt(inputs[2]);
		}catch (NumberFormatException e){
			throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ('"+inputs[2]+"' given).");
		}
		try{
			height = Integer.parseInt(inputs[3]);
		}catch (NumberFormatException e){
			throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number ('"+inputs[3]+"' given).");
		}
		try{
			startCol = Integer.parseInt(inputs[4]);
		}catch (NumberFormatException e){
			throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number ('"+inputs[4]+"' given).");
		}	
		try{
			startRow = Integer.parseInt(inputs[5]);
		}catch (NumberFormatException e){
			throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as a number ('"+inputs[5]+"' given).");
		}
		if (inputs[6].matches("[\\s01]*")){
			cells = inputs[6];	
		}else{
			throw new PatternFormatException("Invalid pattern format: Malformed pattern '"+inputs[6]+"'.");
		}
	}

	public void initialise(World world) throws PatternFormatException {
		String[] cellRows = cells.split(" ");
		for (int y = 0; y < cellRows.length; y++){
			char[] row = cellRows[y].toCharArray();
			for (int x = 0; x < row.length; x++){
				if (row[x] == '1'){
					try{
						world.setCell(startCol + x, startRow + y, true);
					}catch (ArrayIndexOutOfBoundsException e){
						throw new PatternFormatException("Invalid pattern format: Pattern cannot be applied to this world.");
					}
				}
			}
		}
	}
}