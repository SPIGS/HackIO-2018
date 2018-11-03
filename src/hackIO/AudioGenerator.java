package hackIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioGenerator {
	public static Runtime runtime = Runtime.getRuntime();
	
	public static File wav(File input) throws IOException
	{
		File output = new File("res/wav/" + input.getName().replace(".txt", ".wav") );
		
		runtime.exec("cmd /c \"cd dectalk && say -w ../" + output.getPath() + " < ../" + input.getPath() + "\"");
		
		return output;
	}
	
	public static File concatenateChannels (ArrayList<File> channelWavs) {
		ArrayList <AudioInputStream> channelStreams = new ArrayList<AudioInputStream>();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
