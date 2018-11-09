package midigen;

public class Note {
    private final Integer pitch;
    private final int time;

    public Note(Integer pitch, int time) {
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
        return "Note{" +
                "pitch=" + pitch +
                '}';
    }
}
