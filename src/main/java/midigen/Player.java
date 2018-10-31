package midigen;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Player {
    private static final List<Integer> majorScaleOffsets = List.of(0, 2, 4, 5, 7, 9, 11, 12).stream().map(note -> note + 60).collect(Collectors.toList());

    private MidiDevice device = null;
    private List<Tone> sequence;

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

    static List<String> listAvailableDevices() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        return Arrays.stream(infos).map(it -> it.getName() + ": " + it.getDescription()).collect(Collectors.toList());
    }

    public void randomSequence() {
        var newSeq = new ArrayList<Tone>();

        for (var i = 0; i < 3; i++) {
            newSeq.add(new Tone(majorScaleOffsets.get(ThreadLocalRandom.current().nextInt(5)), 500));
        }

        sequence = newSeq;
    }

    public void setScale(int i, int time) {
        sequence = majorScaleOffsets.stream()
                .map(note -> new Tone(note + i, time))
                .collect(Collectors.toList());
    }

    public void play() {
        System.out.println("Playing: " + sequence);
        for (var note : sequence) {
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

    private void play(Tone tone) {
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

}
