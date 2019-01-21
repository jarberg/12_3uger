package controller;

import model.board.Board;
import model.board.FerryField;
import model.board.Field;
import model.board.PropertyField;
import model.deck.*;
import model.player.Player;
import model.player.PlayerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class DrawControllerTest {

    private GameController gamecontroller;
    private ViewControllerInterface viewcontroller;
    private PlayerFieldRelationController playerFieldRelationController;
    private Board board;
    private Deck deck;
    private PlayerList playerList;
    private TradeController tradecontroller;
    private DrawController drawController;

    @Before
    public void setUp() {
        gamecontroller = GameController.getSingleInstance();
        viewcontroller = new ViewControllerStub();
        gamecontroller.setViewController(viewcontroller);

        tradecontroller = TradeController.getSingleInstance();
        tradecontroller.setViewController(viewcontroller);
        playerFieldRelationController = PlayerFieldRelationController.getSingleInstance();

        gamecontroller.setupGame();
        board = gamecontroller.getBoard();
        deck = gamecontroller.getDeck();
        playerList = gamecontroller.getPlayerList();

        Player playerOne = playerList.getAllPlayers()[0];
        Player[] players = new Player[2];
        players[0] = playerList.getAllPlayers()[1];
        players[1] = playerList.getAllPlayers()[2];
        drawController = new DrawController(playerOne, players, playerFieldRelationController, board, deck, viewcontroller);
    }

    @After
    public void tearDown() {
        gamecontroller = null;
        viewcontroller = null;
        tradecontroller = null;
        board = null;
        deck = null;
        playerFieldRelationController = null;
        playerList = null;
        drawController = null;
    }


    @Test
    public void shouldGetOutOfJail() {

        GetOutOfJailCard card = new GetOutOfJailCard("desc", 50);

        Player player = playerList.getCurrentPlayer();

        assertFalse("dont have getoutofjail card", player.getJailCardStatus());

        drawController.draw(card);

        assertTrue("have getoutofjail card", player.getJailCardStatus());
    }


    @Test
    public void shouldGetMonopolyJackpot () {

        MonopolyJackpotCard card = new MonopolyJackpotCard("desc", 750, 2000);

        Player player = playerList.getCurrentPlayer();

        assertEquals(1500, player.getBalance());

        player.addToBalance(-1000);
        drawController.draw(card);

        assertEquals(2500, player.getBalance());
    }


    @Test
    public void shouldMoveToField() {

        int destinationField = 25;

        MoveToFieldCard card = new MoveToFieldCard("desc", destinationField);

        Player player = playerList.getCurrentPlayer();

        drawController.draw(card);

        assertEquals(destinationField, player.getPosition());

    }


    @Test
    public void shouldPayForBuildings() {
        Player player = playerList.getCurrentPlayer();
        setPlayerOwnAllFields(player);

        //Player has 2 hotels and 4 houses;
        for (int i = 0; i < 5; i++) {
            ((PropertyField)board.getFields()[6]).addBuilding();
            ((PropertyField)board.getFields()[8]).addBuilding();
            if(((PropertyField)board.getFields()[9]).getBuildingCount() != 4)
                ((PropertyField)board.getFields()[9]).addBuilding();
        }

        int house = 4;
        int hotel = 2;

        int pricePrHouse = 25;
        int pricePrHotel = 125;

        PayForBuildingsCard card = new PayForBuildingsCard("desc",pricePrHouse,pricePrHotel);

        int housePrice = card.getHouse();
        int hotelPrice = card.getHotel();

        int amount = housePrice * house + hotelPrice * hotel;

        int playerStartBalance = player.getBalance();

        drawController.draw(card);

        assertEquals(playerStartBalance-amount,player.getBalance());
    }

    private void setPlayerOwnAllFields(Player player){
        for(Field field : board.getFields()){
            if(field instanceof PropertyField){
                playerFieldRelationController.addFieldToPlayer(player,field);
            }
        }
    }


    @Test
    public void shouldTeleportAndPayDouble() {

        int destinationField = 5;
        Field field = playerFieldRelationController.getFieldById(String.valueOf(destinationField));
        assertTrue(field instanceof FerryField);
        int amount = ((FerryField)field).getRent(1);

        TeleportAndPayDoubleCard card = new TeleportAndPayDoubleCard("desc", 2);

        int multiplied = card.getMultiplier();

        Player player = playerList.getCurrentPlayer();
        int playerStartBalance = player.getBalance();

        Player playerTwo = playerList.getAllPlayers()[1];
        int playerTwoBalance = playerTwo.getBalance();

        playerFieldRelationController.addFieldToPlayer(playerTwo,field);

        drawController.draw(card);

        assertEquals(destinationField, player.getPosition());

        assertEquals(playerStartBalance-(amount*multiplied),player.getBalance());

        assertEquals(playerTwoBalance+(amount*multiplied),playerTwo.getBalance());

    }



    @Test
    public void shouldGoToJail() {

    GoToJail card = new GoToJail("desc",10);

    Player player = playerList.getCurrentPlayer();

    assertFalse("is not in jail", player.isInJail());

    drawController.draw(card);

    assertTrue("Player is in jail",player.isInJail());
    }


    @Test
    public void shouldGiveBirthdayMonies() {
        int amount = 25;

        BirthdayCard card = new BirthdayCard("desc", amount);

        Player playerOne = playerList.getAllPlayers()[0];
        int playerOneStartBalance = playerOne.getBalance();

        Player playerTwo = playerList.getAllPlayers()[1];
        int playerTwoStartBalance = playerTwo.getBalance();

        Player playerThree = playerList.getAllPlayers()[2];
        int playerThreeStartBalance = playerThree.getBalance();

        drawController.draw(card);

        assertEquals(amount*2 + playerOneStartBalance, playerOne.getBalance());

        assertEquals(playerTwoStartBalance-amount, playerTwo.getBalance());

        assertEquals(playerThreeStartBalance-amount, playerThree.getBalance());


    }


    @Test
    public void shouldGiveAndTakeMonies() {

        int amount = 10;
        MoneyCard card = new MoneyCard("desc", amount);

        Player player = playerList.getCurrentPlayer();
        int playerStartBalance = player.getBalance();

        
        drawController.draw(card);

        assertEquals(amount+playerStartBalance, player.getBalance());

    }

    @Test
    public void shouldMoveAmount() {

        int moveAmount = 5;
        MoveAmountCard card = new MoveAmountCard("desc", moveAmount);

        Player player = playerList.getCurrentPlayer();
        int playerOldPosition = player.getPosition();

        drawController.draw(card);

        assertEquals(moveAmount+playerOldPosition, player.getPosition());

    }


}

