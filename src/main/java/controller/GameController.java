package controller;

import model.board.Board;
import model.board.Field;
import model.player.Player;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

import java.util.Scanner;


public class GameController {

    private ViewControllerType viewcon ;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;
    private GameLogic gamelogic;
    private static GameController singletonInstance = null;
    private Board board;

    private boolean test = false;



    public static GameController getInstance(){

        if(singletonInstance ==null){

            singletonInstance = new GameController();
        }
        return singletonInstance;

    }

    private GameController(){
        logicCollection = LogicStringCollection.getInstance();
        languageCollection = LanguageStringCollection.getInstance("/danish");
        gamelogic = new GameLogic(4);
        board = new Board();
        setupGame();
    }

    private void setupGame(){

        gamelogic.createPlayerList(4);
        for (int i = 0; i <gamelogic.getAllPlayers().length ; i++) {
            addPlayer("test"+i,i);
        }
    }

    public void playGame(){

        Scanner scan = new Scanner(System.in);

        //setupGame();
        while(!checkIfAllBroke()){
            Player currPlayer = gamelogic.getCurrentPlayer();

            gamelogic.rollDice(currPlayer);
            gamelogic.movePlayer(currPlayer,currPlayer.getPosition(), gamelogic.getValue(),board.getFields().length);
            gamelogic.setNextPlayer();

        }

        checkForWinner();

    }

    private boolean checkIfAllBroke(){
        boolean allBroke=false;
        int counter=0;
        Player[] playerlist = gamelogic.getAllPlayers();
        for (int i = 0; i < playerlist.length; i++) {
            if (!playerlist[i].getBrokeStatus()) {

            } else {
                counter+=1;
            }
        }
        if(counter >=gamelogic.getAllPlayers().length-1){
            allBroke=true;
        }
        return allBroke;
    }

    void createBoard(){
        this.board = new Board();
        int[][] fieldLogic = logicCollection.getFieldsText();
        //String[] fieldMessages = languageCollection.getFieldMessages();


        String[][] fieldInfo = languageCollection.getFieldsText();
        this.board.setupBoard(fieldLogic, fieldInfo);
    }

    public void addPlayer(String name, int index){
        Player player = new Player(name);
        gamelogic.addPlayer(index, player);
    }

    public void changePlayerBalance(Player player, int amount){
        player.addToBalance(amount);
    }

    private Player getPlayerByName(String playerName){
        Player player = null;
        for (int i = 0; i <gamelogic.getAllPlayers().length ; i++) {
            if(gamelogic.getPlayer(i).getName().equals(playerName)){
                player =gamelogic.getPlayer(i);
            }
        }
        return player;
    }

    public Player[] getPlayers() { return gamelogic.getAllPlayers(); }

    public Field[] getBoard(){return board.getFields();}

    public void GodMode(boolean mode){
        if(this.test){
            this.viewcon = ViewControllerStub.getSingleInstance();
        }
        else{
            this.viewcon = ViewController.getSingleInstance();
        }
    }

    public void checkForWinner(){

        String winner = "";
        for (int i = 0; i <gamelogic.getAllPlayers().length ; i++) {
            if(gamelogic.getPlayer(i).getBrokeStatus()==false){
                winner = getPlayers()[i].getName();
            }
        }
        System.out.println(winner+" is the winner!");
    }

    public GameLogic getGameLogic(){
        return gamelogic;
    }
}



