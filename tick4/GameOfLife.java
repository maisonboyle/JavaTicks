package uk.ac.cam.dab80.oop.tick4;
import java.io.*;
import java.util.*;

public class GameOfLife {

    private World world;
	private PatternStore store;
	private ArrayList<World> cachedWorlds;

    public GameOfLife(PatternStore ps) {
		store = ps;
    }

	private World copyWorld(boolean useCloning) {
		if (useCloning){
			try{
				return world.clone();
			}catch (CloneNotSupportedException e){
				// Should never fail to clone an existing world
				throw new RuntimeException(e);
			}
		// check which child class to use for copy method
		}else if(world instanceof ArrayWorld){
			return new ArrayWorld((ArrayWorld)world);
		}else{
			return new PackedWorld((PackedWorld)world);
		}
	}
	
	public void play() throws IOException {
        
		String response="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
		System.out.println("Please select a pattern to play (l to list:");
		while (!response.equals("q")) {
			response = in.readLine();
			System.out.println(response);
			if (response.equals("b")){
				if (world == null) {
					// no world loaded
					System.out.println("Please select a pattern to play (l to list):");
				} else {
					// load correct world from cache
					if (world.getGenerationCount() > 0){
						world = cachedWorlds.get(world.getGenerationCount() - 1);
					}
					print();
				}
			}
			if (response.equals("f")) {
				if (world == null) {
					// no world loaded
					System.out.println("Please select a pattern to play (l to list):");
				}
				else {
					// load from cache where possible
					if (world.getGenerationCount() < cachedWorlds.size() - 1){
						world = cachedWorlds.get(world.getGenerationCount() + 1);
					}else{
						world = copyWorld(true);
						world.nextGeneration();
						// store newly created world in cache
						cachedWorlds.add(world);
					}
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
				// part after " " is index of pattern to load
				int selection = Integer.parseInt(response.split(" ")[1]);
				Pattern choice = store.getPatternByName(names.get(selection).getName());
				cachedWorlds = new ArrayList<>();
				// use PackedWorld where possible
				if (choice.getWidth() * choice.getHeight() <= 64){
					world = new PackedWorld(choice);
				}else{
					world = new ArrayWorld(choice);
				}
				cachedWorlds.add(world);
				print();
			} 
		}
	}

	public static void main(String args[]) throws IOException {
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