package controller;

import model.board.Board;
import model.board.Field;
import model.deck.Card;
import model.deck.Deck;
import model.deck.MonopolyJackpotCard;
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

        for (int i = 1; i == viewcontroller.getPLayerAmount() ; i++) {
            otherplayerList[i] = gamecontroller.getPlayer(i);
        }

        tradecontroller.setViewController(viewcontroller);

        otherplayerList = new Player[viewcontroller.getPLayerAmount()-1];

        gamecontroller.setupGame();
        drawcontroller = new DrawController(player,otherplayerList,bank,gamecontroller.board,gamecontroller.deck,viewcontroller);


    }

    @After
    public void tearDown() {
        drawcontroller = null;
    }


   /*
    @Test
    public void GetOutOfJailCard() {
        //teste om use af card virker

        boolean playerInJail = true;
        boolean getOutOfJailCard = false;

        if (getOutOfJailCard == true) {
            playerInJail = false;
            System.out.println( "player is not in jail");
            assertFalse(playerInJail);
        } else if (getOutOfJailCard == false) {
            playerInJail = true;
            System.out.println("player is in jail");
            assertTrue(playerInJail);
        }

    }

     */


    @Test
    public void MonopolyJackpotCard() {

        MonopolyJackpotCard card = new MonopolyJackpotCard("desc",750,2000);


        player.addToBalance(-500);
        drawcontroller.draw(card);





        //assertEquals(1500,player.getBalance());


    }



    /*


    @Test
    public void MoveToFieldCard() {
    }

    @Test
    public void PayForBuildingsCard() {
    }

    @Test
    public void TeleportAndPayDoubleCard() {
    }

    @Test
    public void GoToJail() {
    }

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