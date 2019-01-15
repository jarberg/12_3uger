import model.board.Field;
import model.player.Player;
import model.player.PlayerList;

public class FieldOwnerBank {

    private String[][] fieldOwnerBank;
    private int length;

    public FieldOwnerBank(PlayerList playerList){
        length = playerList.getAllPlayers().length;

        fieldOwnerBank = new String[length][];

        for (int i = 0; i < length; i++) {
            fieldOwnerBank[i][0] = playerList.getPlayer(i).getName();
        }


    }

    public void addFieldToPlayer(Player player, Field field){
        for (int j = 0; j < length; j++) {
            if(player.getName().equals(fieldOwnerBank[j][0])){
                for (int k = 0; k < 40; k++) {
                    if(fieldOwnerBank[j][k+1]==null){
                        fieldOwnerBank[j][k+1] = field.getID();
                        break;
                    }
                }
                break;
            }

        }

    }

    public String getOwner(Field field){

    }


}
