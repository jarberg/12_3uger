import controller.GameController;

public class Main {
    public static void main(String[] args) {

        GameController gamecon = GameController.getInstance();

        gamecon.getViewCon().showEmptyGUI();
        gamecon.GodMode(true);
        gamecon.getViewCon().showEmptyGUI();
    }
}
