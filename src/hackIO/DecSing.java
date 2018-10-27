package hackIO;
import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class DecSing {
	public static final int NOTE_ON = 144;
	public static final int NOTE_OFF = 128;
	public static final String [] NOTE_NAMES = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	private ArrayList<Note> raw_notes = new ArrayList<Note>(); 
	private ArrayList<Voice> raw_voices = new ArrayList<Voice>();
	
	public DecSing () {
		
	}

	public static void main(String[] args) throws Exception {
		//TODO make midi input part of the program arguments
		Sequence sequence = MidiSystem.getSequence(new File("res/furelise.mid"));
		System.out.println("Midi loaded!");
		DecSing decsing = new DecSing ();
		decsing.getMidiInfo(sequence);
		System.out.println(decsing.raw_notes);
	}
	
	public void getMidiInfo (Sequence sequence) {
		int key;
		int octave;
		int note;
		int velocity;
		int trackNumber = 0;
		long tick;
		
		for (Track track : sequence.getTracks()) {
			trackNumber++;
			//System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
			for (int i = 0; i < track.size()/10; i++) {
				MidiEvent event = track.get(i);
				tick = event.getTick();
				//System.out.print("@" + event.getTick() + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage)message;
					System.out.print("Channel: " + sm.getChannel() + " ");
					if (sm.getCommand() == NOTE_ON) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        velocity = sm.getData2();
                        
                        raw_notes.add(new Note(noteName + octave + key, velocity, tick));
                        //System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
					} else if (sm.getCommand() == NOTE_OFF) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        velocity = sm.getData2();
                        //System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
					}
				}
			}
			if (!raw_notes.isEmpty()) {
				raw_voices.add(new Voice(raw_notes));
				System.out.println(raw_voices.get(0).notes.get(0).note);
			}
			 System.out.println();
		
		}
		
	}
}
