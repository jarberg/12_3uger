import controller.GameController;
import model.board.Board;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

public class Main {
    public static void main(String[] args) {

        GameController gameController = GameController.getInstance();
        gameController.playGame();

        Board board = new Board();
        board.setupBoard(LogicStringCollection.getInstance().getFieldsText(), LanguageStringCollection.getInstance("danish").getFieldsText());
        System.out.println(board.getField(1).getTitle());
        PlayerList playerlist = new PlayerList(4);
        playerlist.addPlayer(0,new Player("Test1"));
        playerlist.addPlayer(1,new Player("Test2"));
        playerlist.addPlayer(2,new Player("Test3"));
        playerlist.addPlayer(3,new Player("Test4"));

        Bank Bank = new Bank(playerlist,board);


        Bank.addFieldToPlayer(playerlist.getPlayer(2),board.getField(1));

        System.out.println(Bank.getInfo(1,0));
        System.out.println(Bank.getOwner(board.getField(1).getID()).getName());
        System.out.println(Bank.hasOwner(board.getField(1).getID()));
        System.out.println(Bank.isOwnerOfAllFieldsOfType(playerlist.getPlayer(1),board.getField(1).getID()));


    }
}
