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
    private ViewController viewController;
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
    TradeController tradecontroller = TradeController.getSingleInstance();

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
        this.board = new Board(fieldLogic, fieldInfo);
        this.board.setupBoard();
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

        endTurn = false;

        currentPlayer = playerlist.getCurrentPlayer();
        if(!currentPlayer.isInJail()) {
            rollAndShowDice(currentPlayer);
            int lastField = currentPlayer.getPosition();
            int sumOfDice = dice.getDieOneValue() + dice.getDieTwoValue();

        movePlayer(currentPlayer, lastField, sumOfDice);
        }
        if(currentPlayer.getPassedStartStatus()){
            //TODO: Currently gives money from goToJail. And possibly for moving backwards with chancecard?
            currentPlayer.addToBalance(200);
            currentPlayer.setPassedStartStatus(false);
            viewController.setGUI_PlayerBalance(currentPlayer.getName(),currentPlayer.getBalance());
            viewController.showMessage(languageCollection.getMenu()[24]);
        }

        int position = currentPlayer.getPosition();
        currentField = board.getFields()[position];


        FieldVisitor fieldVisitor = new FieldVisitor(currentPlayer, getPlayersButPlayer(currentPlayer), deck);
        currentField.accept(fieldVisitor);


        while(!endTurn) {
          playerOptions(getChoices(currentPlayer),currentPlayer);
        }


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


    private void buyBuilding(Player player, Field aField){

            if (aField instanceof PropertyField) {
                if (bank.isOwnerOfAllFieldsOfType(currentPlayer, aField)) {
                    if (((PropertyField) aField).getBuildingCount() == 5) {

                    } else {
                        if (((PropertyField) aField).getBuildingCount() < 5) {
                            ((PropertyField) aField).addBuilding();
                            viewController.addBuilding(((PropertyField) aField));
                            payment(player, -((PropertyField) aField).getBuildingPrice());

                        } else {

                            ((PropertyField) aField).addBuilding();
                            viewController.addBuilding(((PropertyField) aField));
                            payment(player, -((PropertyField) aField).getBuildingPrice());
                        }
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

        Field field = board.getFields()[player.getPosition()%40];
        //TODO: Menu.txt
        //TODO: Sell jail card (not end turn + work)
        if(playerInJail) {
            if (currentField instanceof  JailField) {
                if (player.getJailCardStatus()) {
                    choiceList.add("Use JailCard,1");
                }
                if (player.getBalance() > ((JailField) field).getBailAmount()) {
                    choiceList.add("Pay " + ((JailField) field).getBailAmount() + ",2");
                }
                if (player.getBalance() < ((JailField) field).getBailAmount()) {
                    choiceList.add("Sell,3");
                }
            }
        }
        if(player.getJailCardStatus()==true){
            choiceList.add("Sell Jail Card,4");
        }
        if((bank.getPlayerFields(player).length >0&& bank.getSameTypeFields(currentPlayer).length>1)){
            choiceList.add("Buy House,5");
        }
        //if(bank.getPlayerFields(player).length>0){
        //    choiceList.add("Pantsæt,6");

       // }
        if(field instanceof TaxField){

        }
        //TODO: Show ROLL AGAIN or GO TO JAIL YOU LUCKY BASTARD instead of END TURN when rolled identical rolls
        choiceList.add("End turn,0");

        String[][] finalChoiceList = new String[choiceList.getItemCount()][];

        for (int i = 0; i <choiceList.getItemCount() ; i++) {
            finalChoiceList[i] = choiceList.getItem(i).split(",");
        }

        return finalChoiceList;
    }

    public void playerOptions(String[][] choices,Player player) {
        Field field = board.getFields()[player.getPosition()%40];

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

            case 1: useJailCard();    break;

            case 2: tradecontroller.transferAssets(currentPlayer,((JailField) field).getBailAmount());
                    viewController.setGUI_PlayerBalance(currentPlayer.getName(),currentPlayer.getBalance());
                    this.endTurn = true;
                    break;

            case 3: this.endTurn = true;    break;

            case 4: this.endTurn = true;    break;

            case 5: getListOfBuildable();    break;

            case 6: this.endTurn = true;    break;

            case 7: this.endTurn = true;    break;

            case 8: ;  break;

            case 9: ;break;
        }
   }

   public void getListOfBuildable(){

       Field[] fields = bank.getPlayerFields(currentPlayer);

       String[] usableFields = new String[0];

       for (Field aField : fields) {

           if(bank.isOwnerOfAllFieldsOfType(currentPlayer,aField)) {
               String[] temp = new String[usableFields.length + 1];

               for (int i = 0; i < usableFields.length; i++) {

                   temp[i] = usableFields[i];

               }
               temp[temp.length - 1] = aField.getTitle();
               usableFields = temp;
           }

       }
       if(usableFields.length == 0){
            usableFields = new String[]{"you have no viable options"};
       }

       Field test= bank.getFieldByName(viewController.getUserSelection("chose a field to buy", usableFields));

       buyBuilding(currentPlayer, test);
   }

   public void useJailCard(){
        currentPlayer.setJailCardStatus(false);
        currentPlayer.setInJail(false);
   }



}
