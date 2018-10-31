package rest;

import midigen.MidiDeviceBean;
import midigen.MidiService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
public class MidiController {



    @RequestMapping("/play")
    public String greeting() {
        var midiService = MidiService.instance();
        midiService.newSequence();
        return "Played";
    }

    @RequestMapping("/play/scale")
    public String greeting(@RequestParam(value="offset", defaultValue = "0") Integer pitchOffset) {
        var midiService = MidiService.instance();
        midiService.play(pitchOffset);
        return "Played";
    }

    @RequestMapping("/set")
    public String setInstrument(@RequestParam(value="instrument", defaultValue = "1") Integer instrument) {
        MidiService.instance().setInstrument(instrument);
        return "Ok";
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
