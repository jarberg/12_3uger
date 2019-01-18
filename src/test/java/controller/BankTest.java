package controller;

import model.board.Board;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTest {
    private Bank bank;
    private Board board;
    private PlayerList playerList;

    @Before
    public void setUp(){
        bank = Bank.getSingleInstance();
        LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
        LogicStringCollection logicStringCollection = LogicStringCollection.getSingleInstance();
        board = new Board(logicStringCollection.getFieldsText(), languageStringCollection.getFieldsText());
        int numOfPlayers = 3;
        playerList = new PlayerList(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            Player player = new Player("PlayerName#" + i);
            playerList.addPlayer(i, player);
        }
    }

    @After
    public void tearDown(){
        bank = null;
        board = null;
        playerList = null;
    }

    @Test
    public void shouldSetupFieldOwnerArrayWithPlayerNames() {
        bank.setupFieldOwnerArray(playerList);
        String[][] fieldOwnerArray = bank.getFieldOwnerArray();
        int playerNamePosition = 0;
        for (int i = 0; i < fieldOwnerArray.length; i++) {
            for (int j = playerNamePosition; j < 1 ; j++) {
                assertEquals(playerList.getPlayer(i).getName(), fieldOwnerArray[i][j]);
            }
        }
    }

    @Test
    public void removeFieldOwner() {
    }

    @Test
    public void getPlayerByName() {
    }

    @Test
    public void addFieldToPlayer() {
    }

    @Test
    public void hasOwner() {
    }

    @Test
    public void getOwner() {
    }

    @Test
    public void isOwner() {
    }

    @Test
    public void isOwnerOfAllFieldsOfType() {
    }

    @Test
    public void getNetWorth() {
    }

    @Test
    public void getFieldById() {
    }

    @Test
    public void getFieldByName() {
    }

    @Test
    public void getPlayerFields() {
    }

    @Test
    public void getFieldsWithHousesByPlayer() {
    }

    @Test
    public void getFieldsWithNoHousesByPlayerAndCheckPawnStatus() {
    }

    @Test
    public void getFieldsWithNoHousesByPlayer() {
    }

    @Test
    public void getAmountOfTypeOwned() {
    }

    @Test
    public void getPlayerBuildableFields() {
    }

    @Test
    public void findPlayersWithFieldsWithNoHouses() {
    }

    @Test
    public void getPlayerNamesWithFieldsWithNoHouses() {
    }

    @Test
    public void getPropertyNamesWithNoHousesByPlayer() {
    }

    @Test
    public void getPawnedFieldsByPlayer() {
    }

/*
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
    }*/
}