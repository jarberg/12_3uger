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
    private int boardLength = 40;




    public Bank(PlayerList playerList, Board board){
        playerListLength = playerList.getAllPlayers().length;
        this.playerList = playerList;
        this.board = board;
        fieldOwnerArray = new String[playerListLength][30];
        for (int i = 0; i < playerListLength; i++) {
            fieldOwnerArray[i][0] = playerList.getPlayer(i).getName();
        }
    }

    public Player getPlayerByName(String name){
        Player player = null;

        for (int i = 0; i <playerListLength ; i++) {
            if(name==playerList.getPlayer(i).getName()){
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
        boolean haveOwner = true;
        if(getOwner(fieldID).equals("free"));{
            haveOwner = false;
        }
        return haveOwner;
    }

    public Player getOwner(String fieldID) {

        String owner = "free";

        for (int i = 0; i < playerListLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (fieldOwnerArray[i][j] == null) {
                    break;
                }
                if (fieldOwnerArray[i][j].equals(fieldID)) {
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

    public boolean isOwnerOfAllFieldsOfType(Player player, String fieldTypeID){

        int amountOfFields = 0;
        int count = 0;
        Field currentTestField;
        String ownerOfField = null;

        switch(fieldTypeID){
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

        for (int i = 0; i < boardLength; i++) {
            if(board.getFields()[i] instanceof PropertyField){
                currentTestField = board.getFields()[i];
                for (int j = 0; j < playerListLength; j++) {
                    if(playerList.getPlayer(j).equals(getOwner(board.getFields()[i].getID()))){
                        ownerOfField = playerList.getPlayer(j).getName();
                    }

                }
                if(((PropertyField) currentTestField).getType().equals(fieldTypeID) && player.getName().equals((ownerOfField))){
                    count++;
                }
            }
        }

        return(count == amountOfFields);
    }

    public String getInfo(int index1,int index2){
        return fieldOwnerArray[index1][index2];
    }

    //sudo

    public int getNetWorth(Player p){
        int netWorth =0;
        netWorth += p.getBalance();

        for (int i = 0; i <fieldOwnerArray.length ; i++) {
            if(fieldOwnerArray[i][0]== p.getName()){
                for (int j = 1; j < fieldOwnerArray[i].length; j++) {
                    if(fieldOwnerArray[i][j]==null){}
                    else {
                        Field field = getFieldById(fieldOwnerArray[i][j]);
                        if (field instanceof PropertyField) {
                            netWorth += ((PropertyField) field).getBuildingCount() * ((PropertyField) field).getBuildingPrice();
                            netWorth += ((PropertyField) field).getPrice();
                        } else if (field instanceof BreweryField) {
                            ((BreweryField) field).getPrice();
                        }
                    }
                }
            }
        }

        return netWorth;
    }

    public Field getFieldById(String fieldID){
        Field field = null;

        field =board.getFields()[Integer.parseInt(fieldID)];

        return field;

    }

    public void getFieldsWithHousesByPlayer(){

    }

    public void getFieldsWithNoHousesByPlayer(){

    }


}
