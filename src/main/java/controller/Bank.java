package controller;

import model.board.*;
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

    public void setPlayerList(PlayerList playerlist){
        this.playerList = playerlist;
        this.playerListLength = this.playerList.getAllPlayers().length;
        this.fieldOwnerArray = new String[playerListLength][300];
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
        return playerList.getPlayerByName(name);
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
        boolean hasOwner = false;

        for (String[] playerOwnedFields : fieldOwnerArray) {
            for (int j = 1; j < playerOwnedFields.length; j++) {
                if (playerOwnedFields[j] != null) {
                    if (playerOwnedFields[j].equals(fieldID)) {
                        hasOwner = true;
                    }
                }
            }
        }

        return hasOwner;
    }

    public Player getOwner(String fieldID) {

        String owner = null;

        for (int i = 0; i <fieldOwnerArray.length ; i++) {
            for (int j = 1; j <fieldOwnerArray[i].length ; j++) {
                if(fieldOwnerArray[i][j] != null && fieldOwnerArray[i][j].equals(fieldID)){
                    owner = fieldOwnerArray[i][0];
                    break;
                }
            }
        }
        return playerList.getPlayerByName(owner);
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

            //Checks if you own the correct amount of a type of field. Owning 3 "Allégade" will let you buy houses on it.
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

        //Checks if you own the correct amount of a type of field. Owning 3 "Allégade" will let you buy houses on it.
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

    public int getNetWorth(Player player){
        int netWorth = 0;
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
            else if (ownedfields[i] instanceof FerryField) {
                netWorth += ((FerryField)ownedfields[i]).getPrice();
            }

        }

        return netWorth;
    }

    public Field getFieldById(String fieldID){
        Field field = null;

        //TODO: Make sure index is appropriate and does not need +1 or -1.
        field = board.getFields()[Integer.parseInt(fieldID)];

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
                        for (int k = 0; k < ownedFields.length ; k++) {
                            temp[k] = ownedFields[k];
                        }
                        temp[j-1] = board.getFields()[Integer.parseInt(fieldOwnerArray[i][j])];
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

    public Field[] getFieldsWithNoHousesByPlayerAndCheckPawnStatus(Player player){

        Field[] playerFieldArray = getPlayerFields(player);
        Field[] ownedFields = new Field[0];

        for (Field field : playerFieldArray) {
            if(field instanceof PropertyField) {
                if (((PropertyField) field).getBuildingCount() == 0 && !((PropertyField) field).getPawnedStatus()) {

                    Field[] temp = new Field[ownedFields.length + 1];

                    for (int j = 0; j < ownedFields.length; j++) {
                        temp[j] = ownedFields[j];
                    }

                    temp[temp.length - 1] = field;

                    ownedFields = temp;

                }

            }
            else if(field instanceof Ownable){

                if(!((Ownable) field).getPawnedStatus()){
                    Field[] temp = new Field[ownedFields.length + 1];

                    for (int j = 0; j < ownedFields.length; j++) {
                        temp[j] = ownedFields[j];
                    }
                    temp[temp.length - 1] = field;

                    ownedFields = temp;
                }
            }
        }
        return ownedFields;
    }

    public Field[] getFieldsWithNoHousesByPlayer(Player player){

        Field[] playerFieldArray = getPlayerFields(player);
        Field[] ownedFields = new Field[0];

        for (Field aPlayerFieldList : playerFieldArray) {
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

    public int getAmountOfTypeOwned(Player owner, Field field) {

        Field[] ownedFields = getPlayerFields(owner);
        int counter = 0;
        String fieldType = "";

        fieldType = ((Ownable)field).getType();

        for(Field f : ownedFields){

                if(((Ownable) f).getType().equals(fieldType))
                    counter++;
        }
        return counter;
    }

    public PropertyField[] getPlayerBuildableFields(Player currentPlayer) {
        Field[] playerFields = getPlayerFields(currentPlayer);
        int counter = 0;
        for(Field field : playerFields){
            if(field instanceof PropertyField){
                counter++;
            }
        }
        PropertyField[] candidateFields = new PropertyField[counter];

        counter = 0;
        for(Field field : playerFields){
            if(field instanceof PropertyField){
                candidateFields[counter] = (PropertyField) field;
                counter++;
            }
        }

        counter = 0;
        for(PropertyField field : candidateFields){
            Player owner = getOwner(field.getID());
            boolean isOwnerOfAllOfKind = isOwnerOfAllFieldsOfType(owner, field);
            if(isOwnerOfAllOfKind){
                counter++;
            }
        }

        PropertyField[] buildableFieldsCandidates = new PropertyField[counter];
        counter = 0;
        for(PropertyField field : candidateFields){
            Player owner = getOwner(field.getID());
            boolean isOwnerOfAllOfKind = isOwnerOfAllFieldsOfType(owner, field);
            if(isOwnerOfAllOfKind){
                buildableFieldsCandidates[counter] = field;
                counter++;
            }
        }

        int counter3 = 0;
        for (PropertyField field : buildableFieldsCandidates){
            PropertyField[] allOfType = getAllPropertyFieldsOfType(field);
            int lowestCount = 5;
            for(PropertyField propertyField : allOfType){
                if(propertyField.getBuildingCount() < lowestCount)
                    lowestCount = propertyField.getBuildingCount();
            }
            if(field.getBuildingCount() == lowestCount && lowestCount != 5){
                counter3++;
            }
        }

        PropertyField[] buildableFields = new PropertyField[counter3];
        counter3 = 0;
        for (PropertyField field : buildableFieldsCandidates){
            PropertyField[] allOfType = getAllPropertyFieldsOfType(field);
            int lowestCount = 5;
            for(PropertyField propertyField : allOfType){
                if(propertyField.getBuildingCount() < lowestCount)
                    lowestCount = propertyField.getBuildingCount();
            }
            if(field.getBuildingCount() == lowestCount && lowestCount != 5){
                buildableFields[counter3] = field;
                counter3++;
            }
        }

        return buildableFields;
    }

    private PropertyField[] getAllPropertyFieldsOfType(PropertyField field) {
        int counter = 0;
        for(Field otherField : board.getFields()){
            if(otherField instanceof PropertyField){
                if(((PropertyField) otherField).getType().equals(field.getType())){
                    counter++;
                }
            }
        }
        PropertyField[] allFields = new PropertyField[counter];
        for(Field otherField : board.getFields()){
            if(otherField instanceof PropertyField){
                if(((PropertyField) otherField).getType().equals(field.getType())){
                    counter--;
                    allFields[counter] = (PropertyField)otherField;
                }
            }
        }
        return allFields;
    }

    public Player[] findPlayersWithFieldsWithNoHouses(){
        Player[] players = new Player[0];
        for (Player player : playerList.getAllPlayers()){
            if (getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player).length > 0){
                Player[] temp = new Player[players.length+1];
                for (int i = 0; i < players.length; i++) {
                    temp[i] = players[i];
                }
                temp[players.length] = player;
                players = temp;
            }
        }
        return players;
    }

    public String[] getPlayerNamesWithFieldsWithNoHouses(){
        Player[] players = findPlayersWithFieldsWithNoHouses();
        String[] names = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    public String[] getPropertyNamesWithNoHousesByPlayer(Player player){
        Field[] fields = getFieldsWithNoHousesByPlayerAndCheckPawnStatus(player);
        String[] titles = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            titles[i] = fields[i].getTitle();
        }
        return titles;
    }

    public Field[] getPawnedFieldsByPlayer(Player player) {
        Field[] fields = getFieldsWithNoHousesByPlayer(player);
        int counter = 0;

        for (Field field: fields){
            if(field instanceof Ownable){
                if(((Ownable) field).getPawnedStatus()){
                    counter++;
                }
            }
        }

        Field[] fieldsPawnedCopy = new Field[counter];

        counter = 0;
        for (int i = 0; i < fields.length; i++) {
            if(fields[i] instanceof Ownable){
                if(((Ownable) fields[i]).getPawnedStatus()){
                    fieldsPawnedCopy[counter] = fields[i];
                    counter++;
                }
            }
        }

        return fieldsPawnedCopy;

    }

    public Player[] getPlayers() {
        return playerList.getAllPlayers();
    }
}


