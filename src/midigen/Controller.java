package midigen;

import javafx.event.ActionEvent;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    Player player = new Player();


    public void play(ActionEvent actionEvent) {

            player.play();
    }


    public void newSequence(ActionEvent actionEvent) {
        player.newSequence();
        player.play();
    }
}
