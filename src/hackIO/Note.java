package hackIO;

public class Note {
	
	//MIDI Information
	public int channel;
	public long tick;
	public String note;
	public int velocity;
	
	//DecTalk Information
	public int frequency;
	public long length_tick;
	public long length_miliseconds;
	
	
	public Note (String note, int velocity, long tick, int channel) {
		this.note = note;
		this.tick = tick;
		this.channel = channel;
		this.velocity = velocity;
		
		
	}

}
