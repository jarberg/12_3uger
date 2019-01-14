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

public class GameController {

    ViewController viewController;
    private GameLogic gameLogic;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;
    private String language;
    private int playerAmount;
    private boolean test = false;
    private static GameController singletonInstance = null;
    private Board board;
    private int boardLength = 40;


    public static GameController getInstance(){

        if(singletonInstance ==null){
            singletonInstance = new GameController();
        }
        return singletonInstance;

    }

    private GameController(){
        this.viewController = ViewController.getSingleInstance();

    }

    public void playGame(){
        setupGame();
        viewController.showFieldMessage(gameLogic.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);
        while(!gameLogic.checkIfAllBroke()){
            playTurn();
        }
        checkForWinner();
    }

    public void playTurn(){
        Player currentPlayer = gameLogic.getCurrentPlayer();
        rollAndShowDice(currentPlayer);
        int lastField = currentPlayer.getPosition();
        viewController.movePlayer(currentPlayer.getName(), lastField, gameLogic.getSumOfDice());
        gameLogic.movePlayer(currentPlayer, lastField, gameLogic.getSumOfDice(),boardLength);
        int position = currentPlayer.getPosition();

        Field currentField = board.getFields()[position];

        nextPlayerTurn();

    }

    public void rollAndShowDice(Player curPlayer){
        gameLogic.rollDice(curPlayer);
        int dieOneValue = gameLogic.getDieOneValue();
        int dieTwoValue = gameLogic.getDieTwoValue();
        viewController.showDice(dieOneValue, dieTwoValue);
    }

    public void setupGame(){
        setupLanguage();
        createBoard();
        this.playerAmount = getPlayerAmount();
        createPlayers();
        makePlayerChooseCar();
        showGameBoard();
        addPlayersToGUI();
    }

    protected void setupLanguage(){
        if(!test) {
            viewController.showEmptyGUI();
            String userLanguage = viewController.getUserLanguage();
            setFilepathLanguage(userLanguage);
            this.logicCollection = LogicStringCollection.getInstance();
            this.languageCollection = LanguageStringCollection.getInstance(language);
        }
        else{
            language = "danish";
        }
    }

    private void setFilepathLanguage(String language) {
        this.language = language;
        viewController.setFilepath(this.language);
    }

    public void createBoard(){
        this.board = new Board();
        int[][] fieldLogic = logicCollection.getFieldsText();
        String[][] fieldInfo = languageCollection.getFieldsText();
        this.board.setupBoard(fieldLogic, fieldInfo);
    }

    protected void createPlayers() {
        this.gameLogic = new GameLogic(playerAmount);
        for (int i = 0; i < playerAmount; i++) {
            String name = viewController.getPlayerName();
            //String name = "name"; // gider ikke skrive navne ind hver gang programmet skal køres
            String playerName = name;
            int playerIdentifier = 2;
            while(this.gameLogic.hasPlayerWithName(playerName)){
                playerName = name + "#" + playerIdentifier;
                playerIdentifier++;
            }
            gameLogic.addPlayer(i, new Player(playerName));
        }
    }

    private void makePlayerChooseCar() {
        for (Player player : gameLogic.getAllPlayers()){
            Color chosenColor = viewController.getUserColor(player.getName());
            player.setPlayerColor(chosenColor);
        }
    }

    private void showGameBoard(){
        viewController.showGameGUI(board.getFields());
    }

    private void addPlayersToGUI() {
        int startAmout = 1500;
        for (Player player : gameLogic.getAllPlayers()){
            player.addToBalance(startAmout);
            viewController.addPlayer(player.getName(), player.getPlayerColor(), startAmout);
            viewController.spawnPlayers();
            viewController.showPlayerScores();

        }
    }

    public void nextPlayerTurn(){
        gameLogic.setNextPlayer();
    }

    public void checkForWinner(){
        String winner="";
        for (int i = 0; i <gameLogic.getAllPlayers().length ; i++) {
            if (!gameLogic.getPlayer(i).getBrokeStatus())
                winner = gameLogic.getAllPlayers()[i].getName();
        }
        System.out.println(winner+" is the winner!");
    }

    public void addPlayer(String name, int index){
        Player player = new Player(name);
        gameLogic.addPlayer(index, player);
    }

    public void changePlayerBalance(Player player, int amount){
        player.addToBalance(amount);
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
        return playerAmount;
    }

    private Player getPlayerByName(String playerName){
       Player player = null;
        for (int i = 0; i <gameLogic.getAllPlayers().length ; i++) {
            if(gameLogic.getPlayer(i).getName().equals(playerName)){
                player =gameLogic.getPlayer(i);
            }
        }
        return player;
    }

    public Field[] getBoard(){return board.getFields();}

    public void GodMode(boolean mode){
        this.test = mode;
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }

    public void setPlayerAmount(int amount){this.playerAmount = amount;}

}
