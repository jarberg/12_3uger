package controller;

import model.board.Board;
import model.board.Field;
import model.deck.*;
import model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrawControllerTest {

    private GameController gamecontroller = GameController.getSingleInstance();
    private ViewControllerInterface viewcontroller = new ViewControllerStub();
    private Player player = new Player(viewcontroller.getPlayerName());
    private Player[] otherplayerList;
    private Bank bank = new BankStub();
    private Board board;
    private Deck deck;
    private TradeController tradecontroller = TradeController.getSingleInstance();
    private DrawController drawcontroller;

    @Before
    public void setUp() {
        gamecontroller.setViewController(viewcontroller);
        gamecontroller.setupGame();
        gamecontroller.createPlayers();

        player = gamecontroller.getPlayer(0);

        for (int i = 1; i == viewcontroller.getPLayerAmount(); i++) {
            otherplayerList[i] = gamecontroller.getPlayer(i);
        }

        tradecontroller.setViewController(viewcontroller);

        otherplayerList = new Player[viewcontroller.getPLayerAmount() - 1];

        gamecontroller.setupGame();
        drawcontroller = new DrawController(player, otherplayerList, bank, gamecontroller.board, gamecontroller.deck, viewcontroller);


    }

    @After
    public void tearDown() {
        drawcontroller = null;
    }


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

*/

    @Test
    public void TeleportAndPayDoubleCard() {

        TeleportAndPayDoubleCard card = new TeleportAndPayDoubleCard("desc", 2);


        drawcontroller.draw(card);
/*
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
    }

    @Test
    public void GoToJail() {

    GoToJail card = new GoToJail("desc",10);

    assertFalse("is not in jail", player.isInJail());

    drawcontroller.draw(card);

    assertTrue("Player is in jail",player.isInJail());
    }

    /*

    @Test
    public void BirthdayCard() {
    }

    @Test
    public void MoneyCard() {
    }

    @Test
    public void MoveAmountCard() {
    }


    */

    }
