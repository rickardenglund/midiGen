package midigen;

public class Tone {
    private final Integer pitch;
    private final int time;

    public Tone(Integer pitch, int time) {
        this.pitch = pitch;
        this.time = time;
    }

    public Integer getPitch() {
        return pitch;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Tone{" +
                "pitch=" + pitch +
                '}';
    }
}
