package controller;

import model.player.Player;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTest {
/*
    int playerAmount =4;
    private GameController gamecontroller = GameController.getInstance();

    private GameLogic gamelogic = gamecontroller.getGameLogic();
/*
    @Before
    public void before(){
    gamecontroller.GodMode(true);
    gamecontroller.setupLanguage();

    }

    @After
    public void After(){
        gamecontroller = null;
    }

    @Test
    public void createPlayers() {
        gamecontroller.setPlayerAmount(playerAmount);
        gamecontroller.createPlayers();
        this.gamelogic = gamecontroller.getGameLogic();
        assertEquals(4, gamelogic.getAllPlayers().length);
        for (int i = 0; i < gamelogic.getAllPlayers().length; i++) {
            assertEquals(("test"+i), gamelogic.getAllPlayers()[i].getName());
        }
    }


    @Test
    public void addPlayer() {

        createPlayers();

        String[] names = new String[]{"Bob","Dylan","Chump","Bro"};
        for (int i = 0; i < gamelogic.getAllPlayers().length; i++) {
            gamecontroller.addPlayer(names[i],i);
        }
        for (int i = 0; i < gamelogic.getAllPlayers().length ; i++) {
            assertEquals(names[i], gamelogic.getAllPlayers()[i].getName());
        }
    }

    @Test
    public void changePlayerBalance() {

        addPlayer();
        assertEquals(0, gamelogic.getAllPlayers()[2].getBalance());
        gamelogic.getAllPlayers()[2].addToBalance(55);
        assertEquals(55, gamelogic.getAllPlayers()[2].getBalance());
        gamelogic.getAllPlayers()[2].addToBalance(-255);
        assertEquals(0, gamelogic.getAllPlayers()[2].getBalance());
    }

    @Test
    public void movePlayer() {

        addPlayer();
        for (int i = 0; i < gamelogic.getAllPlayers().length ; i++) {
            assertEquals(0, gamelogic.getAllPlayers()[i].getPosition());
        }
        for (int i = 0; i < gamelogic.getAllPlayers().length ; i++) {
            gamelogic.getAllPlayers()[i].setPosition(i+1*2);
        }
        for (int i = 0; i < gamelogic.getAllPlayers().length ; i++) {
            assertEquals((i+1*2),gamelogic.getAllPlayers()[i].getPosition());
        }
    }

    @Test
    public void checkdiceForDoubleRollTest(){
    }

    @Test
    public void getCurrentPlayerTurn(){

        addPlayer();

        Player currentPlayer = gamelogic.getAllPlayers()[0];
        Player nextCurrentPlayer = gamelogic.getAllPlayers()[1];
        Player next2CurrentPlayer = gamelogic.getAllPlayers()[2];
        Player next3CurrentPlayer = gamelogic.getAllPlayers()[3];

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
    */

}