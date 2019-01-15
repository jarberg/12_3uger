package controller;

import model.board.Field;
import model.player.Player;

public class TradeController {

    private static TradeController singleInstance = new TradeController();

    private TradeController(){

    }

    public static TradeController getSingleInstance(){
        return singleInstance;
    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, int amount){
        sourcePlayer.addToBalance(-amount);
        targetPlayer.addToBalance(amount);
    }

    public void transferAssets(Player targetPlayer, int amount){
        targetPlayer.addToBalance(amount);

    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, Field field){

    }

    public void transferAssets(Player targetPlayer, Field field){

    }
}
