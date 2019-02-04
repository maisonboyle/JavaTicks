package uk.ac.cam.dab80.oop.tick4;


public class PackedWorld extends World implements Cloneable{

    private long world;
    // If smaller than 8x8, top left corner used

	
	@Override
	public PackedWorld clone() throws CloneNotSupportedException{
		return (PackedWorld)super.clone();
	}
	
    public PackedWorld(String serial) throws PatternFormatException {
		// pattern set in parent class
        super(serial);
        if (getWidth() * getHeight() > 64){
            throw new IllegalArgumentException("World cannot be more than 64 cells");
        }
        getPattern().initialise(this);
    }
	public PackedWorld(Pattern p) {
        super(p);
        if (getWidth() * getHeight() > 64){
            throw new IllegalArgumentException("World cannot be more than 64 cells");
        }
        getPattern().initialise(this);
    }
	
	public PackedWorld(PackedWorld toCopy){
		super(toCopy);
		world = toCopy.world;
	}

    protected void nextGenerationImpl(){
		// new long created to store next generation
        long nextGen = 0;
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                if (computeCell(x, y)){
                    nextGen |= 1L << (x + y*getWidth());
                }
            }
        }
        world = nextGen;
    }

    public boolean getCell(int col, int row){
		// always false if outside board
        if (row < 0 || row >= getHeight()) return false;
        if (col < 0 || col >= getWidth()) return false;
        return (((world >>> (col + row*getWidth())) & 1) == 1);
    }

    public void setCell(int col, int row, boolean value){
        if (row < 0 || row >= getHeight()) return;
        if (col < 0 || col >= getWidth()) return;
        if (value){
            world |= 1L << (col + row*getWidth());
        }
        else {
            world &= ~(1L << (col + row*getWidth()));
        }
        return;
    }
}