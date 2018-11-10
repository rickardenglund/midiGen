package midigen;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Player {
    private static List<Integer> majorScaleOffsets = new ArrayList<>();
    static {
        majorScaleOffsets.add(0);
        majorScaleOffsets.add(2);
        majorScaleOffsets.add(4);
        majorScaleOffsets.add(5);
        majorScaleOffsets.add(7);
        majorScaleOffsets.add(9);
        majorScaleOffsets.add(11);
        majorScaleOffsets.add(12);
        majorScaleOffsets = majorScaleOffsets.stream().map(note -> note + 60).collect(Collectors.toList());
    }

    private MidiDevice device = null;
    private List<Note> sequence;

    public Player(int deviceIndex) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        try {
            device = MidiSystem.getMidiDevice(infos[deviceIndex]);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        if (!(device.isOpen())) {
            System.out.println("opening: " + device.getDeviceInfo().getDescription());

            try {
                device.open();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        randomSequence();
    }

    public Player() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
            if (device instanceof Synthesizer) {
                break;
            }
        }


        if (!(device.isOpen())) {
            System.out.println("opening: " + device.getDeviceInfo().getDescription());

            try {
                device.open();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        randomSequence();
    }

    static List<MidiDevice.Info> listAvailableDevices() {
        return Arrays.asList(MidiSystem.getMidiDeviceInfo());
    }

    public void randomSequence() {
        ArrayList<Note> newSeq = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            newSeq.add(new Note(majorScaleOffsets.get(ThreadLocalRandom.current().nextInt(5)), 500));
        }

        sequence = newSeq;
    }

    public void setScale(int i, int time) {
        sequence = majorScaleOffsets.stream()
                .map(note -> new Note(note + i, time))
                .collect(Collectors.toList());
    }

    public void play() {
        System.out.println("Playing: " + device.getDeviceInfo().getName() + ": " + sequence);
        for (Note note : sequence) {
            play(note);
        }
    }


    void setInstrument(int instrument) {
        System.out.println("Setting instrument: " + instrument);
        ShortMessage myMsg = new ShortMessage();
        long timeStamp = -1;
        int channel = 0;
        try {
            Receiver receiver = MidiSystem.getReceiver();
            myMsg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
            receiver.send(myMsg, timeStamp);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void play(Note tone) {
        ShortMessage myMsg = new ShortMessage();
        int channel = 0;
        try {
            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();


            myMsg.setMessage(ShortMessage.NOTE_ON, channel, tone.getPitch(), 93);
            receiver.send(myMsg, timeStamp);

            Thread.sleep(tone.getTime());

            myMsg.setMessage(ShortMessage.NOTE_OFF, channel, tone.getPitch(), 93);
            receiver.send(myMsg, timeStamp);
        } catch (InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public MidiDevice.Info getMidiDevice() {
        return device.getDeviceInfo();
    }

    public List<Note> getSequence() {
        return sequence;
    }
}
