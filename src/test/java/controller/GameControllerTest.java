package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {

    GameController gamecontroller = GameController.getInstance();

    @Before
    public void before(){
        gamecontroller.GodMode(true);
    }
    @After
    public void After(){
        gamecontroller = null;
    }

    @Test
    public void createBoard() {
        gamecontroller.createBoard();

        assertEquals(40, gamecontroller.getBoard().length);
        for (int i = 0; i < gamecontroller.getBoard().length; i++) {
            assertEquals(null, gamecontroller.getBoard()[i]);
        }
    }

    @Test
    public void getPlayers() {
        gamecontroller.getPlayers();
    }

    @Test
    public void createPlayerList() {
        gamecontroller.createPlayerList(4);
        assertEquals(4, gamecontroller.getPlayers().length);
        for (int i = 0; i < gamecontroller.getPlayers().length; i++) {
            assertEquals(null, gamecontroller.getPlayers()[i]);
        }
    }

    @Test
    public void addPlayer() {

        createPlayerList();

        String[] names = new String[]{"Bob","Dylan","Chump","Bro"};
        for (int i = 0; i < gamecontroller.getPlayers().length; i++) {
            gamecontroller.addPlayer(names[i],i);
        }
        for (int i = 0; i < gamecontroller.getPlayers().length ; i++) {
            assertEquals(names[i], gamecontroller.getPlayers()[i].getName());
        }
    }

    @Test
    public void changePlayerBalance() {

        addPlayer();
        assertEquals(0, gamecontroller.getPlayers()[2].getBalance());
        gamecontroller.getPlayers()[2].addBalance(55);
        assertEquals(55, gamecontroller.getPlayers()[2].getBalance());
        gamecontroller.getPlayers()[2].addBalance(-255);
        assertEquals(0, gamecontroller.getPlayers()[2].getBalance());
    }

    @Test
    public void movePlayer() {

        addPlayer();
        for (int i = 0; i < gamecontroller.getPlayers().length ; i++) {
            assertEquals(0, gamecontroller.getPlayers()[i].getPosition());
        }
        for (int i = 0; i < gamecontroller.getPlayers().length ; i++) {
            gamecontroller.getPlayers()[i].setPosition(i+1*2);
        }
        for (int i = 0; i < gamecontroller.getPlayers().length ; i++) {
            assertEquals((i+1*2),gamecontroller.getPlayers()[i].getPosition());
        }
    }
}