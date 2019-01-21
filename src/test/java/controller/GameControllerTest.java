package controller;

import model.board.Field;
import model.board.PropertyField;
import model.board.JailField;
import model.player.Player;
import model.player.PlayerList;
import utilities.FileReader;
import utilities.LanguageStringCollection;
import utilities.LogicStringCollection;
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
    private FileReader fileReader;

    @Before
    public void setUp(){
        gameCon = GameController.getSingleInstance();
        viewController = new ViewControllerStub();

        gameCon.setViewController(viewController);
        tradeController = TradeController.getSingleInstance();
        tradeController.setViewController(viewController);

        languageCollection = LanguageStringCollection.getSingleInstance();
        logicCollection = LogicStringCollection.getSingleInstance();
        fileReader = FileReader.getSingleInstance();
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
        fileReader = null;
    }

    @Test
    public void shouldChangeLanguage(){
        fileReader.setDefaultLanguage();
        languageCollection.getTextFromFileReader();
        String originalMenu = languageCollection.getMenu()[0];
        String originalCard = languageCollection.getChanceCard()[0][1];
        String originalField = languageCollection.getFieldsText()[7][1];
        gameCon.setupGame();
        String newMenu = languageCollection.getMenu()[0];
        String newCard = languageCollection.getChanceCard()[0][1];
        String newField = languageCollection.getFieldsText()[7][1];

        assertNotEquals(originalMenu, newMenu);
        assertNotEquals(originalCard, newCard);
        assertNotEquals(originalField, newField);
    }

    @Test
    public void shouldCreateModelFromLanguage(){

    }

    @Test
    public void shouldCreateFieldVisitorAndDrawController(){
        gameCon.setupGame();

        PlayerList playerList = gameCon.getPlayerList();
        Player currentPlayer = playerList.getAllPlayers()[0];
        Player[] otherPlayers = playerList.getPlayersButPlayer(currentPlayer);
        fieldVisitor = new FieldVisitor(currentPlayer, otherPlayers, gameCon.getDeck(), gameCon.getBoard(), viewController);
        drawController = new DrawController(currentPlayer, otherPlayers, gameCon.getPlayerFieldRelationController(), gameCon.getBoard(), gameCon.getDeck(), viewController);
    }


    @Test
    public void shouldLetPlayerLeavePrisonIfPaying50(){
        gameCon.setupGame();
        PlayerList playerList = gameCon.getPlayerList();
        Player player = playerList.getCurrentPlayer();
        player.addCurrentTurn();
        player.setInJail(true);
        player.setJailTurn();
        JailField jail = new JailField("ID", "Title", "Subtitle", "message", Color.black, 50);
        gameCon.payToLeaveJail(jail);
        assertEquals(1450, player.getBalance());
        assertFalse(player.getJailCardStatus());
    }
    @Test
    public void shouldStartPlayersOnStart(){
        gameCon.setupGame();
        for(Player player : gameCon.getPlayerList().getAllPlayers()){
            assertEquals(0, player.getPosition());
        }
    }

    @Test
    public void shouldStartWithExpectedAmount(){
        int expectedAmount = 1500;
        gameCon.setupGame();
        for(Player player : gameCon.getPlayerList().getAllPlayers()){
            assertEquals(expectedAmount, player.getBalance());
        }
    }

    @Test
    public void shouldMoveInRightDirection(){
        int reps = 1000;
        for (int i = 0; i < reps; i++) {
            gameCon.setupGame();
            Player player = gameCon.getPlayerList().getCurrentPlayer();
            int initialPosition = player.getPosition();
            gameCon.checkIfinJailBeforeMoving();
            int laterPosition = player.getPosition();
            assertTrue(initialPosition < laterPosition);
        }
    }

    @Test
    public void shouldStartWithNoOwner(){
        gameCon.setupGame();
        for(Field field : gameCon.getBoard().getFields()){
            assertNull(gameCon.getPlayerFieldRelationController().getOwnerOfField(field.getID()));
        }
    }

    @Test
    public void shouldBeGivenOptionToBuyField(){
        int testAmount = 1000;
        int counter = 0;
        int counterTwo = 0;
        for (int i = 0; i < testAmount; i++) {
            gameCon.setupGame();
            Player player = gameCon.getPlayerList().getCurrentPlayer();
            if(viewController instanceof ViewControllerStub){
                ((ViewControllerStub) viewController).setChoice("Ja");
            }
            gameCon.checkIfinJailBeforeMoving();
            gameCon.resolveField();
            String[][] options = gameCon.getChoices(player);
            Field field = gameCon.getCurrentField();
            if(field instanceof PropertyField){
                boolean hasBoughtField = options.length > 1;
                if(hasBoughtField){
                    counter++;
                }
                counterTwo++;
            }
        }
        assertNotEquals(0, counter);
        assertEquals(counter, counterTwo);
    }

}