import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class ViewController {

    private static final ViewController singleInstance = new ViewController();

    private GUI gui;
    private GUI_Field[] gui_board;
    private GUI_Player[] gui_players;

    private ViewController(){
    }

    public ViewController getSingleInstance(){
        return singleInstance;
    }



}
