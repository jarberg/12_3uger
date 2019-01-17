package controller;

import model.player.Player;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {


    private GameController gamecontroller = GameController.getSingleInstance();
    private ViewControllerInterface viewController = new ViewControllerStub();
    private LogicStringCollection logicCollection = LogicStringCollection.getSingleInstance();
    private LanguageStringCollection languageCollection = LanguageStringCollection.getSingleInstance();
    private TradeController tradeController = TradeController.getSingleInstance();

    @Before
    public void before(){
        gamecontroller.setViewController( viewController);
        tradeController.setViewController(viewController);
        setupGame();
    }

    @After
    public void After(){
        gamecontroller = null;
    }

    @Test
    public void createPlayers() {

    }

    @Test
    public void setupLanguage(){

        viewController.showEmptyGUI();
        gamecontroller.setFilepathLanguage(viewController.getUserLanguage());
        this.logicCollection = LogicStringCollection.getSingleInstance();
        this.languageCollection = LanguageStringCollection.getSingleInstance();

    }

    @Test
    public void setupGame(){

        gamecontroller.playerAmount = gamecontroller.getPlayerAmount();
        gamecontroller.setFilepathLanguage("danish");
        gamecontroller.createPlayers();
        gamecontroller.createDeck();
        setupLanguage();
        gamecontroller.makePlayerChooseCar();
        gamecontroller.createBoard(logicCollection.getFieldsText(), languageCollection.getFieldsText());

        assertEquals("Start",gamecontroller.board.getFields()[0].getTitle());
        assertEquals("Raadhuspladsen",gamecontroller.board.getFields()[39].getTitle());

        gamecontroller.createDeck();
        gamecontroller.setupBank();
        gamecontroller.showGameBoard();
        gamecontroller.addPlayersToGUI();

        assertEquals(viewController.getPLayerAmount(),gamecontroller.playerAmount);

        for (int i = 1; i ==gamecontroller.playerAmount ; i++) {
            if(i==1) {
                assertEquals("Test", gamecontroller.getPlayer(i).getName());
            }
            if(i>1) {
                assertEquals("Test#"+i, gamecontroller.getPlayer(i).getName());
            }
        }

        for (int i = 0; i <gamecontroller.playerAmount ; i++) {
            assertEquals(0,gamecontroller.getPlayer(i).getPosition());
        }
        for (int i = 0; i <gamecontroller.playerAmount ; i++) {
            assertEquals(1500,gamecontroller.getPlayer(i).getBalance());
        }
    }

    @Test
    public void playTurn(){

        gamecontroller.currentTurn++;



        gamecontroller.endTurn = false;
        gamecontroller.currentPlayer = gamecontroller.playerlist.getCurrentPlayer();

        gamecontroller.checkIfinJailBeforeMoving();
        gamecontroller.checkIfPassedStart();
        resolveField();

        while(!gamecontroller.endTurn) {
            gamecontroller.playerOptions(gamecontroller.getChoices(gamecontroller.currentPlayer),gamecontroller.currentPlayer);
        }

        gamecontroller.setNextPlayer();


        gamecontroller.lastTurn = gamecontroller.currentTurn;

        //gamecontroller.currentPlayer.setBrokeStatus(true);


    }

    @Test
    public void playGame(){


        setupGame();
        viewController.showFieldMessage(gamecontroller.playerlist.getCurrentPlayer().getName(), languageCollection.getMenu()[11]);
        while(!gamecontroller.checkIfAllBroke()){
            playTurn();
        }
        gamecontroller.checkForWinner();

    }

    @Test
    public void testCase01(){
        setupGame();
    }

    @Test
    public void resolveField(){
        gamecontroller.currentPlayer= gamecontroller.playerlist.getCurrentPlayer();
        int position = gamecontroller.currentPlayer.getPosition();
        gamecontroller.currentField = gamecontroller.board.getFields()[position];

        FieldVisitor fieldVisitor = new FieldVisitor(gamecontroller.currentPlayer, gamecontroller.getPlayersButPlayer(gamecontroller.currentPlayer), gamecontroller.deck, gamecontroller.board, viewController);
        gamecontroller.currentField.accept(fieldVisitor);
    }

    @Test
    public void checkIfinJailBeforeMoving(){
        gamecontroller.currentPlayer= gamecontroller.playerlist.getCurrentPlayer();
        if(!gamecontroller.currentPlayer.isInJail()) {
            gamecontroller.rollAndShowDice(gamecontroller.currentPlayer);
            int lastField = gamecontroller.currentPlayer.getPosition();
            int sumOfDice = gamecontroller.dice.getDieOneValue() + gamecontroller.dice.getDieTwoValue();

            if(gamecontroller.currentPlayer.getDoubleThrowNum()>2){
                gamecontroller.currentPlayer.setInJail(true);
                sumOfDice = (40-lastField+10)%40;
                gamecontroller.endTurn = true;
            }
            gamecontroller.movePlayer(gamecontroller.currentPlayer, lastField, sumOfDice);
        }
    }
}