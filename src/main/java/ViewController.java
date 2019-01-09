import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class ViewController {

    private static final ViewController singleInstance = new ViewController();
    private GUI gui;
    private GUI_Field[] gui_board;
    private GUI_Player[] gui_players;

    private ViewController(){
        this.gui_board = new GUI_Field[40];
    }

    public static ViewController getSingleInstance(){
        return singleInstance;
    }

    public void showEmptyGUI(){
        this.gui = new GUI(new GUI_Field[0]);
    }

    public void closeGUI(){
        this.gui.close();
    }

    public void addPlayer(String name, int balance){
        int length = gui_players.length;
        GUI_Player[] temp = new GUI_Player[length+1];
        System.arraycopy(gui_players, 0, temp, 0, length);
        gui_players = temp;
        gui_players[length] = new GUI_Player(name, balance);
    }


}
