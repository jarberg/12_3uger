import controller.GameController;
import jdk.internal.jline.internal.TestAccessible;
import model.board.Board;
import model.deck.TeleportAndPayDoubleCard;
import model.player.Player;
import model.player.PlayerList;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

public class Main {
    public static void main(String[] args) {

        GameController gameController = GameController.getSingleInstance();
        gameController.playGame();



        //System.out.println(controller.DrawController,TeleportAndPayDoubleCard card);
        }

    }






