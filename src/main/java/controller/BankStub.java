package controller;

import model.board.Board;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.player.PlayerList;

public class BankStub extends Bank {

    BankStub(){
        super();
    }

    @Override
    public void setBankNoCrashy(int length){}

    @Override
    public void setPlayerList(PlayerList playerlist) {}

    @Override
    public void setBoard(Board board) {}

    @Override
    public void removeFieldOwner(Field field) {}

    @Override
    public Player getPlayerByName(String name) {
        return null;
    }

    @Override
    public void addFieldToPlayer(Player player, Field field) {}

    @Override
    public boolean hasOwner(String fieldID) {
        return false;
    }

    @Override
    public Player getOwner(String fieldID) {

    return null; }

    @Override
    public boolean isOwner(Player player, Field field) {
        return false;
    }

    @Override
    public boolean isOwnerOfAllFieldsOfType(Player player, Field field) {
        return false;
    }

    @Override
    public String getInfo(int index1, int index2) {
        return null;
    }

    @Override
    public int getNetWorth(Player player) {
        return 0;
    }

    @Override
    public Field getFieldById(String fieldID) {
        return null;
    }

    @Override
    public Field getFieldByName(String fieldName) {
        return null;
    }

    @Override
    public Field[] getPlayerFields(Player player) {
        return null;
    }

    @Override
    public Field[] getFieldsWithHousesByPlayer(Player player) {
        return null;
    }

    @Override
    public Field[] getFieldsWithNoHousesByPlayer(Player player) {
        return null;
    }

    @Override
    public Field[] getSameTypeFields(Player player) {
        return null;
    }

    @Override
    public int getAmountOfTypeOwned(Player owner, Field field) {
        return 0;
    }

    @Override
    public PropertyField[] getPlayerBuildableFields(Player currentPlayer) {
        return null;
    }

    @Override
    public Player[] findPlayersWithFieldsWithNoHouses() {
        return null;
    }

    @Override
    public String[] getPlayerNames() {
        return null;
    }

    @Override
    public String[] getPlayerNamesWithNoHouses() {
        return null;
    }

    @Override
    public String[] getPropertyNamesWithNoHousesByPlayer(Player player) {
        return null;
    }
}
