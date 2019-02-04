import sound.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test{
    public static void main(String[] args) throws IOException{
        AudioSequence as = new AudioSequence(0.1);
	//	for (int i = 0; i < 30; i++){
	//		as.addSound(new SineWaveSound(300,0.3));
	//		as.advance();
	//	}
		for (int i = 0; i < 30; i++){
			as.addSound(new TriangleWaveSound(300,0.3));
			as.advance();
		}
  //      as.addSound(new SineWaveSound(0.2,0.3));
  //      as.addSound(new SineWaveSound(0.5,0.3));
   //     as.advance();
   //     as.addSound(new SineWaveSound(0.5,0.3));
        as.write(new FileOutputStream("file.wav"));
    }
}