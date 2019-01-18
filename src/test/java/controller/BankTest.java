package controller;

import model.board.Board;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTest {
    private Bank bank;
    private Board board;
    private PlayerList playerList;
    private String[][] fieldOwnerArray;

    @Before
    public void setUp(){
        bank = Bank.getSingleInstance();
        LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
        LogicStringCollection logicStringCollection = LogicStringCollection.getSingleInstance();
        board = new Board(logicStringCollection.getFieldsText(), languageStringCollection.getFieldsText());
        board.setupBoard();
        bank.setBoard(board);
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
        fieldOwnerArray = null;
    }

    @Test
    public void shouldSetupFieldOwnerArrayWithPlayerNames() {
        assertNull(fieldOwnerArray);
        bank.setupFieldOwnerArray(playerList);
        fieldOwnerArray = bank.getFieldOwnerArray();
        int playerNamePosition = 0;
        for (int i = 0; i < fieldOwnerArray.length; i++) {
            for (int j = playerNamePosition; j < 1 ; j++) {
                assertEquals(playerList.getPlayer(i).getName(), fieldOwnerArray[i][j]);
            }
        }
        assertNotNull(fieldOwnerArray);
    }

    @Test
    public void shouldAddFieldToPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        int numOfNullFields = 0;
        for (String[] owner : fieldOwnerArray) {
            for (int j = 0; j < owner.length-1; j++) {
                if (owner[j + 1] == null) {
                    numOfNullFields++;
                }
            }
        }
        int numOfPlayers = fieldOwnerArray.length;
        int numOfNames = fieldOwnerArray.length;
        int playerStorageForFields = fieldOwnerArray[0].length;
        assertEquals(numOfNullFields, (numOfPlayers*playerStorageForFields)-numOfNames);

        for (int i = 0; i < playerList.getAllPlayers().length; i++) {
            Player player = playerList.getPlayer(i);
            Field field = board.getFields()[i];
            bank.addFieldToPlayer(player, field);
        }

        int counter = 0;
        int posOfFirstFieldID = 1;
        for (String[] playerFields : fieldOwnerArray){
            String fieldID = board.getFields()[counter].getID();
            String addedFieldIDToPlayer = playerFields[posOfFirstFieldID];
            assertEquals(fieldID, addedFieldIDToPlayer);
            counter++;
        }
    }

    @Test
    public void shouldRemoveFieldOwner() {
        shouldAddFieldToPlayer();
        Field rodovrevej = board.getFields()[1];
        String rodovrevejID = rodovrevej.getID();
        String[] ownerOfRodovrevej = fieldOwnerArray[1];
        assertEquals(rodovrevejID, ownerOfRodovrevej[1]);

        bank.removeFieldOwner(rodovrevej);
        ownerOfRodovrevej = fieldOwnerArray[1];
        assertNull(ownerOfRodovrevej[1]);
    }

    @Test
    public void shouldReturnTrueIfFieldHasOwnerElseFalse() {
        bank.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        String fieldID = field.getID();
        assertFalse(bank.fieldHasOwner(fieldID));
        shouldAddFieldToPlayer();
        assertTrue(bank.fieldHasOwner(fieldID));
    }

    @Test
    public void shouldGetOwnerOfField() {
        bank.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        String fieldID = field.getID();
        assertNull(bank.getOwnerOfField(fieldID));
        shouldAddFieldToPlayer();
        assertEquals(playerList.getPlayer(1), bank.getOwnerOfField(fieldID));
    }

    @Test
    public void shouldReturnTrueIfPlayerOwnerOfField() {
        bank.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        assertFalse(bank.isPlayerOwner(playerList.getPlayer(1), field));
        shouldAddFieldToPlayer();
        assertTrue(bank.isPlayerOwner(playerList.getPlayer(1), field));
    }

    @Test
    public void shouldReturnTrueIfOwnerOfAllFieldsOfType() {
        bank.setupFieldOwnerArray(playerList);
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Field field = board.getFields()[1];
        assertFalse(bank.isOwnerOfAllFieldsOfType(playerList.getPlayer(1), field));

        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        assertTrue(bank.isOwnerOfAllFieldsOfType(playerList.getPlayer(1), field));
    }

    @Test
    public void shouldGetNetWorth() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        bank.getNetWorth(player);
        assertEquals(1500, bank.getNetWorth(player));
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        assertEquals(1620, bank.getNetWorth(player));
    }

    @Test
    public void shouldGetFieldById() {
        Field rodovrevej = board.getFields()[1];
        String rodovrevejID = rodovrevej.getID();
        assertEquals(rodovrevej, bank.getFieldById(rodovrevejID));
    }

    @Test
    public void shouldGetFieldByName() {
        assertNull(bank.getFieldByName("FAKE_FIELD"));
        Field rodovrevej = board.getFields()[1];
        assertEquals(rodovrevej, bank.getFieldByName(rodovrevej.getTitle()));
    }

    @Test
    public void shouldGetPlayerFields() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        int numOfFieldsOwned = bank.getPlayerFields(player).length;
        assertEquals(0, numOfFieldsOwned);

        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        numOfFieldsOwned = bank.getPlayerFields(player).length;
        assertEquals(2, numOfFieldsOwned);
    }

    @Test
    public void shouldGetFieldsWithHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = bank.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(2 , fieldsLength);

        ((PropertyField) rodovrevej).addBuilding();
        fieldsLength = bank.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(1, fieldsLength);
    }

    @Test
    public void shouldGetFieldsWithNoHousesByPlayerAndCheckPawnStatus() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        ((PropertyField) rodovrevej).addBuilding();
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        int fieldsLength = bank.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length;
         assertEquals(0, fieldsLength);

        ((PropertyField) hvidovrevej).setPawnedStatus(false);
        fieldsLength = bank.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length;
        assertEquals(1, fieldsLength);
    }

    @Test
    public void shouldGetFieldsWithNoHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = bank.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(2, fieldsLength);

        ((PropertyField) rodovrevej).addBuilding();
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        fieldsLength = bank.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(1, fieldsLength);

        ((PropertyField) hvidovrevej).addBuilding();
        fieldsLength = bank.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(0, fieldsLength);
    }

    @Test
    public void getAmountOfTypeOwned() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field valby = board.getFields()[8];
        int amountOfTypeOwned = bank.getAmountOfTypeOwned(player, valby);
        assertEquals(0, amountOfTypeOwned);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        amountOfTypeOwned = bank.getAmountOfTypeOwned(player, rodovrevej);
        assertEquals(2, amountOfTypeOwned);
    }

    @Test
    public void shouldGetPlayerBuildableFields() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        int numOfBuildableFields = bank.getPlayerBuildableFields(player).length;
        assertEquals(0, numOfBuildableFields);

        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        numOfBuildableFields = bank.getPlayerBuildableFields(player).length;
        assertEquals(2, numOfBuildableFields);
    }

    @Test
    public void shouldFindPlayersWithFieldsWithNoHouses() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);

        Player player2 = playerList.getPlayer(2);
        Field valby = board.getFields()[8];
        Field allegade = board.getFields()[9];
        bank.addFieldToPlayer(player2, valby);
        bank.addFieldToPlayer(player2, allegade);
        int numOfPlayers = bank.findPlayersWithFieldsWithNoHouses().length;
        assertEquals(2, numOfPlayers);

        ((PropertyField) valby).addBuilding();
        ((PropertyField) allegade).addBuilding();
        numOfPlayers = bank.findPlayersWithFieldsWithNoHouses().length;
        assertEquals(1, numOfPlayers);
    }

    @Test
    public void shouldGetPlayerNamesWithFieldsWithNoHouses() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);

        Player player2 = playerList.getPlayer(2);
        Field valby = board.getFields()[8];
        Field allegade = board.getFields()[9];
        bank.addFieldToPlayer(player2, valby);
        bank.addFieldToPlayer(player2, allegade);
        int numOfPlayerNames = bank.getPlayerNamesWithFieldsWithNoHouses().length;
        assertEquals(2, numOfPlayerNames);

        ((PropertyField) valby).addBuilding();
        ((PropertyField) allegade).addBuilding();
        numOfPlayerNames = bank.getPlayerNamesWithFieldsWithNoHouses().length;
        assertEquals(1, numOfPlayerNames);
    }

    @Test
    public void getPropertyNamesWithNoHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);

        String[] propertyNames = bank.getPropertyNamesWithNoHousesByPlayer(player);
        assertEquals("Roedovrevej", propertyNames[0]);
        assertEquals("Hvidovre", propertyNames[1]);

        ((PropertyField) rodovrevej).addBuilding();
        propertyNames = bank.getPropertyNamesWithNoHousesByPlayer(player);
        assertEquals("Hvidovre", propertyNames[0]);
    }

    @Test
    public void getPawnedFieldsByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        bank.addFieldToPlayer(player, rodovrevej);
        bank.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = bank.getPawnedFieldsByPlayer(player).length;
        assertEquals(0, fieldsLength);

        ((PropertyField) rodovrevej).setPawnedStatus(true);
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        fieldsLength = bank.getPawnedFieldsByPlayer(player).length;
        assertEquals(2, fieldsLength);
    }
}