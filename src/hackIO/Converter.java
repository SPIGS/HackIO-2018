package hackIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Converter {
	private ArrayList<Voice> raw_voices;
	private static final ArrayList<String> SUPPORTED_NOTES = new ArrayList<String>(Arrays.asList("C2", "C#2", "D2", "D#2", "E2", "F2", "F#2", "G2", "G#2", "A2", "A#2", "B2",
																								 "C3", "C#3", "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3",
																								 "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4",
																								 "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5"));

	// Tick to miliseconds =  tick * (60000/ (bpm * PPQ);
	private float length_of_tick;
	private ArrayList<Voice> converted_voices = new ArrayList<Voice> ();
	
	public Converter (ArrayList<Voice> raw_voices, float length_of_tick, String song_name) throws IOException {
		this.raw_voices = raw_voices;
		this.length_of_tick = length_of_tick;
		convert();
		AudioGenerator.wav(make_text_file(song_name));
	}
	
	public void convert () {
		HashMap<Long, Note> ordered_notes = new HashMap<Long, Note>();
		HashMap<String, Long> hit_notes = new HashMap<String, Long>();
		ArrayList<Note> converted_notes = new ArrayList<Note> ();
		for (Voice voice : raw_voices) {
			for (Note note: voice.raw_notes) {
				ordered_notes.put(note.tick, note);
				if (hit_notes.containsKey(note.note)) {
					Note on_note = ordered_notes.get(hit_notes.get(note.note));
					Note off_note = note;
					on_note.length_tick = off_note.tick-on_note.tick;
					converted_notes.add(on_note);
					hit_notes.remove(on_note.note, on_note.tick);
				} else {
					hit_notes.put(note.note, note.tick);
				}
			}
			//in case note have negative length
			for (Note note : converted_notes) {
				if (note.length_tick < 0) {
					note.length_tick = note.length_tick * -1;
				}
			}
			
			
			for (Note note : converted_notes) {
				System.out.println(note.note + " : " + note.length_tick);
			}
			
			
			converted_voices.add(new Voice(converted_notes));
			
			
		}
		//Convert ticks to miliseconds and convert notes names to frequency numbers
		for (Voice voice : converted_voices) {
			for (Note note : voice.raw_notes) {
				note.length_miliseconds = Math.round(note.length_tick * length_of_tick);
				note.frequency = SUPPORTED_NOTES.indexOf(note.note);
				System.out.println(note.note+ " frequency: " + note.frequency);
			}
		}
		
	}
	
	public File make_text_file (String song_name) {
		File file = new File("res/text/" + song_name + ".txt");
		try {
			System.out.println("trying to make file");
			FileWriter writer = new FileWriter(file);
			String data = "[:phoneme on][";
			String noise = "laa";
			for (Note note : converted_voices.get(0).raw_notes) {
				data = data + noise + "<" + note.length_miliseconds + "," + note.frequency + ">";
			}
			data = data + "]";
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new File("res/text/" + song_name + ".txt");
	}
}
