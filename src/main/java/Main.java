import controller.GameController;

public class Main {
    public static void main(String[] args) {

        GameController gamecon = GameController.getInstance();

        gamecon.setupGame();
        gamecon.playGame();
    }
}
