package hackIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.MetaMessage;


public class DecSing {
	public static final int NOTE_ON = 144;
	public static final int NOTE_OFF = 128;
	public static final String [] NOTE_NAMES = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	
	public static ArrayList<Channel> channels = new ArrayList<Channel>();
	public int highest_channel = 0;
	public float BPM;
	public float PPQ;
	
	public DecSing (float BPM, float PPQ) {
		this.BPM = BPM;
		this.PPQ = PPQ;
	}

	public static void main(String[] args) throws Exception {
		//TODO make midi input part of the program arguments
		Sequence sequence = MidiSystem.getSequence(new File("res/midi/furelise.mid"));
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.setSequence(sequence);
		
		float BPM = sequencer.getTempoInBPM();
		float PPQ = sequencer.getTempoInMPQ()/1000;
		
		DecSing decsing = new DecSing (BPM, PPQ);
		decsing.getMidiInfo(sequence);
		
		Converter converter = new Converter(channels);
	}
	
	public void getMidiInfo (Sequence sequence) {
		ArrayList<Note> raw_notes = new ArrayList<Note>(); 
		
		
		int key;
		int octave;
		int note;
		int velocity;
		int trackNumber = 0;
		long tick;
		
		//For each track in the midi file:
		for (Track track : sequence.getTracks()) {
			trackNumber++;
			
            //For the length of the track:
			// only one tenth of the song to save time testing
			for (int i = 0; i < track.size()/10; i++) {
				
				//Get the events of the track and the tick they occur at
				MidiEvent event = track.get(i);
				tick = event.getTick();
				System.out.print("@" + tick + " ");
				MidiMessage message = event.getMessage();
				
				//if the message in the event is a shortmessage
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage)message;
					
					//get the channel
					int channel = sm.getChannel();
					
					if (channel > highest_channel) {
						highest_channel = channel;
					}
					
					//if the command of the short message is note_on
					if (sm.getCommand() == NOTE_ON) {
						
						key = sm.getData1();
						octave = (key / 12) - 1;
						note = key % 12;
						String noteName = NOTE_NAMES[note];
						velocity = sm.getData2();
						Note raw_note = new Note(noteName + octave, velocity,tick, channel);
						
						//If the note has volume
						if (sm.getData2() > 0) {
							
							raw_notes.add(raw_note);
							System.out.println("Channel: " + channel + " Note on, " + noteName + octave + " key=" + key);
							
						//If the note is silent.	
						} else if (sm.getData2() == 0) {
							System.out.println("Channel: " + channel + " Note off, " + noteName + octave);
						}
                        
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
	            } else if (message instanceof MetaMessage) {
	            	MetaMessage mm = (MetaMessage)message;
	            	
	            	System.out.println("MetaMessage: " + mm.getType());
	            	
	            }
			}
		}
		System.out.println("Amount of notes: " + raw_notes.size());
		System.out.println("Highest channel " + highest_channel);
		
		//create the correct number of channel lists
		for (int i = 0 ; i <=highest_channel; i++) {
			channels.add(new Channel(new ArrayList<Note>(), i));
		}
		
		//add the correct notes to their corresponding channel list
		for (Note raw_note : raw_notes) {
			for (Channel channel : channels) {
				if (raw_note.channel == channel.number) {
					channel.notes.add(raw_note);
				}
			}
		}
		get_note_lengths();
	}
	
	private void get_note_lengths () {
		for (Channel channel : channels) {
			for (Note note : channel.notes) {
				if (note.velocity == 0) {
					for (int i = channel.notes.size()-1; i >=0; i--) {
						if (note.note.equals(channel.notes.get(i).note)) {
							note.length_tick = channel.notes.get(i).tick - note.tick;
							channel.notes.remove(note);
							channel.notes.get(i).set_length_miliseconds(BPM, PPQ);
							break;
						}
					}
				}
			}
		}
	}
}
