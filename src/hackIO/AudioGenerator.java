package hackIO;
import java.io.File;
import java.io.IOException;

public class AudioGenerator {
	public static Runtime runtime = Runtime.getRuntime();
	
	public static File wav(File input) throws IOException
	{
		File output = new File("res/wav/" + input.getName().replace(".txt", ".wav") );
		
		runtime.exec("cmd /c \"cd dectalk && say -w ../" + output.getPath() + " < ../" + input.getPath() + "\"");
		
		return output;
	}
	
}
