package model.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerListTest {

    private PlayerList playerlist;
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void setUp(){
        playerlist = new PlayerList(3);
        player1 = new Player("Bob");
        player2 = new Player("Dylan");
        player3 = new Player("Bae");
    }

    @After
    public void tearDown(){
        playerlist = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    @Test
    public void getPlayer() {
        for (int i = 0; i <playerlist.getAllPlayers().length ; i++) {
            assertNull(playerlist.getPlayer(i));
        }
    }

    @Test
    public void shouldAllowBetweenThreeAndSixPlayers(){
        for (int i = 3; i <= 6; i++) {
            PlayerList playerList = new PlayerList(i);
            for (int j = 0; j < i; j++) {
                playerList.addPlayer(j, new Player("Test"));
                assertNotNull(playerList.getPlayer(j));
            }
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
            assertNull(playerlist.getAllPlayers()[i]);
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

        assertNull(playerlist.getCurrentPlayer());
        addPlayer();
        assertEquals(player1, playerlist.getCurrentPlayer());
    }

    @Test
    public void setNextPlayer() {
        addPlayer();

        assertEquals("Bob",playerlist.getCurrentPlayer().getName());
        playerlist.setNextPlayer();

        assertEquals("Dylan",playerlist.getCurrentPlayer().getName());;
        playerlist.setNextPlayer();

        assertEquals("Bae",playerlist.getCurrentPlayer().getName());
        playerlist.setNextPlayer();

        assertEquals("Bob",playerlist.getCurrentPlayer().getName());
        playerlist.getCurrentPlayer().setDoubleTurnStatus(true);
        playerlist.setNextPlayer();

        assertEquals("Bob",playerlist.getCurrentPlayer().getName());
        playerlist.setNextPlayer();

        assertEquals("Dylan",playerlist.getCurrentPlayer().getName());
    }
}