import model.board.Board;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.player.PlayerList;

public class FieldOwnerBank {

    private String[][] fieldOwnerBank;
    private int playerListLength;
    private PlayerList playerList;
    private Field[] board;
    private int boardLength;

    public FieldOwnerBank(PlayerList playerList, Board board){
        playerListLength = playerList.getAllPlayers().length;
        boardLength = board.getFields().length;
        this.board = board.getFields();
        fieldOwnerBank = new String[playerListLength][];

        for (int i = 0; i < playerListLength; i++) {
            fieldOwnerBank[0][0] = playerList.getPlayer(i).getName();
        }



    }

    public void addFieldToPlayer(Player player, Field field){
        for (int j = 0; j < playerListLength; j++) {
            if(player.getName().equals(fieldOwnerBank[j][0])){
                for (int k = 0; k < boardLength; k++) {
                    if(fieldOwnerBank[j][k+1]==null){
                        fieldOwnerBank[j][k+1] = field.getID();
                        break;
                    }
                }
                break;
            }

        }

    }

    public Player getOwner(String fieldID){
        String playerName = null;
        Player owner = null;
        stop:
        for (int i = 0; i < playerListLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if(fieldOwnerBank[i][j+1].equals(fieldID)){
                    playerName = fieldOwnerBank[i][0];
                    break stop;
                }
            }
        }
        for (int k = 0; k < playerListLength; k++) {
            if(playerList.getPlayer(k).getName().equals(playerName)){
                owner = playerList.getPlayer(k);
            }
        }
        return owner;

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
            if(board[i]instanceof PropertyField){
                currentTestField = board[i];
                for (int j = 0; j < playerListLength; j++) {
                    if(playerList.getPlayer(j).equals(getOwner(board[i].getID()))){
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
        return fieldOwnerBank[index1][index2];
    }

}
