package model.player;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerListTest {
    int playerAmount = 3;
    Player player1 = new Player("Bob");
    Player player2 = new Player("Dylan");
    Player player3 = new Player("Bae");

    PlayerList playerlist = new PlayerList(playerAmount);

    @Test
    public void getPlayer() {
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            assertEquals(null, playerlist.getPlayer(i));
        }
    }

    @Test
    public void addPlayer() {

        getPlayer();

        playerlist.addPlayer(0,player1);
        playerlist.addPlayer(1,player2);
        playerlist.addPlayer(2,player3);

        assertEquals(player1, playerlist.getPlayer(0));
        assertEquals(player2, playerlist.getPlayer(1));
        assertEquals(player3, playerlist.getPlayer(2));
    }


    @Test
    public void getAllPlayers() {
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            assertEquals(null, playerlist.getAllPlayers()[i]);
        }

        addPlayer();

        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            if (i == 0) {
                assertEquals(player1.getName(), playerlist.getPlayer(i).getName());
            }
            if (i == 1) {
                assertEquals(player2.getName(), playerlist.getPlayer(i).getName());
            }
            if (i == 2) {
                assertEquals(player3.getName(), playerlist.getPlayer(i).getName());
            }
        }
    }

    @Test
    public void getCurrentPlayer() {

        assertEquals(null, playerlist.getCurrentPlayer());
        addPlayer();
        assertEquals(player1, playerlist.getCurrentPlayer());
    }

    @Test
    public void setNextPlayer() {
        getCurrentPlayer();

        playerlist.setNextPlayer();

        assertEquals(player2.getName(),playerlist.getCurrentPlayer().getName());
        assertEquals(player3.getName(),playerlist.getAllPlayers()[1].getName());
        assertEquals(player1.getName(),playerlist.getAllPlayers()[2].getName());
    }
}