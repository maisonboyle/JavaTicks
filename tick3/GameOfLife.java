package uk.ac.cam.dab80.oop.tick3;
import java.io.*;
import java.util.*;

public class GameOfLife {

    private World world;
	private PatternStore store;

    public GameOfLife(PatternStore ps) {
		store = ps;
    }
	/*
    public void play() throws IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            world.nextGeneration();
        }
    }*/
	
	public void play() throws IOException, PatternFormatException {
        
		String response="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
		System.out.println("Please select a pattern to play (l to list:");
		while (!response.equals("q")) {
			response = in.readLine();
			System.out.println(response);
			if (response.equals("f")) {
				if (world == null) {
					System.out.println("Please select a pattern to play (l to list):");
				}
				else {
					world.nextGeneration();
					print();
				}
			}
			else if (response.equals("l")) {
				List<Pattern> names = store.getPatternsNameSorted();
				int i = 0;
				for (Pattern p : names) {
					System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
					i++;
				}
			}
			else if (response.startsWith("p")) {
				List<Pattern> names = store.getPatternsNameSorted();
				int selection = Integer.parseInt(response.split(" ")[1]);
				Pattern choice = store.getPatternByName(names.get(selection).getName());
				if (choice.getWidth() * choice.getHeight() <= 64){
					world = new PackedWorld(choice);
				}else{
					world = new ArrayWorld(choice);
				}
				
      // TODO: Extract the integer after the p in response
      // TODO: Get the associated pattern
      // TODO: Initialise world using PackedWorld or ArrayWorld based
      // on pattern world size
				print();
			} 
		}
	}

	public static void main(String args[]) throws IOException, PatternFormatException {
		if (args.length!=1) {
			System.out.println("Usage: java GameOfLife <path/url to store>");
			return;
		}
  
		try {
			PatternStore ps = new PatternStore(args[0]);
			GameOfLife gol = new GameOfLife(ps);    
			gol.play();
		}
		catch (IOException ioe) {
			System.out.println("Failed to load pattern store");
		}
	}
	

    public void print() {
        System.out.println("- " + world.getGenerationCount());
        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                System.out.print(world.getCell(col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

}