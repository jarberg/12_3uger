package controller;

import model.board.JailField;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GameControllerTest {


    private GameController gameCon;
    private ViewControllerInterface viewController;
    private LogicStringCollection logicCollection;
    private LanguageStringCollection languageCollection;
    private TradeController tradeController;
    private FieldVisitor fieldVisitor;
    private DrawController drawController;

    @Before
    public void setUp(){
        gameCon = GameController.getSingleInstance();
        viewController = new ViewControllerStub();

        gameCon.setViewController(viewController);
        tradeController = TradeController.getSingleInstance();
        tradeController.setViewController(viewController);

        languageCollection = LanguageStringCollection.getSingleInstance();
        logicCollection = LogicStringCollection.getSingleInstance();
    }

    @After
    public void tearDown(){
        languageCollection = null;
        logicCollection = null;
        tradeController = null;
        gameCon = null;
        viewController = null;
        fieldVisitor = null;
        drawController = null;
    }

    @Test
    public void shouldCreateModelFromLanguage(){

    }

    //@Test
    public void shouldCreateFieldVisitorAndDrawController(){
        gameCon.setupGame();

        PlayerList playerList = gameCon.getPlayerList();
        Player currentPlayer = playerList.getAllPlayers()[0];
        Player[] otherPlayers = playerList.getPlayersButPlayer(currentPlayer);
        fieldVisitor = new FieldVisitor(currentPlayer, otherPlayers, gameCon.getDeck(), gameCon.getBoard(), viewController);
        drawController = new DrawController(currentPlayer, otherPlayers, gameCon.getBank(), gameCon.getBoard(), gameCon.getDeck(), viewController);
    }

    @Test
    public void shouldChangeLanguage(){
        String originalMenu = languageCollection.getMenu()[0];
        String originalCard = languageCollection.getChanceCard()[0][1];
        String originalField = languageCollection.getFieldsText()[7][1];
        gameCon.setupGame();
        String newMenu = languageCollection.getMenu()[0];
        String newCard = languageCollection.getChanceCard()[0][1];
        String newField = languageCollection.getFieldsText()[7][1];

        //assertNotEquals(originalMenu, newMenu);
        //assertNotEquals(originalCard, newCard);
        //assertNotEquals(originalField, newField);
    }

    @Test
    public void shouldLetPlayerLeavePrisonIfPaying50(){
        gameCon.setupGame();
        PlayerList playerList = gameCon.getPlayerList();
        Player player = playerList.getCurrentPlayer();
        player.addCurrentTurn();
        player.setInJail(true);
        player.setJailTurn();
        JailField jail = new JailField("ID", "Title", "Subtitle", "message", Color.black, 50, 3);
        gameCon.payToLeaveJail(jail);
        assertEquals(1450, player.getBalance());
        assertFalse(player.getJailCardStatus());
    }
/*
    @Test
    public void setupGameTest(){

        gameCon.setFilepathLanguage("danish");
        gameCon.createPlayers();
        gameCon.createDeck();
        setupLanguage();
        gameCon.makePlayerChooseCar();
        gameCon.createBoard(logicCollection.getFieldsText(), languageCollection.getFieldsText());

        gameCon.createDeck();
        gameCon.setupBank();
        gameCon.showGameBoard();
        gameCon.addPlayersToGUI();

        for (int i = 1; i == gameCon.getPlayerAmount() ; i++) {
            if(i==1) {
                assertEquals("Test", gameCon.getPlayer(i).getName());
            }
            if(i>1) {
                assertEquals("Test#"+i, gameCon.getPlayer(i).getName());
            }
        }

        for (int i = 0; i < gameCon.playerAmount ; i++) {
            assertEquals(0, gameCon.getPlayer(i).getPosition());
        }
        for (int i = 0; i < gameCon.playerAmount ; i++) {
            assertEquals(1500, gameCon.getPlayer(i).getBalance());
        }
    }
*/
/*
    @Test
    public void setupLanguage(){

        viewController.showEmptyGUI();
        gameCon.setFilepathLanguage(viewController.getUserLanguage());
        this.logicCollection = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();

    }
    */
/*
    @Test
    public void playTurn(){

        gameCon.currentTurn++;



        gameCon.endTurn = false;
        gameCon.currentPlayer = gameCon.playerlist.getCurrentPlayer();

        gameCon.checkIfinJailBeforeMoving();
        gameCon.checkIfPassedStart();
        resolveField();

        while(!gameCon.endTurn) {
            gameCon.playerOptions(gameCon.getChoices(gameCon.currentPlayer), gameCon.currentPlayer);
        }

        gameCon.setNextPlayer();


        gameCon.lastTurn = gameCon.currentTurn;

        //gameCon.currentPlayer.setBrokeStatus(true);


    }

    @Test
    public void playGame(){


        setupGame();
        viewController.showFieldMessage(gameCon.playerlist.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);
        while(!gameCon.checkIfAllBroke()){
            playTurn();
        }
        gameCon.checkForWinner();

    }

    @Test
    public void testCase01(){
        setupGame();
    }

    @Test
    public void resolveField(){
        gameCon.currentPlayer= gameCon.playerlist.getCurrentPlayer();
        int position = gameCon.currentPlayer.getPosition();
        gameCon.currentField = gameCon.board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(gameCon.currentPlayer, gameCon.getPlayersButPlayer(gameCon.currentPlayer), gameCon.deck, gameCon.board, viewController);
        gameCon.currentField.accept(fieldVisitor);
    }

    @Test
    public void checkIfinJailBeforeMoving(){
        gameCon.currentPlayer= gameCon.playerlist.getCurrentPlayer();
        if(!gameCon.currentPlayer.isInJail()) {
            gameCon.rollAndShowDice(gameCon.currentPlayer);
            int lastField = gameCon.currentPlayer.getPosition();
            int sumOfDice = gameCon.dice.getDieOneValue() + gameCon.dice.getDieTwoValue();

            if(gameCon.currentPlayer.getDoubleThrowNum()>2){
                gameCon.currentPlayer.setInJail(true);
                sumOfDice = (40-lastField+10)%40;
                gameCon.endTurn = true;
            }
            gameCon.movePlayer(gameCon.currentPlayer, lastField, sumOfDice);
        }
    }

    @Test
    public void createPlayers() {
    }*/
}