package hackIO;
import java.io.File;

public class AudioGenerator {
	public static Runtime runtime = Runtime.getRuntime();
	
	public static File wav(File input)
	{
		File output = new File("res/wav/" + input.getName() + ".wav");
		
		return output;
	}
	
}
