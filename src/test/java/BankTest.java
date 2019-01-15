import model.board.Board;
import model.board.Field;
import model.player.Player;
import model.player.PlayerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    Board board;
    PlayerList playerList;

    Bank bank;

    @Before
    public void setUp(){

        Player player1 = new Player("Bob");
        Player player2 = new Player("Dylan");
        Player player3 = new Player("Bae");

        int[][] fieldLogic = {
                {1,2,1,1,1,1,1,1,1,1,1},
                {2,2,1,1,1,1,1,1,1,1,1},
                {3,2,2,1,1,1,1,1,1,1,1},
                {4,2,2,1,1,1,1,1,1,1,1},
                {5,2,2,1,1,1,1,1,1,1,1},
                {6,2,3,1,1,1,1,1,1,1,1},
                {7,2,3,1,1,1,1,1,1,1,1},
                {8,2,3,1,1,1,1,1,1,1,1},
                {9,2,8,1,1,1,1,1,1,1,1},
                {10,2,8,1,1,1,1,1,1,1,1}
        };

        String[][] fieldInfo = {
            {"1","2","1","1"},
            {"2","2","1","1"},
            {"3","2","2","1"},
            {"4","2","2","1"},
            {"5","2","2","1"},
            {"6","2","3","1"},
            {"7","2","3","1"},
            {"8","2","3","1"},
            {"9","2","8","1"},
            {"10","2","8","1"}
        };

        board = new Board();
        board.setupBoard(fieldLogic,fieldInfo);

        playerList = new PlayerList(3);
        playerList.addPlayer(0,player1);
        playerList.addPlayer(1,player2);
        playerList.addPlayer(2,player3);
        bank = new Bank(playerList,board);
    }

    @After
    public void tearDown(){
        playerList = null;
        board = null;
        bank = null;
    }

    @Test
    public void shouldAddFieldToPlayer() {
        Field testField = board.getFields()[1];
        bank.addFieldToPlayer(playerList.getPlayer(1),board.getFields()[1]);

        assertEquals(bank.getInfo(1,1),testField.getID());
    }

    @Test
    public void shouldGetOwner() {
    }

    @Test
    public void shouldReturnIfPlayerIsOwnerOfAllFieldsOfType() {
    }
}