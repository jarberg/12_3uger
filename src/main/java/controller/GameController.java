package controller;

import model.board.*;
import model.misc.DieSet;
import model.player.Player;
import model.player.PlayerList;
import model.text.FileReader;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

import java.awt.*;

public class GameController {

    private static GameController singletonInstance = new GameController();
    private LanguageStringCollection languageCollection;
    private LogicStringCollection logicCollection;
    private ViewController viewController;
    private FileReader fileReader;

    private DieSet dice;
    private int playerAmount;
    private boolean endTurn = false;
    private Field currentField;
    private Player currentPlayer;
    private PlayerList playerlist;
    private Board board;
    private Bank bank;

    private GameController(){
        this.fileReader = FileReader.getSingleInstance();
        this.viewController = ViewController.getSingleInstance();
        this.logicCollection = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();
        dice = new DieSet();

    }

    public static GameController getSingleInstance(){
        return singletonInstance;
    }

    public void playGame(){

        setupGame();
        viewController.showFieldMessage(playerlist.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);

        while(!checkIfAllBroke()){
            playTurn();
        }
        checkForWinner();

    }

    public void createBoard(int[][] fieldLogic, String[][] fieldInfo){
        this.board = new Board();
        this.board.setupBoard(fieldLogic, fieldInfo);
    }

    public boolean checkIfAllBroke(){
        boolean foundWinner=false;
        int counter=0;

        for (Player player : playerlist.getAllPlayers()){
            if (player.getBrokeStatus())
                counter++;
        }

        if(counter >= playerlist.getAllPlayers().length-1){
            foundWinner=true;
        }
        return foundWinner;
    }

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    public void movePlayer(Player player, int position, int amount){
        player.setPosition((position+amount)%board.getFields().length);
    }

    private void createPlayerList(int amount){
        playerlist = new PlayerList(amount);
    }

    public Player[] getPlayersButPlayer(Player notThisOneToo){
        Player[] playersInGame = playerlist.getAllPlayers();
        int length = playersInGame.length;
        Player[] otherPlayers = new Player[length - 1];
        int counter = 0;
        for (Player aPlayersInGame : playersInGame) {
            if (aPlayersInGame != notThisOneToo) {
                otherPlayers[counter] = aPlayersInGame;
                counter++;
            }
        }
        return otherPlayers;
    }

    private void playTurn(){
        endTurn = false;
        currentPlayer = playerlist.getCurrentPlayer();

        rollAndShowDice(currentPlayer);
        int lastField = currentPlayer.getPosition();
        int sumOfDice = dice.getDieOneValue() + dice.getDieTwoValue();
        viewController.movePlayer(currentPlayer.getName(), lastField, sumOfDice);
        movePlayer(currentPlayer, lastField, sumOfDice);
        int position = currentPlayer.getPosition();
        currentField = board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(currentPlayer, getPlayersButPlayer(currentPlayer));
        currentField.accept(fieldVisitor);

        /*
        while(!endTurn) {
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }
        */
        setNextPlayer();

    }

    private void rollAndShowDice(Player curPlayer){
        rollDice(curPlayer);
        int dieOneValue = dice.getDieOneValue();
        int dieTwoValue = dice.getDieTwoValue();
        viewController.showDice(dieOneValue, dieTwoValue);
    }

    private void setupGame(){
        setupLanguage();
        this.playerAmount = getPlayerAmount();
        createPlayers();
        makePlayerChooseCar();
        createBoard(logicCollection.getFieldsText(), languageCollection.getFieldsText());
        this.bank = new Bank(playerlist, board);
        TradeController.setBank(bank);
        FieldVisitor.setBank(bank);
        showGameBoard();
        addPlayersToGUI();
    }


    private Player getPlayerByName(String playerName){
        Player player = null;
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            if(getPlayer(i).getName().equals(playerName)){
                player =getPlayer(i);
            }
        }
        return player;
    }

    public Player getPlayer(int index) {
        return playerlist.getPlayer(index);
    }

    public void addPlayer(int index, Player player) {
        playerlist.addPlayer(index, player);
    }

    public void setNextPlayer(){
        playerlist.setNextPlayer();
    }


    public void Auktion(Player player, Field field){

    }

    private void setupLanguage(){
        viewController.showEmptyGUI();
        String userLanguage = viewController.getUserLanguage();
        setFilepathLanguage(userLanguage);
        this.logicCollection = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();
    }

    public boolean hasPlayerWithName(String name){
        for (Player player : playerlist.getAllPlayers()){
            if (player != null && player.getName().equals(name))
                return true;
        }
        return false;
    }

    public void rollDice(Player player){
        dice.roll();
        player.setDoubleTurnStatus(checkdiceForDoubleRoll());
    }

    private void setFilepathLanguage(String language) {
        //TODO: Filereader changes language, is shared singleton
        FileReader.setLanguage(language);
    }

    private void createPlayers() {
        //TODO: playerAmount redundancy
        createPlayerList(playerAmount);
        for (int i = 0; i < playerAmount; i++) {
            String name = viewController.getPlayerName();
            //String name = "name"; // gider ikke skrive navne ind hver gang programmet skal køres
            String playerName = name;
            int playerIdentifier = 2;
            while(hasPlayerWithName(playerName)){
                playerName = name + "#" + playerIdentifier;
                playerIdentifier++;
            }
            addPlayer(i, new Player(playerName));
        }
    }

    private void makePlayerChooseCar() {
        for (Player player : playerlist.getAllPlayers()){
            //TODO:
            Color chosenColor = viewController.getUserColor(player.getName());
            player.setPlayerColor(chosenColor);
        }
    }

    private void showGameBoard(){
        viewController.showGameGUI(board.getFields());
    }

    private void addPlayersToGUI() {
        for (Player player : playerlist.getAllPlayers()){
            viewController.addPlayer(player.getName(), player.getPlayerColor(), player.getBalance());
            viewController.spawnPlayers();
            viewController.showPlayerScores();
        }
    }

    private void checkForWinner(){
        String winner="";
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            if (!getPlayer(i).getBrokeStatus())
                winner = playerlist.getAllPlayers()[i].getName();
        }
        System.out.println(winner+" is the winner!");
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
            //TODO: Getplayerchoice, no hardcoded options
        return playerAmount;
    }

    private void buyBuilding(Player player, Field field){

        //TODO: FieldVisitor
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

        Field field = board.getFields()[player.getPosition()];

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
        if(field instanceof  BreweryField  ){
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

        if(field instanceof ChanceField){

            choiceList.add("take card,5");
        }

        if(field instanceof GoToJailField){

        }
        if(field instanceof ParkingField){

        }
        if(field instanceof StartField){

        }
        if(field instanceof TaxField){

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

        }
    }
}
