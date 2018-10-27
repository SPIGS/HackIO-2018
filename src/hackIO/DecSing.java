package hackIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<Long, Note> raw_notes = new HashMap<Long, Note>(); 
	public ArrayList<Voice> voices = new ArrayList<Voice>();
	
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
		int trackNumber = 0;
		long tick;
		for (Track track : sequence.getTracks()) {
			trackNumber++;
			System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
			for (int i = 0; i < track.size()/10; i++) {
				MidiEvent event = track.get(i);
				tick = event.getTick();
				System.out.print("@" + event.getTick() + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage)message;
					System.out.print("Channel: " + sm.getChannel() + " ");
					if (sm.getCommand() == NOTE_ON) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        
                        raw_notes.put(tick, new Note(noteName + octave+key, velocity));
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
					} else if (sm.getCommand() == NOTE_OFF) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
					}
				}
			}
			if (!raw_notes.isEmpty()) {
				voices.add(new Voice(raw_notes));
				System.out.println(voices.get(0));
			}
			 System.out.println();
		
		}
		
	}
}
