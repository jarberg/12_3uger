package model.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp(){
        player = new Player("Bob");
    }

    @After
    public void tearDown(){
        this.player = null;
    }

    @Test
    public void setGetJailCardStatus() {
        assertFalse(player.getJailCardStatus());
        player.setJailCardStatus(true);
        assertTrue(player.getJailCardStatus());
    }

    @Test
    public void setGetPlayerColor() {

        Color colorCheck= Color.CYAN;

        assertNull(player.getPlayerColor());

        player.setPlayerColor(colorCheck);
        assertEquals(colorCheck, player.getPlayerColor());
    }

    @Test
    public void setGetPosition() {

        int testPosition = 5;
        int boardsize = 40;
        assertEquals(0,player.getPosition()%boardsize);
        player.setPositionWithStartMoney(testPosition);
        assertEquals(testPosition,player.getPosition()%boardsize);

        assertEquals(testPosition,(player.getPosition()+boardsize)%boardsize);
    }

    @Test
    public void setGetBrokeStatus() {
        assertFalse(player.getBrokeStatus());
        player.setBrokeStatus(true);
        assertTrue(player.getBrokeStatus());

    }

    @Test
    public void setGetDoubbleTurnStatus() {
        assertFalse(player.getDoubleTurnStatus());
        player.setDoubleTurnStatus(true);
        assertTrue(player.getDoubleTurnStatus());

    }
    //integrated tests
    @Test
    public void setGetBalance() {
        int amount = 5;
        int oldBalance = player.getBalance();

        player.addToBalance(amount);
        assertEquals(oldBalance + amount, player.getBalance());
    }


    @Test
    public void shouldAddToBalance() {
        int beforeBalance = player.getBalance();
        player.addToBalance(100);
        assertEquals(beforeBalance,player.getBalance()-100);
    }

    @Test
    public void shouldAddDoubleThrowTimes() {
        int beforeDoubleThrowNum = player.getDoubleThrowNum();
        player.addDoubleThrowTimes();
        assertEquals(beforeDoubleThrowNum,player.getDoubleThrowNum()-1);
    }

    @Test
    public void shouldResetDoubleThrowTimes() {
        assertEquals(player.getDoubleThrowNum(),0);
    }

    @Test
    public void shouldSetInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
        player.setInJail(false);
        assertTrue(!player.isInJail());
    }

    @Test
    public void shouldSetJailTurn() {
        player.setJailTurn();
        assertEquals(player.getJailTurn(),player.getCurrentTurn());
    }

    @Test
    public void addCurrentTurn() {
        int beforeTurn = player.getCurrentTurn();
        player.addCurrentTurn();
        assertEquals(beforeTurn,player.getCurrentTurn()-1);
    }
}