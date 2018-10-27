package hackIO;
import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class DecSing {
	public static final int NOTE_ON = 144;
	public static final int NOTE_OFF = 128;
	public static final String [] NOTE_NAMES = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	
	
	public DecSing () {
		
	}

	public static void main(String[] args) throws Exception {
		//TODO make midi input part of the program arguments
		Sequence sequence = MidiSystem.getSequence(new File("res/midi/furelise.mid"));
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.setSequence(sequence);
		float BPM = sequencer.getTempoInBPM();
		float PPQ = sequencer.getTempoInMPQ()/1000;
		System.out.println("Midi loaded!");
		System.out.println("Tempo in BPM is: " + BPM);
		System.out.println("Tempo in PPQ is: " + PPQ);
		DecSing decsing = new DecSing ();
		Converter converter = new Converter(decsing.getMidiInfo(sequence), (60000/(BPM * PPQ)), "furelise");
		decsing.getMidiInfo(sequence);
	}
	
	public ArrayList<Voice> getMidiInfo (Sequence sequence) {
		ArrayList<Note> raw_notes = new ArrayList<Note>(); 
		ArrayList<Voice> raw_voices = new ArrayList<Voice>();
		
		int key;
		int octave;
		int note;
		int velocity;
		int trackNumber = 0;
		long tick;
		
		//For each track in the midi file:
		for (Track track : sequence.getTracks()) {
			trackNumber++;
			System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            
            //For the length of the track:
			for (int i = 0; i < track.size()/10; i++) {
				//Get the events of the track and the tick they occur at
				MidiEvent event = track.get(i);
				tick = event.getTick();
				System.out.print("@" + event.getTick() + " ");
				MidiMessage message = event.getMessage();
				//if the message in the event is a shortmessage
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage)message;
					//get the channel
					System.out.print("Channel: " + sm.getChannel() + " ");
					
					//if the command of the short message is note_on
					if (sm.getCommand() == NOTE_ON) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        velocity = sm.getData2();
                        
                        raw_notes.add(new Note(noteName + octave, velocity, tick));
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        
                     //if the command of the short message is note_off
					} else if (sm.getCommand() == NOTE_OFF) {
						key = sm.getData1();
                        octave = (key / 12)-1;
                        note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        
					 } else {
						 //this gets other commands
	                        System.out.println("Command:" + sm.getCommand());
	                    }
	                } else {
	                	//worthless - does not contribute to song
	                    System.out.println("Other message: " + message.getClass());
	                }
			}
			if (!raw_notes.isEmpty()) {
				raw_voices.add(new Voice(raw_notes));
				//System.out.println(raw_voices.get(0).notes.get(0).note);
			}
			 //System.out.println();
		}
		
		
		return raw_voices;
	}
}
