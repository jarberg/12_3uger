package controller;

import model.misc.DieSet;
import model.board.Board;
import model.board.Field;
import model.player.Player;
import model.player.PlayerList;
import model.text.FileReader;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

import java.awt.*;
import java.util.Scanner;


public class GameController {

    ViewController viewController;
    //ViewControllerType viewCon ;
    private FileReader fileReader;
    private GameLogic gameLogic;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;
    private String language;
    private int playerAmount;

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
        this.viewController = ViewController.getSingleInstance();

    }


    private DieSet dice = new DieSet();

    public void setupGame(){
        setupLanguage();
        createBoard();
        this.playerAmount = getPlayerAmount();
        createPlayers();
        //makePlayerChooseCar();
        showGameBoard();
    }

    private void makePlayerChooseCar() {
        for (int i = 0; i < playerAmount; i++) {
            Player playerChoosingColor = gameLogic.getPlayer(i);
            for (Player player : gameLogic.getAllPlayers()){
                if (playerChoosingColor.getPlayerColor() != Color.black)
                    playerChoosingColor = player;
            }
            Color chosenColor = viewController.getUserColor(playerChoosingColor.getName());
            playerChoosingColor.setPlayerColor(chosenColor);
            gameLogic.setNextPlayer();
        }
    }

    private void createPlayers() {
        this.gameLogic = new GameLogic(playerAmount);
        for (int i = 0; i < playerAmount; i++) {
            //String name = viewController.getPlayerName();
            //int age = viewController.getPlayerAge();

            String name = "name " + i;


            String playerName = name;
            /*
            int playerIdentifier = 2;
            while(this.gameLogic.hasPlayerWithName(playerName)){
                playerName = name + "#" + playerIdentifier;
                playerIdentifier++;
            }*/
            gameLogic.addPlayer(i, new Player(playerName));
        }
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
        return playerAmount;
    }

    private void setupLanguage(){
        viewController.showEmptyGUI();
        String userLanguage = viewController.getUserLanguage();
        setFilepathLanguage(userLanguage);
    }

    private void setFilepathLanguage(String language) {
        this.language = language;
        viewController.setFilepath(this.language);
    }

    public void createBoard(){
        this.board = new Board();
        logicCollection = LogicStringCollection.getInstance();
        languageCollection = LanguageStringCollection.getInstance(language);
        int[][] fieldLogic = logicCollection.getFieldsText();
        String[][] fieldInfo = languageCollection.getFieldsText();
        this.board.setupBoard(fieldLogic, fieldInfo);
    }

    public void showGameBoard(){ ;
        viewController.showGameGUI(board.getFields());
    }

    public void playGame(){
        Scanner scan = new Scanner(System.in);

        setupGame();
        while(!checkIfAllBroke()){

            System.out.println(getCurrentPlayerTurn().getName()+"'s turn");
            boolean input = scan.nextBoolean();
            getCurrentPlayerTurn().setBrokeStatus(input);
            nextPlayerTurn();
        }
        checkForWinner();
    }

    public boolean checkIfAllBroke(){
        boolean allBroke=false;
        int counter=0;

        for (Player player : gameLogic.getAllPlayers()){
            if (player.getBrokeStatus())
                counter++;
        }

        if(counter >=gameLogic.getAllPlayers().length-1){
            allBroke=true;
        }
        return allBroke;
    }

    public void createPlayerList(int amount){
        playerlist = new PlayerList(amount);

    }

    public void addPlayer(String name, int index){
        Player player = new Player(name);
        playerlist.addPlayer(index, player);
    }

    public void changePlayerBalance(Player player, int amount){
        player.addToBalance(amount);
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



    public Player[] getPlayers() { return gameLogic.getAllPlayers(); }

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
        return gameLogic.getCurrentPlayer();
    }

    public void nextPlayerTurn(){
        gameLogic.setNextPlayer();
    }
    public void checkForWinner(){
        String winner="";
        for (int i = 0; i <gameLogic.getAllPlayers().length ; i++) {
            if (!gameLogic.getPlayer(i).getBrokeStatus())
                winner = getPlayers()[i].getName();
        }
        System.out.println(winner+" is the winner!");
    }
}
