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
    private DieSet dice;

    public static GameController getInstance(){

        if(singletonInstance ==null){
            singletonInstance = new GameController();
        }
        return singletonInstance;

    }
    private GameController(){
        this.viewController = ViewController.getSingleInstance();
        dice = new DieSet();
    }

    public void playGame(){
        setupGame();
        viewController.showFieldMessage(gameLogic.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);
        while(!checkIfAllBroke()){
            playTurn();
        }
        checkForWinner();
    }

    public void playTurn(){
        Player currentPlayer = gameLogic.getCurrentPlayer();
        rollAndShowDice(currentPlayer);
        int lastField = currentPlayer.getPosition();
        viewController.movePlayer(currentPlayer.getName(), lastField, gameLogic.getSumOfDice());
        currentPlayer.movePosition(gameLogic.getSumOfDice(), board.getFields().length);
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

    private void setupLanguage(){
        viewController.showEmptyGUI();
        String userLanguage = viewController.getUserLanguage();
        setFilepathLanguage(userLanguage);
        this.logicCollection = LogicStringCollection.getInstance();
        this.languageCollection = LanguageStringCollection.getInstance(language);
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

    private void createPlayers() {
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

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
        return playerAmount;
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

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    public void GodMode(boolean mode){
        this.test = mode;
    }

    public Player getCurrentPlayerTurn(){
        return gameLogic.getCurrentPlayer();
    }
}
