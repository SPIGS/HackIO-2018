package hackIO;

import java.util.ArrayList;
import java.util.HashMap;

public class Converter {
	private ArrayList<Voice> raw_voices;
	
	public Converter (ArrayList<Voice> raw_voices) {
		this.raw_voices = raw_voices;
		
	}
	
	public void convert () {
		HashMap<Long, Note> ordered_notes = new HashMap<Long, Note>();
		for (Voice voice : raw_voices) {
			for (Note note: voice.raw_notes) {
				ordered_notes.put(note.tick, note);
			}
		}
	}
}
