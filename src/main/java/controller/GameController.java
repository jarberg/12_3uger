package controller;

public class GameController {

    ViewControllerAbs viewcon ;

    private boolean test = false;
    private static GameController singletonInstance = null;

    public static GameController getInstance(){

        if(singletonInstance ==null){

            singletonInstance = new GameController();
        }
        return singletonInstance;

    }
    public GameController(){
        viewcon = ViewController.getSingleInstance();
    }

    public ViewControllerAbs getViewCon(){
        return viewcon;
    }

    public void GodMode(boolean mode){
        if(!mode) {
            viewcon = ViewController.getSingleInstance();
        }
        else {
            viewcon = ViewControllerStub.getSingleInstance();
        }
    }
}
