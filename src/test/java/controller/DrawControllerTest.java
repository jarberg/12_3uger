package controller;

import model.board.Board;
import model.board.Field;
import model.deck.Deck;
import model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrawControllerTest {

    private DrawController drawcontroller;
    private Player player;
    private Player[] otherPlayers;
    private Bank bank;
    private Board board;
    private Deck deck;

    @Before
    public void setUp() {
        drawcontroller = new DrawController(player,otherPlayers,bank,board,deck);
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