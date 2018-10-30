package midigen;

import javax.sound.midi.*;
import java.util.ArrayList;
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

    public void randomSequence() {
        var newSeq = new ArrayList<Tone>();

        for (var i = 0; i < 3; i++) {
            newSeq.add(new Tone(majorScaleOffsets.get(ThreadLocalRandom.current().nextInt(5)), 50));
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

    private void play(Tone tone) {
        ShortMessage myMsg = new ShortMessage();
        try {
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, tone.getPitch(), 100);

            long timeStamp = -1;
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(myMsg, timeStamp);
            Thread.sleep(tone.getTime());

            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, tone.getPitch(), 100);
            receiver.send(myMsg, timeStamp);
        } catch (InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

}
