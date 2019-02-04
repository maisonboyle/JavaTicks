package uk.ac.cam.dab80.oop.tick2;


public class PackedWorld extends World {

    private long world;
    // If smaller than 8x8, top left corner used

    public PackedWorld(String serial)  {
        super(serial);
        if (getPattern().getWidth() * getPattern().getHeight() > 64){
            throw new IllegalArgumentException("World cannot be more than 64 cells");
        }
        getPattern().initialise(this);
    }

    protected void nextGenerationImpl(){
        long nextGen = 0;
        for (int y = 0; y < getPattern().getHeight(); ++y) {
            for (int x = 0; x < getPattern().getWidth(); ++x) {
                if (computeCell(x, y)){
                    nextGen |= 1L << (x + y*getPattern().getWidth());
                }
            }
        }
        world = nextGen;
    }

    public boolean getCell(int col, int row){
        if (row < 0 || row >= getPattern().getHeight()) return false;
        if (col < 0 || col >= getPattern().getWidth()) return false;
        return (((world >>> (col + row*getPattern().getWidth())) & 1) == 1);
    }

    public void setCell(int col, int row, boolean value){
        if (row < 0 || row >= getPattern().getHeight()) return;
        if (col < 0 || col >= getPattern().getWidth()) return;
        if (value){
            world |= 1L << (col + row*getPattern().getWidth());
        }
        else {
            world &= ~(1L << (col + row*getPattern().getWidth()));
        }
        return;
    }
}