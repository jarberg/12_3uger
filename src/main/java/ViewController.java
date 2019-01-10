import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
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

    public void createBoard(){
        for (int i = 0; i < gui_board.length; i++) {
            gui_board[i] = new GUI_Street();
        }
        showGUI();
    }

    private void showGUI(){
        if(this.gui != null)
            gui.close();

        this.gui = new GUI(gui_board);
    }

    public void addPlayer(String name, int balance){
        int length;
        try{
            length = gui_players.length;
        } catch (NullPointerException e){
            length = 0;
        }
        GUI_Player[] temp = new GUI_Player[length+1];
        for (int i = 0; i < length; i++) {
            temp[i] = gui_players[i];
        }
        gui_players = temp;
        GUI_Player newPlayer = new GUI_Player(name, balance);

        gui_players[length] = newPlayer;
        this.gui.addPlayer(newPlayer);
    }

    public void changePlayerBalance(String playerName, int amount){
        GUI_Player player = getPlayerByName(playerName);
        int currentBalance = player.getBalance();
        player.setBalance(currentBalance + amount);
    }

    public void spawnPlayers(){
        if(gui_board[0] != null){
            for(GUI_Player player : gui_players){
                gui_board[0].setCar(player, true);
            }
        }
    }

    public void movePlayer(String playerName, int position, int amount){
        GUI_Player movingPlayer = getPlayerByName(playerName);
        for (int i = 0; i < amount; i++) {
            gui_board[position].setCar(movingPlayer, false);
            position++;
            position = position % gui_board.length;
            gui_board[position].setCar(movingPlayer, true);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private GUI_Player getPlayerByName(String playerName){
        GUI_Player player = null;
        for(GUI_Player p : gui_players){
            if(p.getName().equals(playerName))
                player = p;
        }
        return player;
    }

    public GUI_Player[] getGui_players() {
        return gui_players;
    }
}
