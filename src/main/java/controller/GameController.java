package controller;

import model.board.*;
import model.player.Player;
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
    private boolean endTurn= false;
    private Field currentField;
    private Player currentPlayer;

    private int boardLength = 40;
    FieldVisitor Fieldvisitor;

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

    private void playTurn(){
        endTurn = false;
        currentPlayer = gameLogic.getCurrentPlayer();
        Fieldvisitor = new FieldVisitor(currentPlayer,gameLogic.getAllPlayers());

        rollAndShowDice(currentPlayer);
        int lastField = currentPlayer.getPosition();
        viewController.movePlayer(currentPlayer.getName(), lastField, gameLogic.getSumOfDice());
        gameLogic.movePlayer(currentPlayer, lastField, gameLogic.getSumOfDice(),boardLength);
        int position = currentPlayer.getPosition();
        currentField = gameLogic.getBoard().getFields()[position];

        currentField.accept(Fieldvisitor);
        while(!endTurn) {
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }

        gameLogic.setNextPlayer();

    }

    private void rollAndShowDice(Player curPlayer){
        gameLogic.rollDice(curPlayer);
        int dieOneValue = gameLogic.getDieOneValue();
        int dieTwoValue = gameLogic.getDieTwoValue();
        viewController.showDice(dieOneValue, dieTwoValue);
    }

    private void setupGame(){
        setupLanguage();
        this.playerAmount = getPlayerAmount();
        createPlayers();
        makePlayerChooseCar();
        gameLogic.createBoard(logicCollection.getFieldsText(), languageCollection.getFieldsText());
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
        viewController.showGameGUI(gameLogic.getBoard().getFields());
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

    private void checkForWinner(){
        String winner="";
        for (int i = 0; i <gameLogic.getAllPlayers().length ; i++) {
            if (!gameLogic.getPlayer(i).getBrokeStatus())
                winner = gameLogic.getAllPlayers()[i].getName();
        }
        System.out.println(winner+" is the winner!");
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
        return playerAmount;
    }

    public void GodMode(boolean mode){
        this.test = mode;
    }

    GameLogic getGameLogic(){
        return gameLogic;
    }

    private void buyBuilding(Player player, Field field){


        if (field instanceof PropertyField) {
            if(((PropertyField) field).getBuildingCount()==5) {

            }
            else{
                if (((PropertyField) field).getBuildingCount() <5) {
                    ((PropertyField) field).addBuilding();
                    viewController.addBuilding(((PropertyField) field));
                    payment(player, -((PropertyField) field).getBuildingPrice());

                }
                else {
                    ((PropertyField) field).addBuilding();
                    viewController.addBuilding(((PropertyField) field));
                    payment(player, -((PropertyField) field).getBuildingPrice());
                }
            }
        }
    }

    private void payment(Player player, int amount){
        player.addToBalance(amount);
        viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance()
        );
    }

    public String[][] getChoices(Player player){
        List choiceList = new List();
        boolean playerInJail = player.isInJail();
        Field field = gameLogic.getBoard().getFields()[player.getPosition()];
        if(field instanceof  PropertyField){
            if(!((PropertyField) field).isOwned()) {
                choiceList.add("Buy Field,1");
            }
            else {
                if(((PropertyField) field).isOwned() && ((PropertyField) field).getBuildingCount()==5){
                }
                else{
                    choiceList.add("Buy House,8");
                }
            }
        }
        if(field instanceof  BreweryField && !((BreweryField) field).isOwned() ){
            choiceList.add("Buy Field,4");
        }
        if(playerInJail)
        {
            if(player.getJailCardStatus())
                choiceList.add("Use JailCard,2");
            if(player.getBalance() > ((JailField) field).getBailAmount()){
                choiceList.add("Pay "+((JailField) field).getBailAmount()+",3");
            }
        }

        choiceList.add("Auktion,7");
        choiceList.add("End turn,0");

        String[][] finalChoiceList = new String[choiceList.getItemCount()][];

        for (int i = 0; i <choiceList.getItemCount() ; i++) {
            finalChoiceList[i] = choiceList.getItem(i).split(",");
        }

        return finalChoiceList;
    }

    public void playerOptions(String[][] choices,Player player) {

        String[] choiceOptions = new String[choices.length];
        int[] typeArray = new int[choices.length];

        for (int i = 0; i < choices.length; i++) {
            choiceOptions[i] = choices[i][0];
            typeArray[i] = Integer.parseInt(choices[i][1]);
        }

        int typeChoice=0;

        String choiceList = viewController.getUserSelection("Do a thing", choiceOptions);

        for (int i = 0; i < choiceOptions.length ; i++) {
            if( choiceOptions[i]== choiceList ){
                typeChoice = Integer.parseInt(choices[i][1]);
            }
        }
        switch(typeChoice){

            case 0: this.endTurn = true;    break;
            case 1: buyProperty(player, currentField);break;
            case 2: this.endTurn = true;    break;
            case 3: this.endTurn = true;    break;
            case 4: this.endTurn = true;    break;
            case 5: this.endTurn = true;    break;
            case 6: this.endTurn = true;    break;
            case 7: this.endTurn = true;    break;
            case 8: buyBuilding(player, currentField);  break;
            case 9: ;break;
        }
   }

    public void buyProperty(Player player, Field field){

        if(field instanceof PropertyField){
            player.addToBalance(-((PropertyField) field).getPrice());
            viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance());
            ((PropertyField) field).setOwned(true);
        }
        if(field instanceof  BreweryField) {
            player.addToBalance(-150);
            viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance());

            ((BreweryField) field).setOwned(true);
        }
    }
}
