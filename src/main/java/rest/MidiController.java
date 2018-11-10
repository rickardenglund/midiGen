package rest;

import midigen.MidiDeviceBean;
import midigen.MidiService;
import midigen.Note;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MidiController {

    @RequestMapping("/new")
    public List<Note> newSequence() {
        MidiService midiService = MidiService.instance();
        midiService.newSequence();
        midiService.play();
        return midiService.getNotes();
    }

    @RequestMapping("/play")
    public List<Note> greeting() {
        MidiService midiService = MidiService.instance();
        midiService.play();
        return midiService.getNotes();
    }

    @RequestMapping("/play/scale")
    public List<Note> greeting(@RequestParam(value="offset", defaultValue = "0") Integer pitchOffset) {
        MidiService midiService = MidiService.instance();
        midiService.playScale(pitchOffset);
        return midiService.getNotes();
    }

    @RequestMapping("/set")
    public String setInstrument(@RequestParam(value="instrument", defaultValue = "1") Integer instrument) {
        MidiService.instance().setInstrument(instrument);
        return "Instrument set to : " + instrument;
    }

    @RequestMapping("/set/device")
    public MidiDeviceBean setMidiDevice(@RequestParam(value="id", defaultValue = "0") Integer deviceId) {
        MidiService.instance().setMidiDevice(deviceId);
        return MidiService.instance().getActiveMidiDevice();
    }

    @RequestMapping("/list")
    public List<MidiDeviceBean> list() {
        return MidiService.listMidiDevices();
    }

    @RequestMapping("/get/midiDevice")
    public MidiDeviceBean getMidiDevice() {
        return MidiService.instance().getActiveMidiDevice();
    }

}
