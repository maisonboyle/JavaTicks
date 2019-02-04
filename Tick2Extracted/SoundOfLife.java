import sound.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundOfLife {

    private World world;
	private int totalFrames = 150;
	private String outputf = "result.wav";
	private double rowDiv;
	private double colDiv;
	
	
    public SoundOfLife(World w) {
        world = w;
    }

    public void play() throws IOException {
		
		AudioSequence as = new AudioSequence(0.05);
		
		while (totalFrames > 0){
			int width = world.getPattern().getWidth();
			int height = world.getPattern().getHeight();
			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					if (world.getCell(col, row)){
						double distance = Math.sqrt((col-width/2)*(col-width/2) + (row-height/2)*(row-height/2));
						distance /= Math.sqrt(width*width/4 + height*height/4);
						as.addSound(new TriangleWaveSound(100 + 400*distance,0.1));
					//	as.addSound(new TriangleWaveSound(250+rowDiv*(row-height/2)+ colDiv*(col-width/2),0.1));
					}
					
				}
			}		
			
			world.nextGeneration();
		
			as.advance();
			totalFrames--;
		}
		as.write(new FileOutputStream(outputf));
    }

    

    public static void main(String args[]) throws IOException {
		String setup = "no:no:40:40:20:20:011 110 01";
        World w = new ArrayWorld(setup);
        SoundOfLife sol = new SoundOfLife(w);
		sol.colDiv = 400.0/w.getHeight();
		sol.rowDiv = sol.colDiv/w.getWidth();
        sol.play();
    }
}