package hackIO;

public class Note {
	//used for conversion
	public String note;
	public long tick;
	public int velocity;
	//put into dectalk
	public int frequency;
	
	
	public Note (String note, int velocity, long tick) {
		this.note = note;
		this.velocity = velocity;
		this.tick = tick;
	}

}
