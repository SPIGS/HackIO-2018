package hackIO;
import java.io.File;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class DecSing {
	
	public DecSing () {
		
	}

	public static void main(String[] args) throws Exception {
		//TODO make midi input part of the program arguments
		Sequence sequence = MidiSystem.getSequence(new File("res/test.mid"));
	}

}
