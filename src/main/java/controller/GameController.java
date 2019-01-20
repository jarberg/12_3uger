package controller;

import model.board.*;
import model.deck.Deck;
import model.misc.DieSet;
import model.player.Player;
import model.player.PlayerList;
import utilities.FileReader;
import utilities.LanguageStringCollection;
import utilities.LogicStringCollection;

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
    private PlayerFieldRelationController playerFieldRelationController = PlayerFieldRelationController.getSingleInstance();
    private Deck deck;
    private int lastTurn;
    private int currentTurn;
    private TradeController tradecontroller = TradeController.getSingleInstance();
    private boolean threwDice = false;
    private int playerCount = 1;

    //TODO: if time. split into use case controllers
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
        tradecontroller.setPlayerList(playerlist);
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

    public boolean checkIfAllBroke(){
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

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    private void movePlayer(Player player, int position, int amount){
        player.setPositionWithStartMoney((position+amount)%board.getFields().length);
        viewController.movePlayer(currentPlayer.getName(), position, amount);

    }

    public void createPlayerList(int amount){
        playerlist = new PlayerList(amount);
    }

    public void playTurn(){

        currentTurn++;

        endTurn = false;
        currentPlayer = playerlist.getCurrentPlayer();
        currentPlayer.addCurrentTurn();

        checkIfinJailBeforeMoving();
        checkIfPassedStart();
        resolveField();
        checkIfPassedStart();

        while(!endTurn) {
            Field field = board.getFields()[currentPlayer.getPosition() % 40];
            if (currentPlayer.isInJail() && currentPlayer.getCurrentTurn() >= 3 + currentPlayer.getJailTurn()) {
                currentPlayer.setInJail(false);
                viewController.showMessage(languageCollection.getMenu()[52]);
                payToLeaveJail((JailField) field);
                checkIfinJailBeforeMoving();
                checkIfPassedStart();
                resolveField();
                checkIfPassedStart();
            }
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }

        currentPlayer.setPassedStartStatus(false);

        setNextPlayer();

        lastTurn = currentTurn;

    }

    public void resolveField(){
        int position = currentPlayer.getPosition();
        currentField = board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(currentPlayer, playerlist.getPlayersButPlayer(currentPlayer), deck, board, viewController);
        currentField.accept(fieldVisitor);
    }

    public void checkIfinJailBeforeMoving(){
        if(!currentPlayer.isInJail()) {
            rollAndShowDice(currentPlayer);
            int lastField = currentPlayer.getPosition();
            int sumOfDice = dice.getDieOneValue() + dice.getDieTwoValue();

            if(currentPlayer.getDoubleThrowNum()>2){
                currentPlayer.setInJail(true);
                sumOfDice = (40-lastField+10)%40;
                viewController.showMessage(languageCollection.getMenu()[51]);
                currentPlayer.setDoubleTurnStatus(false);
                endTurn = true;
            }
            if(!currentPlayer.isInJail()) {
                String message = String.format(languageCollection.getMenu()[47], currentPlayer.getName(), sumOfDice);
                viewController.showMessage(message);
            }
            movePlayer(currentPlayer, lastField, sumOfDice);
        }
    }

    public void checkIfPassedStart(){
        if(currentPlayer.getPassedStartStatus() && !currentPlayer.isInJail()){

            viewController.showMessage(languageCollection.getMenu()[24]);
            tradecontroller.transferAssets(currentPlayer, 200);
        }
        currentPlayer.setPassedStartStatus(false);
    }

    public void rollAndShowDice(Player curPlayer){
        rollDice(curPlayer);
        int dieOneValue = dice.getDieOneValue();
        int dieTwoValue = dice.getDieTwoValue();
        if(dice.getIdenticalRolls()) {

            currentPlayer.addDoubleThrowTimes();
        }
        else{
            currentPlayer.resetDoubleThrowTimes();
        }
        viewController.showDice(dieOneValue, dieTwoValue);
    }

    public void setupGame(){
        setupLanguage();
        logicCollection.getTextFromFileReader();
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
        playerFieldRelationController.setupFieldOwnerArray(playerlist);
        playerFieldRelationController.setBoard(board);
    }

    public void createDeck(){
        String[][] deckLogic = logicCollection.getChanceCard();
        String[][] deckText = languageCollection.getChanceCard();
        this.deck  = new Deck(deckLogic, deckText);
    }

    public Player getPlayerByName(String playerName){
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

    public void auction(Player player, Field field){
    }

    public void setupLanguage(){
        viewController.showEmptyGUI();
        String userLanguage = viewController.getUserLanguage();
        setFilepathLanguage(userLanguage);
        this.logicCollection = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();
        setFilepathLanguage(userLanguage);
        languageCollection.getTextFromFileReader();

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
        player.setDoubleTurnStatus(dice.getIdenticalRolls());
    }

    public void setFilepathLanguage(String language) {

        FileReader.setLanguage(language);
    }

    public String getPlayerCount(){
        return String.valueOf(playerCount);
    }

    public void createPlayers() {
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
            playerCount++;
        }
        currentPlayer = playerlist.getCurrentPlayer();
    }

    public void makePlayerChooseCar() {
        for (Player player : playerlist.getAllPlayers()){
            //TODO:
            Color chosenColor = viewController.getUserColor(player.getName());
            player.setPlayerColor(chosenColor);
        }
    }

    public void showGameBoard(){
        viewController.showGameGUI(board.getFields());
    }

    public void addPlayersToGUI() {
        for (Player player : playerlist.getAllPlayers()){
            viewController.addPlayer(player.getName(), player.getPlayerColor(), player.getBalance());
            viewController.spawnPlayers();
            viewController.showPlayerScores();
        }
    }

    public void checkForWinner(){
        Player winner = null;
        for (int i = 0; i < playerlist.getAllPlayers().length ; i++) {
            if (!getPlayer(i).getBrokeStatus())
                winner = playerlist.getAllPlayers()[i];

        }
        String winnerMessage = String.format(languageCollection.getMenu()[25], winner.getName());
        viewController.showMessage(winnerMessage);
    }

    public int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPlayerAmount(logicCollection.getPlayerAmount());
            //TODO: Getplayerchoice, no hardcoded options
        return playerAmount;
    }

    public void buyBuilding(Player player, PropertyField field){
        tradecontroller.buyBuilding(player, field);
    }

    //TODO: Use trade controller
    public void payment(Player player, int amount){
        tradecontroller.transferAssets(player, amount);
    }

    public String[][] getChoices(Player player) {
        String[] choiceList = new String[0];
        boolean playerInJail = player.isInJail();

        Field field = board.getFields()[player.getPosition() % 40];

        //TODO: Sell jail card (not end turn + work)
        if (playerInJail) {
            //TODO: Had a player stuck in jail forever
            if (currentField instanceof JailField) {

                    if (currentPlayer.getCurrentTurn() > currentPlayer.getJailTurn() && currentPlayer.isInJail()) {
                        String option = String.format(languageCollection.getMenu()[34] + ",8");
                        choiceList = addToStringArray(choiceList, option);
                    }
                    if (player.getJailCardStatus()) {
                        String option = String.format(languageCollection.getMenu()[33] + ",1");
                        choiceList = addToStringArray(choiceList, option);
                    }
                    if (currentPlayer.getCurrentTurn() > currentPlayer.getJailTurn()) {

                        String option = String.format(languageCollection.getMenu()[30] + " " + ((JailField) currentField).getBailAmount() + ",2");
                        choiceList = addToStringArray(choiceList, option);

                    }

                    if (currentPlayer.getBalance() < ((JailField) currentField).getBailAmount() || ((currentPlayer.getBalance() < ((JailField) currentField).getBailAmount() && (currentPlayer.getCurrentTurn() >= 3 + currentPlayer.getJailTurn())))) {
                        String option = languageCollection.getMenu()[20] + ",7";
                        choiceList = addToStringArray(choiceList, option);
                    }
                }
            }
            if (player.getJailCardStatus()) {
                String option = String.format(languageCollection.getMenu()[32] + ",4");
                choiceList = addToStringArray(choiceList, option);
            }
            PropertyField[] buildableProperty = playerFieldRelationController.getPlayerBuildableFields(currentPlayer);
            boolean playerCanBuild = (buildableProperty.length > 0);
            if (playerCanBuild) {
                String option = languageCollection.getMenu()[35] + ",5";
                choiceList = addToStringArray(choiceList, option);
            }
            if (playerFieldRelationController.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length > 0) {
                String pawnString = languageCollection.getMenu()[27];
                String number = "6";
                String choiceString = String.format("%s,%s", pawnString, number);
                choiceList = addToStringArray(choiceList, choiceString);
            }
            if (field instanceof TaxField) {

            }
            if (playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(player).length > 0 && playerFieldRelationController.getPlayerNamesWithFieldsWithNoHouses().length > 1) {
                String message = languageCollection.getMenu()[41];
                choiceList = addToStringArray(choiceList, message + ",9");
            }
            Field[] canBuybackFields = playerFieldRelationController.getPawnedFieldsByPlayer(player);
            if (canBuybackFields.length > 0) {
                String message = languageCollection.getMenu()[50];
                choiceList = addToStringArray(choiceList, message + ",10");
            }


            String option = String.format(languageCollection.getMenu()[36] + ",0");

            choiceList = addToStringArray(choiceList, option);

            String[][] finalChoiceList = new String[choiceList.length][];

            for (int i = 0; i < choiceList.length; i++) {
                finalChoiceList[i] = choiceList[i].split(",");
            }

            return finalChoiceList;
        }


    private String[] addToStringArray(String[] array, String newString){
        String[] newArray = new String[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = newString;
        return newArray;
    }

    public void playerOptions(String[][] choices, Player player) {
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
                    checkIfinJailBeforeMoving();
                    checkIfPassedStart();
                    resolveField();
                    checkIfPassedStart();

                    while(!endTurn) {
                        playerOptions(getChoices(currentPlayer),currentPlayer);
                    }

                    currentPlayer.setPassedStartStatus(false);

                    setNextPlayer();
                    break;

            case 2: payToLeaveJail((JailField) field);
                    checkIfinJailBeforeMoving();
                    checkIfPassedStart();
                    resolveField();
                    checkIfPassedStart();

                    while(!endTurn) {
                        playerOptions(getChoices(currentPlayer),currentPlayer);
                    }

                    currentPlayer.setPassedStartStatus(false);

                    setNextPlayer();
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

            case 7: tradecontroller.raiseMoney(currentPlayer);
                    break;

            case 8: rollAndShowDice(currentPlayer);
                    currentPlayer.setPositionWithoutStartMoney(dice.getValue());

                    if(currentPlayer.getDoubleTurnStatus()) {
                        viewController.showMessage(languageCollection.getMenu()[54]);
                        movePlayer(currentPlayer, currentPlayer.getPosition(), dice.getValue());
                        currentPlayer.setInJail(false);
                        checkIfPassedStart();
                        resolveField();
                        checkIfPassedStart();

                        while(!endTurn) {
                            playerOptions(getChoices(currentPlayer),currentPlayer);
                        }

                        currentPlayer.setPassedStartStatus(false);

                        setNextPlayer();
                        break;
                    }
                    viewController.showMessage(languageCollection.getMenu()[55]);
                    endTurn = true;
                    break;

            case 9: tradecontroller.transferAssets(player);
                    break;

            case 10: tradecontroller.buyBackPawnedProperty(currentPlayer);
                    break;

        }
   }

    public void payToLeaveJail(JailField field) {
        tradecontroller.transferAssets(currentPlayer,-field.getBailAmount());
        currentPlayer.setInJail(false);
        if(currentPlayer.getCurrentTurn() > currentPlayer.getJailTurn()){
            currentPlayer.setDoubleTurnStatus(false);
        }
    }

    public void sellJailCard() {
        currentPlayer.setJailCardStatus(false);
        int jailCardPrice = Integer.parseInt(logicCollection.getChanceCard()[25][2]);
        tradecontroller.transferAssets(currentPlayer, jailCardPrice / 2);

    }

    public String[][] reverse2DStringArray(String[][] array) {
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
        PropertyField[] buildable = playerFieldRelationController.getPlayerBuildableFields(currentPlayer);
        String[] options = new String[buildable.length];
        for (int i = 0; i < buildable.length; i++) {
            options[i] = buildable[i].getTitle();
        }
        String message = languageCollection.getMenu()[28];
        Field test= playerFieldRelationController.getFieldByName(viewController.getUserSelection(message, options));

        buyBuilding(currentPlayer, (PropertyField) test);
    }

    //TODO: 3???
    private void pawnProperty(Player player){
        String message = languageCollection.getMenu()[43];
        String[] options = playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(player);
        String choice = viewController.getUserSelection(message, options);
        Field field = playerFieldRelationController.getFieldByName(choice);
        if (field instanceof PropertyField){
            ((PropertyField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((PropertyField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
        if (field instanceof BreweryField){
            ((BreweryField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((BreweryField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
        if (field instanceof FerryField){
            ((FerryField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((FerryField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
   }

    private void useJailCard(){
        currentPlayer.setJailCardStatus(false);
        currentPlayer.setInJail(false);
   }

    public PlayerList getPlayerList() {
        return playerlist;
    }

    public PlayerFieldRelationController getPlayerFieldRelationController() {
        return playerFieldRelationController;
    }

    public Deck getDeck() {
        return deck;
    }

    public Board getBoard() {
        return board;
    }

    public Field getCurrentField() {
        return currentField;
    }
}