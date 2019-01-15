package controller;

import model.board.Field;
import model.player.Player;
import model.text.LanguageStringCollection;

public class TradeController {

    private static TradeController singleInstance = new TradeController();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private ViewController viewController = ViewController.getSingleInstance();

    private TradeController(){

    }

    public static TradeController getSingleInstance(){
        return singleInstance;
    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, int amount){
        if(sourcePlayer.getBalance() > amount){
            sourcePlayer.addToBalance(-amount);
            targetPlayer.addToBalance(amount);
            String message = String.format(languageStringCollection.getMenu()[18],sourcePlayer.getName(), String.valueOf(amount), targetPlayer);
            viewController.showMessage(message);
        }
        if(raiseMoney()){
            sourcePlayer.addToBalance(-amount);
            targetPlayer.addToBalance(amount);
        }
    }

    private boolean raiseMoney() {
        return true;
    }

    public void transferAssets(Player targetPlayer, int amount){
        targetPlayer.addToBalance(amount);

    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, Field field){

    }

    public void transferAssets(Player targetPlayer, Field field){

    }
}
