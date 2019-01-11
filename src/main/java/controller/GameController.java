package controller;

import model.misc.DieSet;
import model.board.Board;
import model.board.Field;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

import java.util.Scanner;


public class GameController {

    ViewController viewcon ;
    //ViewControllerType viewCon ;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;
    private GameLogic gamelogic;

    private boolean test = false;
    private static GameController singletonInstance = null;
    private Board board;


    public static GameController getInstance(){

        if(singletonInstance ==null){

            singletonInstance = new GameController();
        }
        return singletonInstance;

    }

    private GameController(){
        //viewcon = ViewController.getSingleInstance();
        logicCollection = LogicStringCollection.getInstance();
        languageCollection = LanguageStringCollection.getInstance("/danish");
        gamelogic = new GameLogic(4);
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

            System.out.println(gamelogic.getCurrentPlayer().getName()+"'s turn");

            //boolean input = scan.nextBoolean();

            gamelogic.rollDice(currPlayer);
            System.out.println(gamelogic.getValue());

            gamelogic.movePlayer(currPlayer,currPlayer.getPosition(), gamelogic.getValue(),board.getFields().length);

            System.out.println(currPlayer.getName()+" is now on "+currPlayer.getPosition());

            gamelogic.setNextPlayer();
        }

        checkForWinner();

    }

    private boolean checkIfAllBroke(){
        boolean allBroke=false;
        int counter=0;

            for (int i = 0; i < gamelogic.getAllPlayers().length; i++) {
                if (!gamelogic.getAllPlayers()[i].getBrokeStatus()) {

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
        this.test = mode;
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
}
