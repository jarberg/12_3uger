package controller;

import model.board.Board;
import model.board.Field;
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
    private Bank bank;
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
        bank = Bank.getSingleInstance();

        gamecontroller.setupGame();
        board = gamecontroller.getBoard();
        deck = gamecontroller.getDeck();
        playerList = gamecontroller.getPlayerList();

        Player playerOne = playerList.getAllPlayers()[0];
        Player[] players = new Player[2];
        players[0] = playerList.getAllPlayers()[1];
        players[1] = playerList.getAllPlayers()[2];
        drawController = new DrawController(playerOne, players, bank, board, deck, viewcontroller);
    }

    @After
    public void tearDown() {
        gamecontroller = null;
        viewcontroller = null;
        tradecontroller = null;
        board = null;
        deck = null;
        bank = null;
        playerList = null;
        drawController = null;
    }


    /*

    @Test
    public void GetOutOfJailCard() {

        GetOutOfJailCard card = new GetOutOfJailCard("desc", 50);

        assertFalse("dont have getoutofjail card", player.getJailCardStatus());

        drawcontroller.draw(card);

        assertTrue("have getoutofjail card", player.getJailCardStatus());
    }


    @Test
    public void MonopolyJackpotCard () {

        MonopolyJackpotCard card = new MonopolyJackpotCard("desc", 750, 2000);

        assertEquals(1500, player.getBalance());

        player.addToBalance(-1000);
        drawcontroller.draw(card);

        assertEquals(2500, player.getBalance());
    }



    /*


    @Test
    public void MoveToFieldCard() {
    }

    @Test
    public void PayForBuildingsCard() {
    }

/*

    @Test
    public void TeleportAndPayDoubleCard() {

        TeleportAndPayDoubleCard card = new TeleportAndPayDoubleCard("desc", 2);


        drawcontroller.draw(card);

        assertEquals();

        int amount = 25*card.getMultiplier();

        int oldPosition = player.getPosition();

        int firFerry = 5;
        int secFerry = 15;
        int thiFerry = 25;
        int fouFerry = 35;

        if (player.getPosition() < 5 || player.getPosition() > 35) {
            player.setPosition(firFerry);
        } else if (player.getPosition() > 5 && player.getPosition() < 15) {
            player.setPosition(secFerry);
        } else if (player.getPosition() > 15 && player.getPosition() < 25) {
            player.setPosition(thiFerry);
        } else if (player.getPosition() > 25 && player.getPosition() < 35) {
            player.setPosition(fouFerry);
        }

        int newPosition = player.getPosition();

        String positionAsString = String.valueOf(newPosition);
        Field disbutedField = bank.getFieldById(positionAsString);

        viewController.teleportPlayer(player.getName(), oldPosition, newPosition);

        if(bank.hasOwner(disbutedField.getID())){
            Player otherPlayer =  bank.getOwner(positionAsString);
            tradeController.transferAssets(player, otherPlayer, amount);
        } else{
            tradeController.askIfWantToBuy(player, disbutedField);
        }

        //først at den er teleported

        //så om den er ejet

        //betaling

        //købning

    }

*/

    /*
    }

    @Test
    public void GoToJailCard() {

    GoToJail card = new GoToJail("desc",10);

    assertFalse("is not in jail", player.isInJail());

    drawcontroller.draw(card);

    assertTrue("Player is in jail",player.isInJail());
    }

    /*


    */

    @Test
    public void ShouldGiveBirthdayMonies() {
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
    public void ShouldGiveAndTakeMonies() {

        int amount = 10;
        MoneyCard card = new MoneyCard("desc", amount);

        Player player = playerList.getCurrentPlayer();
        int playerStartBalance = player.getBalance();

        
        drawController.draw(card);

        assertEquals(amount+playerStartBalance, player.getBalance());

    }

    /*


    assertEquals(1510,playerList);

    Player player = player.getBalance();

    assertEquals(amount+player.getBalance(),player.getBalance());



    }

        */



    /*

    @Test
    public void MoveAmountCard() {
    }


    */

    }

