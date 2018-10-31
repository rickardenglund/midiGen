package midigen;

import java.util.List;

public class MidiService {
    Player player = new Player();

    private MidiService() {}

    private static MidiService instance;
    public static MidiService instance() {
        if (instance == null) {
            instance = new MidiService();
        }
        return instance;
    }

    public void play(int scale) {
        player.setScale(scale, 500);
        player.play();
    }

    public void newSequence() {
        player.randomSequence();
        player.play();
    }

    public void setInstrument(int instrument) {
        player.setInstrument(instrument);
    }

    public List<String> listMidiDevices() {
        return Player.listAvailableDevices();
    }
}
