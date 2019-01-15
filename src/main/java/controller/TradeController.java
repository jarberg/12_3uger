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
        while(sourcePlayer.getBalance() < amount){
            raiseMoney(sourcePlayer);
            if(sourcePlayer.getBrokeStatus())
                break;
        }
        if(sourcePlayer.getBalance() >= amount){
            sourcePlayer.addToBalance(-amount);
            targetPlayer.addToBalance(amount);
            String message = String.format(languageStringCollection.getMenu()[18],sourcePlayer.getName(), String.valueOf(amount), targetPlayer);
            viewController.showMessage(message);
        }
    }

    private boolean raiseMoney(Player player) {
        int amount;
        Field[] fieldsWithHouses = bank.getFieldsWithHousesByPlayer(player);
        Field[] fieldsWithoutHouses = bank.getFieldsWithoutHousesByPlayer(player);

        String sellHouseOption = languageStringCollection.getMenu()[19];
        String sellFieldOption = languageStringCollection.getMenu()[20];


        if (fieldsWithHouses.length > 0 && fieldsWithoutHouses.length > 0){
            String choice1 = viewController.getUserButtonSelection(sellHouseOption, fieldsWithHouses);
            String choice2 = viewController.getUserButtonSelection(sellFieldOption, fieldsWithoutHouses);

            amount = (choice1 != null) ? choice1 : choice2;
            transferAssets(player, amount);

        } else if (fieldsWithHouses.length > 0){
            String choice1 = viewController.getUserButtonSelection(sellHouseOption, fieldsWithHouses);


        } else if (fieldsWithoutHouses.length > 0){
            String choice2 = viewController.getUserButtonSelection(sellFieldOption, fieldsWithoutHouses);


        }



        return false;
    }

    public void transferAssets(Player targetPlayer, int amount){
        targetPlayer.addToBalance(amount);

    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, Field field){

    }

    public void transferAssets(Player targetPlayer, Field field){

    }
}
