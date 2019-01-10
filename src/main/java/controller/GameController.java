package controller;
import model.Board.Board;
import model.Board.Field;
import model.misc.DieSet;
import model.player.Player;
import model.player.PlayerList;


public class GameController {

    ViewControllerAbs viewcon ;

    private boolean test = false;
    private static GameController singletonInstance = null;

    public static GameController getInstance(){

        if(singletonInstance ==null){

            singletonInstance = new GameController();
        }
        return singletonInstance;

    }
    private GameController(){
        viewcon = ViewController.getSingleInstance();
    }


    private Board board;
    private PlayerList playerlist;
    private DieSet dice = new DieSet();

    public void createBoard(){
        board = new Board();
    }

    public void createPlayerList(int amount){
        playerlist = new PlayerList(amount);
    }

    public void addPlayer(String name, int index){
        Player player = new Player(name);
        playerlist.addPlayer(index, player);
    }

    public void changePlayerBalance(Player player, int amount){
        player.addBalance(amount);
    }

    public void movePlayer(Player player, int position, int amount){
        player.setPosition((position+amount)%board.getFields().length);
    }

    private Player getPlayerByName(String playerName){
       Player player = null;
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            if(playerlist.getPlayer(i).getName().equals(playerName)){
                player =playerlist.getPlayer(i);
            }
        }
        return player;
    }

    public Player[] getPlayers() { return playerlist.getAllPlayers(); }

    public Field[] getBoard(){return board.getFields();}

    public int rollDice(){
        dice.roll();
        return dice.getValue();
    }

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    public void GodMode(boolean mode){
        this.test = mode;
    }

    public Player getCurrentPlayerTurn(){
        return playerlist.getCurrentPlayer();
    }

    public void nextPlayerTurn(){
        playerlist.setNextPlayer();
    }
}
