package uk.ac.cam.dab80.oop.tick2;
import java.io.IOException;

public class GameOfLife {

    private World world;

    public GameOfLife(World w) {
        world = w;
    }

    public void play() throws IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            world.nextGeneration();
        }
    }

    public void print() {
        System.out.println("- " + world.getGenerationCount());
        for (int row = 0; row < world.getPattern().getHeight(); row++) {
            for (int col = 0; col < world.getPattern().getWidth(); col++) {
                System.out.print(world.getCell(col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }
    // Could check if (userResponse = 10) as windows newline is \r\n which is 13 then 10
    // Skipping any 10 input means only iterated once per enter

    public static void main(String args[]) throws IOException {
        World w = null;

        if (args[0].equals("--array")){
            w = new ArrayWorld(args[1]);
        } else if (args[0].equals("--packed")){
            w = new PackedWorld(args[1]);
        }else {
            w = new ArrayWorld(args[0]);
        }

        GameOfLife gol = new GameOfLife(w);
        gol.play();
    }
}