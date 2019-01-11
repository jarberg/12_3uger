package controller;

import model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController gamecontroller;

    @Before
    public void before(){
        gamecontroller = GameController.getInstance();
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
        gamecontroller.getPlayers()[2].addToBalance(55);
        assertEquals(55, gamecontroller.getPlayers()[2].getBalance());
        gamecontroller.getPlayers()[2].addToBalance(-255);
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

    @Test
    public void checkdiceForDoubleRollTest(){
    }

    @Test
    public void getCurrentPlayerTurn(){

        addPlayer();

        Player currentPlayer = gamecontroller.getPlayers()[0];
        Player nextCurrentPlayer = gamecontroller.getPlayers()[1];
        Player next2CurrentPlayer = gamecontroller.getPlayers()[2];
        Player next3CurrentPlayer = gamecontroller.getPlayers()[3];

        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(currentPlayer,gamecontroller.getCurrentPlayerTurn());

        gamecontroller.nextPlayerTurn();
        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(nextCurrentPlayer,gamecontroller.getCurrentPlayerTurn());

        gamecontroller.nextPlayerTurn();
        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(next2CurrentPlayer,gamecontroller.getCurrentPlayerTurn());

        gamecontroller.nextPlayerTurn();
        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(next3CurrentPlayer,gamecontroller.getCurrentPlayerTurn());

        gamecontroller.nextPlayerTurn();


        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(currentPlayer,gamecontroller.getCurrentPlayerTurn());
        gamecontroller.getCurrentPlayerTurn().setDoubleTurnStatus(true);
        gamecontroller.nextPlayerTurn();
        assertNotEquals(nextCurrentPlayer,gamecontroller.getCurrentPlayerTurn());
        assertEquals(currentPlayer,gamecontroller.getCurrentPlayerTurn());

        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        gamecontroller.nextPlayerTurn();
        System.out.println(gamecontroller.getCurrentPlayerTurn().getName());
        assertEquals(nextCurrentPlayer,gamecontroller.getCurrentPlayerTurn());

    }
    @Test
    public void nextPlayerTurn(){

    }

}