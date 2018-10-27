package hackIO;

public class Note {
	
	//MIDI Information
	public int channel;
	public long tick;
	public String note;
	
	//DecTalk Information
	public int frequency;
	public long length_tick;
	public long length_miliseconds;
	
	
	public Note (String note, long tick, int channel) {
		this.note = note;
		this.tick = tick;
		this.channel = channel;
		
	}

}
