package model.player;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player("Bob");


    @Test
    public void getName() {
        assertEquals("Bob", player.getName());
    }

    @Test
    public void setGetJailCardStatus() {
        assertEquals(false,player.getJailCardStatus());
        player.setJailCardStatus(true);
        assertEquals(true,player.getJailCardStatus());
    }



    @Test
    public void setGetPlayerColor() {

        Color colorCheck= Color.CYAN;

        assertEquals(null, player.getPlayerColor());

        player.setPlayerColor(colorCheck);
        assertEquals(colorCheck, player.getPlayerColor());
    }

    @Test
    public void setGetPosition() {

        int testPosition =5;
        int boardsize =40;
        assertEquals(0,player.getPosition()%boardsize);
        player.setPosition(testPosition);
        assertEquals(testPosition,player.getPosition()%boardsize);

        assertEquals(testPosition,(player.getPosition()+boardsize)%boardsize);
    }

    @Test
    public void setGetBrokeStatus() {
        assertEquals(false,player.getBrokeStatus());
        player.setBrokeStatus(true);
        assertEquals(true,player.getBrokeStatus());

    }

    @Test
    public void setGetDoubbleTurnStatus() {
        assertEquals(false,player.getDoubleTurnStatus());
        player.setDoubleTurnStatus(true);
        assertEquals(true,player.getDoubleTurnStatus());

    }
    //integrated tests
    @Test
    public void setGetBalance() {
        int newbalance = 5;

        assertEquals(0,player.getBalance());

        player.addToBalance(newbalance);
        assertEquals(newbalance,player.getBalance());
    }

}