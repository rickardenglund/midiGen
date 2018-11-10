package midigen;

import javax.sound.midi.MidiDevice;

public class MidiDeviceBean {
    private final String name;
    private final String description;
    private final String vendor;

    public MidiDeviceBean(MidiDevice.Info midiDevice) {
        name = midiDevice.getName();
        description = midiDevice.getDescription();
        vendor = midiDevice.getVendor();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }
}
