package midigen;

public class Controller {
    Player player = new Player();


    public void play() {
        player.setScale(0, 500);
        player.play();
    }

    public void newSequence() {
        player.randomSequence();
        player.play();
    }
}
