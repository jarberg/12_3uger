package controller;

import model.board.*;
import model.deck.Deck;
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
    private ViewControllerInterface viewController;
    private FileReader fileReader;

    private DieSet dice;
    private int playerAmount;
    private boolean endTurn = false;
    private Field currentField;
    private Player currentPlayer;
    private PlayerList playerlist;
    private Board board;
    private Bank bank = Bank.getSingleInstance();
    private Deck deck;
    private int lastTurn;
    int currentTurn;
    TradeController tradecontroller = TradeController.getSingleInstance();
    boolean threwDice =false;

    //TODO: if time. split into usecase controllers
    private GameController(){

        this.fileReader         = FileReader.getSingleInstance();
        this.viewController     = ViewController.getSingleInstance();
        this.logicCollection    = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();
        dice                    = new DieSet();
    }

    public static GameController getSingleInstance(){
        return singletonInstance;
    }

    public void setViewController(ViewControllerInterface viewController){
        this.viewController = viewController;
    }

    public void playGame(){
        setupGame();
        viewController.showFieldMessage(playerlist.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);
        while(!checkIfAllBroke()){
            playTurn();
        }
        checkForWinner();
    }

    private void createBoard(int[][] fieldLogic, String[][] fieldInfo){
        this.board = new Board(fieldLogic, fieldInfo);
        this.board.setupBoard();
    }

    private boolean checkIfAllBroke(){
        boolean foundWinner = false;
        int counter         = 0;

        for (Player player : playerlist.getAllPlayers()){
            if (player.getBrokeStatus())
                counter++;
        }

        if(counter >= playerlist.getAllPlayers().length-1){
            foundWinner=true;
        }
        return foundWinner;
    }

    private boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    private void movePlayer(Player player, int position, int amount){
        player.setPosition((position+amount)%board.getFields().length);
        viewController.movePlayer(currentPlayer.getName(), position, amount);

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

        currentTurn++;


        endTurn = false;
        currentPlayer = playerlist.getCurrentPlayer();

        checkIfinJailBeforeMoving();
        checkIfPassedStart();
        resolveField();

        while(!endTurn) {
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }

        setNextPlayer();


        lastTurn = currentTurn;


    }

    private void resolveField(){
        int position = currentPlayer.getPosition();
        currentField = board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(currentPlayer, getPlayersButPlayer(currentPlayer), deck, board);
        currentField.accept(fieldVisitor);
    }

    private void checkIfinJailBeforeMoving(){
        if(!currentPlayer.isInJail()) {
            rollAndShowDice(currentPlayer);
            int lastField = currentPlayer.getPosition();
            int sumOfDice = dice.getDieOneValue() + dice.getDieTwoValue();

            if(currentPlayer.getDoubleThrowNum()>2){
                currentPlayer.setInJail(true);
                sumOfDice = (40-lastField+10)%40;
                endTurn = true;
            }
            movePlayer(currentPlayer, lastField, sumOfDice);
        }
    }

    private void checkIfPassedStart(){
        if(currentPlayer.getPassedStartStatus() && !currentPlayer.isInJail()){

            viewController.showMessage(languageCollection.getMenu()[24]);
            tradecontroller.transferAssets(currentPlayer, 200);
            currentPlayer.setPassedStartStatus(false);
        }
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
        createDeck();
        setupBank();
        showGameBoard();
        addPlayersToGUI();
    }

    private void setupBank(){
        bank.setBankNoCrashy(playerlist.getAllPlayers().length);
        bank.setPlayerList(playerlist);
        bank.setBoard(board);
    }

    private void createDeck(){
        String[][] deckLogic = logicCollection.getChanceCard();
        String[][] deckText = languageCollection.getChanceCard();
        this.deck  = new Deck(deckLogic, deckText);
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

    private Player getPlayer(int index) {
        return playerlist.getPlayer(index);
    }

    public void addPlayer(int index, Player player) {
        playerlist.addPlayer(index, player);
    }

    private void setNextPlayer(){
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

    private boolean hasPlayerWithName(String name){
        for (Player player : playerlist.getAllPlayers()){
            if (player != null && player.getName().equals(name))
                return true;
        }
        return false;
    }

    private void rollDice(Player player){
        dice.roll();
        player.setDoubleTurnStatus(checkdiceForDoubleRoll());
    }

    private void setFilepathLanguage(String language) {

        FileReader.setLanguage(language);
    }

    private void createPlayers() {
        //TODO: playerAmount redundancy
        createPlayerList(playerAmount);
        for (int i = 0; i < playerAmount; i++) {
            String name = viewController.getPlayerName();
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
        Player winner = null;
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            if (!getPlayer(i).getBrokeStatus())
                winner = playerlist.getAllPlayers()[i];

        }
        String winnerMessage = String.format(languageCollection.getMenu()[25], winner.getName());
        viewController.showMessage(winnerMessage);
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPLayerAmount();
            //TODO: Getplayerchoice, no hardcoded options
        return playerAmount;
    }


    private void buyBuilding(Player player, PropertyField field){
        tradecontroller.buyBuilding(player, field);
    }

    //TODO: Use trade controller
    private void payment(Player player, int amount){
        tradecontroller.transferAssets(player, amount);
    }

    private String[][] getChoices(Player player){
        List choiceList = new List();
        boolean playerInJail = player.isInJail();

        Field field = board.getFields()[player.getPosition()%40];
        //TODO: Menu.txt
        //TODO: Sell jail card (not end turn + work)
        if(playerInJail) {
            //TODO: Had a player stuck in jail forever
            if (currentField instanceof  JailField) {

                if (currentTurn>lastTurn) {
                    choiceList.add(String.format(languageCollection.getMenu()[34]+",9"));
                }
                if (player.getJailCardStatus()) {
                    choiceList.add(String.format(languageCollection.getMenu()[33]+",1"));
                }
                if (player.getBalance() > ((JailField) field).getBailAmount()) {
                    choiceList.add(String.format(languageCollection.getMenu()[30]+" "+((JailField) field).getBailAmount()+ ",2"));
                }
                if (player.getBalance() < ((JailField) field).getBailAmount()) {
                    choiceList.add(languageCollection.getMenu()[31]+ ", 2");
                }
            }
        }
        if(player.getJailCardStatus()==true){
            choiceList.add(String.format(languageCollection.getMenu()[32]+",4"));
        }
        PropertyField[] buildableProperty = bank.getPlayerBuildableFields(currentPlayer);
        boolean playerCanBuild = (buildableProperty.length > 0);
        if(playerCanBuild){
            choiceList.add(languageCollection.getMenu()[35
                    ]+",5");
        }
        if(bank.getFieldsWithNoHousesByPlayer(player).length>0){
            String pawnString = languageCollection.getMenu()[27];
            String number = "6";
            String choiceString = String.format("%s,%s", pawnString, number);
            choiceList.add(choiceString);
        }

        if(field instanceof TaxField){

        }
        if (bank.getPlayerNamesWithNoHouses().length > 1){
            String message = languageCollection.getMenu()[41];
            choiceList.add(message+",9");
        }
        //TODO: Show ROLL AGAIN or GO TO JAIL YOU LUCKY BASTARD instead of END TURN when rolled identical rolls
        choiceList.add(String.format(languageCollection.getMenu()[36]+",0"));

        String[][] finalChoiceList = new String[choiceList.getItemCount()][];

        for (int i = 0; i <choiceList.getItemCount() ; i++) {
            finalChoiceList[i] = choiceList.getItem(i).split(",");
        }

        return finalChoiceList;
    }

    private void playerOptions(String[][] choices, Player player) {
        Field field = board.getFields()[player.getPosition()%40];

        String[] choiceOptions = new String[choices.length];
        int[] typeArray = new int[choices.length];

        for (int i = 0; i < choices.length; i++) {
            choiceOptions[i] = choices[i][0];
            typeArray[i] = Integer.parseInt(choices[i][1]);
        }

        int typeChoice=0;

        choiceOptions = reverseStringArray(choiceOptions);
        choices = reverse2DStringArray(choices);
        String choiceList = viewController.getUserSelection(String.format(LanguageStringCollection.getSingleInstance().getMenu()[29]+" "+currentPlayer.getName()
        ), choiceOptions);

        for (int i = 0; i < choiceOptions.length ; i++) {
            if(choiceOptions[i].equals(choiceList)){
                typeChoice = Integer.parseInt(choices[i][1]);
            }
        }

        switch(typeChoice){

            case 0: this.endTurn = true;
                    break;

            case 1: useJailCard();
                    currentPlayer.setDoubleTurnStatus(true);
                    endTurn = true;
                    break;

            case 2: tradecontroller.transferAssets(currentPlayer,-((JailField) field).getBailAmount());
                    viewController.setGUI_PlayerBalance(currentPlayer.getName(),currentPlayer.getBalance());
                    currentPlayer.setInJail(false);
                    checkIfinJailBeforeMoving();
                    break;

            case 3: this.endTurn = true;
                    break;

            //TODO: Make sure this method isn't breaking anything
            case 4: sellJailCard();
                    break;

            case 5: getListOfBuildable();
                    break;

            case 6: pawnProperty(player);
                    break;

            case 7: this.endTurn = true;
                    break;

            case 8: rollAndShowDice(currentPlayer);
                    threwDice = true;
                    if(currentPlayer.getDoubleTurnStatus())
                        {movePlayer(currentPlayer, currentPlayer.getPosition(), dice.getValue());}
                    else
                        {endTurn =true;}
                    break;

            case 9: tradecontroller.tradePropertyWithPlayer(player);
                    break;
        }
   }

    private void sellJailCard() {
        currentPlayer.setJailCardStatus(false);
        int jailCardPrice = Integer.parseInt(logicCollection.getChanceCard()[25][2]);
        tradecontroller.transferAssets(currentPlayer, jailCardPrice / 2);

    }

    private String[][] reverse2DStringArray(String[][] array) {
        String[][] reversed = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[(array.length - i) - 1];
        }
        return reversed;
    }

    private String[] reverseStringArray(String[] array) {
        String[] reversed = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[(array.length - i) - 1];
        }
        return reversed;
    }

    public void getListOfBuildable(){
        PropertyField[] buildable = bank.getPlayerBuildableFields(currentPlayer);
        String[] options = new String[buildable.length];
        for (int i = 0; i < buildable.length; i++) {
            options[i] = buildable[i].getTitle();
        }
        String message = languageCollection.getMenu()[28];
        Field test= bank.getFieldByName(viewController.getUserSelection(message, options));

        buyBuilding(currentPlayer, (PropertyField) test);
    }

    private void pawnProperty(Player player){
        String message = languageCollection.getMenu()[22222];
        viewController.showMessage(message);
        Field[] fields = bank.getFieldsWithNoHousesByPlayer(player);
        String[] options = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            options[i] = fields[i].getTitle();
        }
        String choice = viewController.getUserSelection(message, options);
   }

    private void useJailCard(){
        currentPlayer.setJailCardStatus(false);
        currentPlayer.setInJail(false);
   }
}