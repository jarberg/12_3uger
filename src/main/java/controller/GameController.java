package Controller;
import model.Board.Board;
import model.Board.Field;
import model.player.Player;
import model.player.PlayerList;
import model.text.FReader;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;


public class GameController {

    //ViewControllerAbs viewcon ;
    private FReader fileReader;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;

    private boolean test = false;
    private static GameController singletonInstance = null;
    private Board board;
    private PlayerList playerlist;

    public static GameController getInstance(){

        if(singletonInstance ==null){

            singletonInstance = new GameController();
        }
        return singletonInstance;

    }
    private GameController(){
        //viewcon = ViewController.getSingleInstance();
        logicCollection = LogicStringCollection.getInstance("logic");
        //languageCollection = LanguageStringCollection.getInstance("language/danish");
    }


    public void createBoard(){
        this.board = new Board();
        int[][] fieldLogic = logicCollection.getFieldsText();
        //String[] fieldDescriptions = languageCollection.getFieldDescription();
        //String[] fieldMessages = languageCollection.getFieldMessages();
        String[] fieldDescriptions = null;
        String[] fieldMessages = null;
        this.board.setupBoard(fieldLogic, fieldDescriptions, fieldMessages);
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

    public void GodMode(boolean mode){
        this.test = mode;
    }
}
