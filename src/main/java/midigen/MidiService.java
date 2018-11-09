package midigen;

import javax.sound.midi.MidiDevice;
import java.util.List;
import java.util.stream.Collectors;

public class MidiService {
    Player player = new Player();

    private MidiService() {
    }

    private static MidiService instance;

    public static MidiService instance() {
        if (instance == null) {
            instance = new MidiService();
        }
        return instance;
    }

    public void play() {
        player.play();
    }

    public void playScale(int scale) {
        player.setScale(scale, 500);
        player.play();
    }

    public void newSequence() {
        player.randomSequence();
    }

    public void setInstrument(int instrument) {
        player.setInstrument(instrument);
    }

    public MidiDevice.Info setMidiDevice(Integer deviceId) {
        player = new Player(deviceId);
        return player.getMidiDevice();
    }

    public static List<MidiDeviceBean> listMidiDevices() {
        return Player.listAvailableDevices()
                .stream()
                .map(it -> new MidiDeviceBean(it))
                .collect(Collectors.toList());
    }

    public MidiDeviceBean getActiveMidiDevice() {
        return new MidiDeviceBean(player.getMidiDevice());
    }

    public List<Note> getNotes() {
        return player.getSequence();
    }
}
