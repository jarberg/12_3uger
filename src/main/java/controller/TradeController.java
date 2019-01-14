package controller;

import model.player.Player;

public class TradeController {

    private TradeController singleInstance = new TradeController();

    private TradeController(){

    }

    public TradeController getSingleInstance(){
        return singleInstance;
    }

    public void TransferAssets(Player sourcePlayer, Player targetPlayer, int amount){

    }


}
