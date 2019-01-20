import controller.GameController;
import model.deck.Card;


public class Main {
    public static void main(String[] args) {


        GameController gameController = GameController.getSingleInstance();
        gameController.playGame();


        }

    }






