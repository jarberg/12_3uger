package controller;

import model.board.BreweryField;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.text.LanguageStringCollection;

public class TradeController {

    private static TradeController singleInstance = new TradeController();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private ViewController viewController = ViewController.getSingleInstance();
    private static Bank bank = Bank.getSingleInstance();

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
            String message = String.format(languageStringCollection.getMenu()[18],sourcePlayer.getName(), String.valueOf(amount), targetPlayer.getName());
            viewController.showMessage(message);
        }
        viewController.setGUI_PlayerBalance(sourcePlayer.getName(), sourcePlayer.getBalance());
        viewController.setGUI_PlayerBalance(targetPlayer.getName(), targetPlayer.getBalance());
    }

    private void raiseMoney(Player player) {
        Field[] fieldsWithHouses = bank.getFieldsWithHousesByPlayer(player);
        Field[] fieldsWithoutHouses = bank.getFieldsWithNoHousesByPlayer(player);

        String sellHouseOption = languageStringCollection.getMenu()[19];
        String sellFieldOption = languageStringCollection.getMenu()[20];
        String sellMessage = languageStringCollection.getMenu()[21];

        if (fieldsWithHouses.length > 0 && fieldsWithoutHouses.length > 0){
            String choice = viewController.getUserButtonSelection(sellMessage, sellHouseOption, sellFieldOption);
            if(choice.equals(sellFieldOption)){
                String[] fieldNames = new String[fieldsWithoutHouses.length];
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldNames[i] = fieldsWithoutHouses[i].getTitle();
                }
                String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
                Field field = bank.getFieldByName(sellingField);
                bank.removeFieldOwner(field);
                if(field instanceof PropertyField){
                    player.addToBalance(((PropertyField) field).getPrice() / 2);
                } else if (field instanceof BreweryField){
                    player.addToBalance(((BreweryField) field).getPrice()/2);
                }
                viewController.showOwner(sellingField, player.getName(), player.getPlayerColor());
            } else if(choice.equals(sellHouseOption)){
                String[] fieldNames = new String[fieldsWithHouses.length];
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldNames[i] = fieldsWithHouses[i].getTitle();
                }
                String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
                PropertyField field = ((PropertyField)bank.getFieldByName(sellingField));
                field.removeBuilding();
                viewController.updateFieldBuildings(sellingField, (field.getBuildingCount()));
                player.addToBalance(field.getBuildingPrice()/2);
            }

        } else if (fieldsWithHouses.length > 0){
            String choice1 = viewController.getUserButtonSelection(sellMessage, sellHouseOption);
            String[] fieldNames = new String[fieldsWithHouses.length];
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = fieldsWithHouses[i].getTitle();
            }
            String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
            PropertyField field = ((PropertyField)bank.getFieldByName(sellingField));
            field.removeBuilding();
            viewController.updateFieldBuildings(sellingField, (field.getBuildingCount()));
            player.addToBalance(field.getBuildingPrice()/2);

        } else if (fieldsWithoutHouses.length > 0){
            String choice2 = viewController.getUserButtonSelection(sellMessage, sellFieldOption);
            String[] fieldNames = new String[fieldsWithoutHouses.length];
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = fieldsWithoutHouses[i].getTitle();
            }
            String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
            Field field = bank.getFieldByName(sellingField);
            bank.removeFieldOwner(field);
            if(field instanceof PropertyField){
                player.addToBalance(((PropertyField) field).getPrice() / 2);
            } else if (field instanceof BreweryField){
                player.addToBalance(((BreweryField) field).getPrice()/2);
            }
            viewController.showOwner(sellingField, player.getName(), player.getPlayerColor());
        } else {
            String brokeMessage = String.format(languageStringCollection.getMenu()[22], player.getName());
            viewController.showMessage(brokeMessage);
            player.setBrokeStatus(true);
        }
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());
    }

    public void transferAssets(Player targetPlayer, int amount){
        targetPlayer.addToBalance(amount);
        while(targetPlayer.getBalance() < 0){
            raiseMoney(targetPlayer);
            if(targetPlayer.getBrokeStatus())
                break;
        }
        viewController.setGUI_PlayerBalance(targetPlayer.getName(), targetPlayer.getBalance());
    }

    public void transferAssets(Player sourcePlayer, Player targetPlayer, Field field){

    }

    public void transferAssets(Player targetPlayer, Field field){
        bank.addFieldToPlayer(targetPlayer, field);
        viewController.showOwner(field.getTitle(), targetPlayer.getName(), targetPlayer.getPlayerColor());

        if(field instanceof PropertyField){
            transferAssets(targetPlayer, -((PropertyField)field).getPrice());
        }

        if(field instanceof BreweryField){
            transferAssets(targetPlayer, -((BreweryField)field).getPrice());
        }
    }
}
