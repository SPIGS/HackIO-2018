package hackIO;

import java.util.ArrayList;
import java.util.HashMap;


public class Voice {
	public ArrayList<Note> raw_notes;
	public HashMap<Long, Note> ordered_notes;
	
	public Voice (ArrayList<Note> notes) {
		raw_notes = notes;
	}	
	
}
