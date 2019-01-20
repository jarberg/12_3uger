package controller;

import model.board.Board;
import model.board.Field;
import model.board.Ownable;
import model.board.PropertyField;
import model.player.Player;
import model.player.PlayerList;
import utilities.LanguageStringCollection;
import utilities.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerFieldRelationControllerTest {
    private PlayerFieldRelationController playerFieldRelationController;
    private Board board;
    private PlayerList playerList;
    private String[][] fieldOwnerArray;

    @Before
    public void setUp(){
        playerFieldRelationController = PlayerFieldRelationController.getSingleInstance();
        LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
        LogicStringCollection logicStringCollection = LogicStringCollection.getSingleInstance();
        board = new Board(logicStringCollection.getFieldsText(), languageStringCollection.getFieldsText());
        board.setupBoard();
        playerFieldRelationController.setBoard(board);
        int numOfPlayers = 3;
        playerList = new PlayerList(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            Player player = new Player("PlayerName#" + i);
            playerList.addPlayer(i, player);
        }
    }

    @After
    public void tearDown(){
        playerFieldRelationController = null;
        board = null;
        playerList = null;
        fieldOwnerArray = null;
    }

    @Test
    public void shouldSetupFieldOwnerArrayWithPlayerNames() {
        assertNull(fieldOwnerArray);
        playerFieldRelationController.setupFieldOwnerArray(playerList);
        fieldOwnerArray = playerFieldRelationController.getFieldOwnerArray();
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
            playerFieldRelationController.addFieldToPlayer(player, field);
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

        playerFieldRelationController.removeFieldOwner(rodovrevej);
        ownerOfRodovrevej = fieldOwnerArray[1];
        assertNull(ownerOfRodovrevej[1]);
    }

    @Test
    public void shouldReturnTrueIfFieldHasOwnerElseFalse() {
        playerFieldRelationController.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        String fieldID = field.getID();
        assertFalse(playerFieldRelationController.fieldHasOwner(fieldID));
        shouldAddFieldToPlayer();
        assertTrue(playerFieldRelationController.fieldHasOwner(fieldID));
    }

    @Test
    public void shouldGetOwnerOfField() {
        playerFieldRelationController.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        String fieldID = field.getID();
        assertNull(playerFieldRelationController.getOwnerOfField(fieldID));
        shouldAddFieldToPlayer();
        assertEquals(playerList.getPlayer(1), playerFieldRelationController.getOwnerOfField(fieldID));
    }

    @Test
    public void shouldReturnTrueIfPlayerOwnerOfField() {
        playerFieldRelationController.setupFieldOwnerArray(playerList);
        Field field = board.getFields()[1];
        assertFalse(playerFieldRelationController.isPlayerOwner(playerList.getPlayer(1), field));
        shouldAddFieldToPlayer();
        assertTrue(playerFieldRelationController.isPlayerOwner(playerList.getPlayer(1), field));
    }

    @Test
    public void shouldReturnTrueIfOwnerOfAllFieldsOfType() {
        playerFieldRelationController.setupFieldOwnerArray(playerList);
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Field field = board.getFields()[1];
        assertFalse(playerFieldRelationController.isOwnerOfAllFieldsOfType(playerList.getPlayer(1), field));

        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        assertTrue(playerFieldRelationController.isOwnerOfAllFieldsOfType(playerList.getPlayer(1), field));
    }

    @Test
    public void shouldGetNetWorth() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        playerFieldRelationController.getNetWorth(player);
        assertEquals(1500, playerFieldRelationController.getNetWorth(player));
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        assertEquals(1620, playerFieldRelationController.getNetWorth(player));
    }

    @Test
    public void shouldGetFieldById() {
        Field rodovrevej = board.getFields()[1];
        String rodovrevejID = rodovrevej.getID();
        assertEquals(rodovrevej, playerFieldRelationController.getFieldById(rodovrevejID));
    }

    @Test
    public void shouldGetFieldByName() {
        assertNull(playerFieldRelationController.getFieldByName("FAKE_FIELD"));
        Field rodovrevej = board.getFields()[1];
        assertEquals(rodovrevej, playerFieldRelationController.getFieldByName(rodovrevej.getTitle()));
    }

    @Test
    public void shouldGetPlayerFields() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        int numOfFieldsOwned = playerFieldRelationController.getPlayerFields(player).length;
        assertEquals(0, numOfFieldsOwned);

        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        numOfFieldsOwned = playerFieldRelationController.getPlayerFields(player).length;
        assertEquals(2, numOfFieldsOwned);
    }

    @Test
    public void shouldGetFieldsWithHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(2 , fieldsLength);

        ((PropertyField) rodovrevej).addBuilding();
        fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(1, fieldsLength);
    }

    @Test
    public void shouldGetFieldsWithNoHousesByPlayerAndCheckPawnStatus() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        ((PropertyField) rodovrevej).addBuilding();
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        int fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length;
         assertEquals(0, fieldsLength);

        ((PropertyField) hvidovrevej).setPawnedStatus(false);
        fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length;
        assertEquals(1, fieldsLength);
    }

    @Test
    public void shouldGetFieldsWithNoHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(2, fieldsLength);

        ((PropertyField) rodovrevej).addBuilding();
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(1, fieldsLength);

        ((PropertyField) hvidovrevej).addBuilding();
        fieldsLength = playerFieldRelationController.getFieldsWithNoHousesByPlayer(player).length;
        assertEquals(0, fieldsLength);
    }

    @Test
    public void getAmountOfTypeOwned() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field valby = board.getFields()[8];
        int amountOfTypeOwned = playerFieldRelationController.getAmountOfTypeOwned(player, ((Ownable)valby));
        assertEquals(0, amountOfTypeOwned);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        amountOfTypeOwned = playerFieldRelationController.getAmountOfTypeOwned(player, ((Ownable)rodovrevej));
        assertEquals(2, amountOfTypeOwned);
    }

    @Test
    public void shouldGetPlayerBuildableFields() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        int numOfBuildableFields = playerFieldRelationController.getPlayerBuildableFields(player).length;
        assertEquals(0, numOfBuildableFields);

        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        numOfBuildableFields = playerFieldRelationController.getPlayerBuildableFields(player).length;
        assertEquals(2, numOfBuildableFields);
    }

    @Test
    public void shouldFindPlayersWithFieldsWithNoHouses() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);

        Player player2 = playerList.getPlayer(2);
        Field valby = board.getFields()[8];
        Field allegade = board.getFields()[9];
        playerFieldRelationController.addFieldToPlayer(player2, valby);
        playerFieldRelationController.addFieldToPlayer(player2, allegade);
        int numOfPlayers = playerFieldRelationController.findPlayersWithFieldsWithNoHouses().length;
        assertEquals(2, numOfPlayers);

        ((PropertyField) valby).addBuilding();
        ((PropertyField) allegade).addBuilding();
        numOfPlayers = playerFieldRelationController.findPlayersWithFieldsWithNoHouses().length;
        assertEquals(1, numOfPlayers);
    }

    @Test
    public void shouldGetPlayerNamesWithFieldsWithNoHouses() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);

        Player player2 = playerList.getPlayer(2);
        Field valby = board.getFields()[8];
        Field allegade = board.getFields()[9];
        playerFieldRelationController.addFieldToPlayer(player2, valby);
        playerFieldRelationController.addFieldToPlayer(player2, allegade);
        int numOfPlayerNames = playerFieldRelationController.getPlayerNamesWithFieldsWithNoHouses().length;
        assertEquals(2, numOfPlayerNames);

        ((PropertyField) valby).addBuilding();
        ((PropertyField) allegade).addBuilding();
        numOfPlayerNames = playerFieldRelationController.getPlayerNamesWithFieldsWithNoHouses().length;
        assertEquals(1, numOfPlayerNames);
    }

    @Test
    public void getPropertyNamesWithNoHousesByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);

        String[] propertyNames = playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(player);
        assertEquals("Roedovrevej", propertyNames[0]);
        assertEquals("Hvidovre", propertyNames[1]);

        ((PropertyField) rodovrevej).addBuilding();
        propertyNames = playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(player);
        assertEquals("Hvidovre", propertyNames[0]);
    }

    @Test
    public void getPawnedFieldsByPlayer() {
        shouldSetupFieldOwnerArrayWithPlayerNames();
        Player player = playerList.getPlayer(1);
        Field rodovrevej = board.getFields()[1];
        Field hvidovrevej = board.getFields()[3];
        playerFieldRelationController.addFieldToPlayer(player, rodovrevej);
        playerFieldRelationController.addFieldToPlayer(player, hvidovrevej);
        int fieldsLength = playerFieldRelationController.getPawnedFieldsByPlayer(player).length;
        assertEquals(0, fieldsLength);

        ((PropertyField) rodovrevej).setPawnedStatus(true);
        ((PropertyField) hvidovrevej).setPawnedStatus(true);
        fieldsLength = playerFieldRelationController.getPawnedFieldsByPlayer(player).length;
        assertEquals(2, fieldsLength);
    }
}