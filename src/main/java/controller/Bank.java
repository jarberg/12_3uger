package controller;

import model.board.Board;
import model.board.BreweryField;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.player.PlayerList;

public class Bank {

    private String[][] fieldOwnerArray;
    private int playerListLength;
    private PlayerList playerList;
    private Board board;



    private static Bank singletonInstance = new Bank();

    public static Bank getSingleInstance(){
        return singletonInstance;
    }

    private Bank(){
    }

    public void setBankNoCrashy(int length){

    }

    public void setPlayerList(PlayerList playerlist){
        this.playerList = playerlist;
        this.playerListLength = this.playerList.getAllPlayers().length;
        fieldOwnerArray = new String[playerListLength][30];
        for (int i = 0; i < playerListLength; i++) {
            fieldOwnerArray[i][0] = playerlist.getPlayer(i).getName();
        }
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void removeFieldOwner(Field field){

        int idxOfRow = 0;
        int idxOfFieldToRemove = 0;
        for (int i = 0; i < fieldOwnerArray.length; i++) {
            for (int j = 0; j < fieldOwnerArray[i].length; j++) {
                if (field.getID().equals(fieldOwnerArray[i][j])) {
                    idxOfRow = i;
                    idxOfFieldToRemove = j;
                }
            }
        }
        int length = fieldOwnerArray[idxOfRow].length;

        String[] ownedFields = new String[length-1];
        int counter = 0;
        for (int i = 0; i < fieldOwnerArray[idxOfRow].length; i++) {
            if (i != idxOfFieldToRemove){
                ownedFields[counter] = fieldOwnerArray[idxOfRow][i];
                counter++;
            }
        }

        fieldOwnerArray[idxOfRow] = ownedFields;
    }

    public Player getPlayerByName(String name){
        Player player = null;

        for (int i = 0; i <playerListLength ; i++) {
            if(name.equals(playerList.getPlayer(i).getName())){
                player = playerList.getPlayer(i);
            }
        }
        return player;
    }

    public void addFieldToPlayer(Player player, Field field){
        for (int j = 0; j < playerListLength; j++) {
            if(player.getName().equals(fieldOwnerArray[j][0])){
                for (int k = 0; k < fieldOwnerArray[j].length; k++) {
                    if(fieldOwnerArray[j][k+1]==null){
                        fieldOwnerArray[j][k+1] = field.getID();
                        break;
                    }
                }
                break;
            }
        }
    }

    public boolean hasOwner(String fieldID){
        boolean haveOwner = false;

        for (int i = 0; i < fieldOwnerArray.length; i++) {
            for (int j = 1; j < fieldOwnerArray[i].length; j++) {
                if(fieldOwnerArray[i][j] != null){
                    if(fieldOwnerArray[i][j].equals(fieldID)){
                        haveOwner = true;
                    }
                }
            }
        }

        return haveOwner;
    }

    public Player getOwner(String fieldID) {

        String owner = "free";

        for (int i = 0; i <fieldOwnerArray.length ; i++) {
            for (int j = 1; j <fieldOwnerArray[i].length ; j++) {
                if(fieldOwnerArray[i][j]==null){
                    break;
                }
                if(fieldOwnerArray[i][j].equals(fieldID)){
                    owner = fieldOwnerArray[i][0];
                    break;
                }
            }
        }
        return getPlayerByName(owner);
    }

    public boolean isOwner(Player player, Field field){
        Player owner = getOwner(field.getID());

        boolean trueOwner = false;
        if(owner == player)
            trueOwner = true;

        return trueOwner;
    }



    public boolean isOwnerOfAllFieldsOfType(Player player, Field field){

        String fieldTypeID;

        int amountOfFields = 0;
        int count = 0;
        Field currentTestField;
        String ownerOfField = null;

        if(field instanceof PropertyField) {
            fieldTypeID = ((PropertyField) field).getType();

            switch (fieldTypeID) {
                case "1":
                    amountOfFields = 2;
                    break;
                case "2":
                    amountOfFields = 3;
                    break;
                case "3":
                    amountOfFields = 3;
                    break;
                case "4":
                    amountOfFields = 3;
                    break;
                case "5":
                    amountOfFields = 3;
                    break;
                case "6":
                    amountOfFields = 3;
                    break;
                case "7":
                    amountOfFields = 3;
                    break;
                case "8":
                    amountOfFields = 2;
                    break;
            }

            for (int i = 0; i <fieldOwnerArray.length ; i++) {
                if(fieldOwnerArray[i][0].equals(player.getName())){
                    Field[] temp = getPlayerFields(player);
                    for (Field aTemp : temp) {
                        if(aTemp instanceof PropertyField){
                            if (((PropertyField) aTemp).getType().equals(((PropertyField) field).getType())) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        if(field instanceof BreweryField){
            amountOfFields = 2;
            for (int i = 0; i <fieldOwnerArray.length ; i++) {
                if(fieldOwnerArray[i][0].equals(player.getName())){
                    Field[] temp = getPlayerFields(player);
                    for (Field aTemp : temp) {
                        if(aTemp instanceof BreweryField){
                                count++;
                        }
                    }
                }
            }

        }
        return (count == amountOfFields);
    }

    public String getInfo(int index1,int index2){
        return fieldOwnerArray[index1][index2];
    }

    public int getNetWorth(Player player){
        int netWorth =0;
        netWorth += player.getBalance();

        Field[] ownedfields = getPlayerFields(player);

        for (int i = 0; i <getPlayerFields(player).length ; i++) {
            if (ownedfields[i] instanceof PropertyField) {
                netWorth += ((PropertyField) ownedfields[i]).getBuildingCount() * ((PropertyField) ownedfields[i]).getBuildingPrice();
                netWorth += ((PropertyField) ownedfields[i]).getPrice();
            }
            else if (ownedfields[i] instanceof BreweryField) {
                netWorth += ((BreweryField) ownedfields[i]).getPrice();
            }

        }

        return netWorth;
    }

    public Field getFieldById(String fieldID){
        Field field = null;

        field =board.getFields()[Integer.parseInt(fieldID)];

        return field;

    }

    public Field getFieldByName(String fieldName){
        for (Field field : board.getFields()){
            if (field.getTitle().equals(fieldName))
                return field;
        }
        return null;
    }

    public Field[] getPlayerFields(Player player){

        Field[] ownedFields = new Field[0];

        for (int i = 0; i <fieldOwnerArray.length ; i++) {

            for (int j = 1; j < fieldOwnerArray[i].length ; j++) {

                if(null==fieldOwnerArray[i][j]){
                    break;
                }
                else{
                    if(player.getName().equals(fieldOwnerArray[i][0])){
                        Field[] temp = new Field[ownedFields.length+1];
                        for (int k = 0; k <ownedFields.length ; k++) {
                            temp[k] = ownedFields[k];
                        }
                        temp[j-1]=board.getFields()[Integer.parseInt(fieldOwnerArray[i][j])];
                        ownedFields = temp;
                    }
                }
            }
        }
        return ownedFields;
    }

    public Field[] getFieldsWithHousesByPlayer(Player player){


        Field[] playerFieldList = getPlayerFields(player);
        Field[] ownedFields = new Field[0];

        for (Field aPlayerFieldList : playerFieldList) {
            if(aPlayerFieldList instanceof PropertyField) {
                if (((PropertyField) aPlayerFieldList).getBuildingCount() > 0) {

                    Field[] temp = new Field[ownedFields.length + 1];

                    for (int j = 0; j < ownedFields.length; j++) {
                        temp[j] = ownedFields[j];
                    }
                    temp[temp.length - 1] = aPlayerFieldList;

                    ownedFields = temp;

                }
            }

        }
        return ownedFields;
    }

    public Field[] getFieldsWithNoHousesByPlayer(Player player){

        Field[] playerFieldList = getPlayerFields(player);
        int length = playerFieldList.length;
        Field[] ownedFields = new Field[0];


        for (Field aPlayerFieldList : playerFieldList) {
            if(aPlayerFieldList instanceof PropertyField) {
                if (((PropertyField) aPlayerFieldList).getBuildingCount() == 0) {

                    Field[] temp = new Field[ownedFields.length + 1];

                    for (int j = 0; j < ownedFields.length; j++) {
                        temp[j] = ownedFields[j];
                    }
                    temp[temp.length - 1] = aPlayerFieldList;

                    ownedFields = temp;

                }

            }
            else{

                Field[] temp = new Field[ownedFields.length + 1];

                for (int j = 0; j < ownedFields.length; j++) {
                    temp[j] = ownedFields[j];
                }
                temp[temp.length - 1] = aPlayerFieldList;

                ownedFields = temp;

            }

        }
        return ownedFields;
    }
}
