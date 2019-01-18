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

    private DieSet
            dice;
    private int playerAmount;
    private boolean endTurn = false;
    private Field currentField;
    private Player currentPlayer;
    private PlayerList playerlist;
    private Board board;
    private Bank bank = Bank.getSingleInstance();
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


    private void playTurn(){

        currentTurn++;


        endTurn = false;
        currentPlayer = playerlist.getCurrentPlayer();
        currentPlayer.addCurrentTurn();

        checkIfinJailBeforeMoving();
        checkIfPassedStart();
        resolveField();

        while(!endTurn) {
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }

        currentPlayer.setPassedStartStatus(false);

        setNextPlayer();


        lastTurn = currentTurn;


    }

    private void resolveField(){
        int position = currentPlayer.getPosition();
        currentField = board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(currentPlayer, playerlist.getPlayersButPlayer(currentPlayer), deck, board);
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
            if(!currentPlayer.isInJail()) {
                String message = String.format(languageCollection.getMenu()[47], currentPlayer.getName(), sumOfDice);
                viewController.showMessage(message);
            }
            movePlayer(currentPlayer, lastField, sumOfDice);
        }
    }

    private void checkIfPassedStart(){
        if(currentPlayer.getPassedStartStatus() && !currentPlayer.isInJail()){

            viewController.showMessage(languageCollection.getMenu()[24]);
            tradecontroller.transferAssets(currentPlayer, 200);
        }
        currentPlayer.setPassedStartStatus(false);
    }

    private void rollAndShowDice(Player curPlayer){
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

    public void auction(Player player, Field field){
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
        player.setDoubleTurnStatus(dice.getIdenticalRolls());
    }

    private void setFilepathLanguage(String language) {

        FileReader.setLanguage(language);
    }


    public String getPlayerCount(){
        return String.valueOf(playerCount);
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
            playerCount++;
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
        for (int i = 0; i < playerlist.getAllPlayers().length ; i++) {
            if (!getPlayer(i).getBrokeStatus())
                winner = playerlist.getAllPlayers()[i];

        }
        String winnerMessage = String.format(languageCollection.getMenu()[25], winner.getName());
        viewController.showMessage(winnerMessage);
    }

    private int getPlayerAmount() {
        if (playerAmount == 0)
            playerAmount = viewController.getPlayerAmount();
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
        String[] choiceList = new String[0];
        boolean playerInJail = player.isInJail();

        Field field = board.getFields()[player.getPosition()%40];
        //TODO: Menu.txt
        //TODO: Sell jail card (not end turn + work)
        if(playerInJail) {
            //TODO: Had a player stuck in jail forever
            if (currentField instanceof  JailField) {

                if (currentPlayer.getCurrentTurn()>currentPlayer.getJailTurn() && !( currentPlayer.getCurrentTurn() >= 3+currentPlayer.getJailTurn())) {
                    String option = String.format(languageCollection.getMenu()[34]+",8");
                    choiceList = addToStringArray(choiceList, option);
                }
                if (player.getJailCardStatus()) {
                    String option = String.format(languageCollection.getMenu()[33]+",1");
                    choiceList = addToStringArray(choiceList, option);
                }
                if (currentPlayer.getCurrentTurn()>currentPlayer.getJailTurn() && (currentPlayer.getBalance() > ((JailField) currentField).getBailAmount() || ( currentPlayer.getCurrentTurn() >= 3+currentPlayer.getJailTurn()))) {

                    String option = String.format(languageCollection.getMenu()[30]+" "+((JailField) currentField).getBailAmount()+ ",2");
                    choiceList = addToStringArray(choiceList, option);

                }

                if (currentPlayer.getBalance() < ((JailField) currentField).getBailAmount()|| ((currentPlayer.getBalance() < ((JailField) currentField).getBailAmount()&&( currentPlayer.getCurrentTurn() >= 3+currentPlayer.getJailTurn())))) {
                    String option = languageCollection.getMenu()[47]+ ",7";
                    choiceList = addToStringArray(choiceList, option);
                }
            }
        }
        if(player.getJailCardStatus()){
            String option = String.format(languageCollection.getMenu()[32]+",4");
            choiceList = addToStringArray(choiceList, option);
        }
        PropertyField[] buildableProperty = bank.getPlayerBuildableFields(currentPlayer);
        boolean playerCanBuild = (buildableProperty.length > 0);
        if(playerCanBuild){
            String option = languageCollection.getMenu()[35]+",5";
            choiceList = addToStringArray(choiceList, option);
        }
        if(bank.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length>0){
            String pawnString = languageCollection.getMenu()[27];
            String number = "6";
            String choiceString = String.format("%s,%s", pawnString, number);
            choiceList = addToStringArray(choiceList, choiceString);
        }
        if(field instanceof TaxField){

        }
        if (bank.getPropertyNamesWithNoHousesByPlayer(player).length > 0 && bank.getPlayerNamesWithFieldsWithNoHouses().length > 1){
            String message = languageCollection.getMenu()[41];
            choiceList = addToStringArray(choiceList, message+",9");
        }
        Field[] canBuybackFields = bank.getPawnedFieldsByPlayer(player);
        if(canBuybackFields.length > 0){
            String message = "buybackFieldField";
            choiceList = addToStringArray(choiceList, message+",10");
        }


        String option = String.format(languageCollection.getMenu()[36] + ",0");

        choiceList = addToStringArray(choiceList, option);

        String[][] finalChoiceList = new String[choiceList.length][];

        for (int i = 0; i < choiceList.length ; i++) {
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
                    currentPlayer.setInJail(false);
                    if(currentTurn>lastTurn){
                        currentPlayer.setDoubleTurnStatus(false);
                    }
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

            case 7: tradecontroller.raiseMoney(currentPlayer);
                    break;

            case 8: rollAndShowDice(currentPlayer);
                    if(currentPlayer.getDoubleTurnStatus()) {
                        movePlayer(currentPlayer, currentPlayer.getPosition(), dice.getValue());
                        resolveField();
                        currentPlayer.setInJail(false);
                        }

                    break;

            case 9: tradecontroller.transferAssets(player);
                    break;

            case 10: tradecontroller.buyBackPawnedProperty(currentPlayer);
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
        String message = languageCollection.getMenu()[43];
        String[] options = bank.getPropertyNamesWithNoHousesByPlayer(player);
        String choice = viewController.getUserSelection(message, options);
        Field field = bank.getFieldByName(choice);
        if (field instanceof PropertyField){
            ((PropertyField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((PropertyField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getName(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
        if (field instanceof BreweryField){
            ((BreweryField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((BreweryField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getName(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
        if (field instanceof FerryField){
            ((FerryField) field).setPawnedStatus(true);
            tradecontroller.transferAssets(player,((FerryField) field).getPrice()/2);
            viewController.pawn(field.getTitle(), player.getName(), player.getPlayerColor(), player.getPlayerColor().darker());
        }
   }

    private void useJailCard(){
        currentPlayer.setJailCardStatus(false);
        currentPlayer.setInJail(false);
   }
}