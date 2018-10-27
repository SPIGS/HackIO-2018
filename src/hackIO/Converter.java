package hackIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Converter {
	private static final ArrayList<String> SUPPORTED_NOTES = new ArrayList<String>(Arrays.asList("C2", "C#2", "D2", "D#2", "E2", "F2", "F#2", "G2", "G#2", "A2", "A#2", "B2",
																								 "C3", "C#3", "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3",
																								 "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4",
																								 "C5"));
	
	public Converter (ArrayList<Channel> channels) throws IOException {
		convert_to_frequency(channels);
		
		//AudioGenerator.wav(make_text_file());
	}
	
	public void convert_to_frequency (ArrayList<Channel> channels) {
		
		for (Channel channel : channels) {
			for (Note note : channel.notes) {
				note.frequency = SUPPORTED_NOTES.indexOf(note.note);
				if (note.frequency == -1) {
					note.frequency = SUPPORTED_NOTES.indexOf("C5");
				}
			}
		}
	}
	
	public File make_text_file (String song_name) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			
			String data = "[:phoneme on][:rate 150][";
			String noise = "laa";
			int i = 0;
			for (Voice voice : converted_voices) {
				File file = new File("res/text/" + song_name + i + ".txt");
				System.out.println("trying to make file");
				FileWriter writer = new FileWriter(file);
				for (Note note : converted_voices.get(0).raw_notes) {
					data = data + noise + "<" + note.length_miliseconds + "," + note.frequency + ">";
				}
				data = data + "]";
				writer.write(data);
				writer.close();
				i++;
				files.add(file);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files.get(0);
	}
}
