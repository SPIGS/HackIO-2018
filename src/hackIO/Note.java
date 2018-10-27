package hackIO;

public class Note {
	public String note;
	public long tick;
	public int velocity;
	
	public Note (String note, int velocity, long tick) {
		this.note = note;
		this.velocity = velocity;
		this.tick = tick;
	}

}
