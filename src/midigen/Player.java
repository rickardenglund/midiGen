package midigen;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Player {
    private static final List<Integer> majorScale = List.of(0, 2, 4, 5, 7, 9, 11, 12).stream().map(note -> note + 60).collect(Collectors.toList());

    private MidiDevice device = null;
    private List<Integer> sequence;

    public Player() {
        List<Object> synthInfos = new ArrayList<>();

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }

            if (device instanceof Synthesizer) {
                synthInfos.add(infos[i]);
            }
        }

        if (!(device.isOpen())) {
            try {
                device.open();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        newSequence();
    }

    public void newSequence() {
        var newSeq = new ArrayList<Integer>();

        for (var i = 0; i < 3; i++) {
            newSeq.add(majorScale.get(ThreadLocalRandom.current().nextInt(5)));
        }

        sequence = newSeq;
    }

    public void play() {
        System.out.println("Playing: " + sequence);
        for (var note : sequence) {
            play(note);
        }
    }

    private void play(int frequency) {
        ShortMessage myMsg = new ShortMessage();
        // Start playing the note Middle C (60),
        // moderately loud (velocity = 93).
        try {
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, frequency, 93);

            long timeStamp = -1;
            Receiver rcvr = MidiSystem.getReceiver();
            rcvr.send(myMsg, timeStamp);
            Thread.sleep(250);

            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, frequency, 93);
            rcvr.send(myMsg, timeStamp);
            Thread.sleep(50);
        } catch (InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
