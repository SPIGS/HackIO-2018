package hackIO;

import java.util.ArrayList;

public class Channel {
	public ArrayList<Note> notes;
	public int number;
	
	public Channel (ArrayList<Note> notes, int number) {
		this.notes = notes;
		this.number = number;
	}
}
