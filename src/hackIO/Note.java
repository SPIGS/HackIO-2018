package hackIO;

public class Note {
	
	//MIDI Information
	public int channel;
	public long tick;
	public String note;
	public int velocity;
	
	private float BPM;
	private float PPQ;
	
	//DecTalk Information
	public int frequency;
	public long length_tick;
	private long length_miliseconds;
	
	
	public Note (String note, int velocity, long tick, int channel) {
		this.note = note;
		this.tick = tick;
		this.channel = channel;
		this.velocity = velocity;
	}
	
	public long get_length_miliseconds () {
		return length_miliseconds;
	}
	public void set_length_miliseconds (float BPM, float PPQ) {
		length_miliseconds = length_tick * (long)(60000/(BPM * PPQ));
	}

}
