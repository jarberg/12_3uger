package controller;

import model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController gamecontroller = GameController.getInstance();
    GameLogic gamelogic = gamecontroller.getGameLogic();

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
        assertEquals("Start", gamecontroller.getBoard()[0].getTitle());

    }

    @Test
    public void getPlayers() {
        gamecontroller.getPlayers();
    }

    @Test
    public void createPlayerList() {
        gamelogic.createPlayerList(4);
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

        assertEquals(currentPlayer.getName(),gamelogic.getCurrentPlayer().getName());

        gamelogic.setNextPlayer();

        assertEquals(nextCurrentPlayer.getName(),gamelogic.getCurrentPlayer().getName());

        gamelogic.setNextPlayer();

        assertEquals(next2CurrentPlayer.getName(),gamelogic.getCurrentPlayer().getName());

        gamelogic.setNextPlayer();

        assertEquals(next3CurrentPlayer.getName(),gamelogic.getCurrentPlayer().getName());

        gamelogic.setNextPlayer();



        assertEquals(currentPlayer.getName(),gamelogic.getCurrentPlayer().getName());
        gamelogic.getCurrentPlayer().setDoubleTurnStatus(true);
        gamelogic.setNextPlayer();
        assertNotEquals(nextCurrentPlayer.getName(),gamelogic.getCurrentPlayer());
        assertEquals(currentPlayer.getName(),gamelogic.getCurrentPlayer().getName());


        gamelogic.setNextPlayer();

        assertEquals(nextCurrentPlayer.getName(),gamelogic.getCurrentPlayer().getName());

    }
    @Test
    public void nextPlayerTurn(){

    }

}