package hackIO;

import java.util.ArrayList;
import java.util.HashMap;


public class Voice {
	public ArrayList<Note> raw_notes;
	private HashMap<Long, Note> ordered_notes;
	
	public Voice (ArrayList<Note> notes) {
		raw_notes = notes;
	}	
	//TODO getter and setter for params
	
	public HashMap<Long, Note> get_ordered_notes(){
		return ordered_notes;
	}
	
	public void set_ordered_notes(HashMap<Long, Note> notes) {
		this.ordered_notes = notes;
	}
}
