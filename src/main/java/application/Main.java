package application;

import controller.GameController;


public class Main {

    public static void main(String[] args) {


        GameController gameController = GameController.getSingleInstance();
        gameController.playGame();


    }

}






