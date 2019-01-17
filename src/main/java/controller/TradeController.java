package controller;

import model.board.BreweryField;
import model.board.FerryField;
import model.board.Field;
import model.board.PropertyField;
import model.player.Player;
import model.text.LanguageStringCollection;

import java.awt.*;

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
                String sellingField = viewController.getUserSelection(sellFieldOption, fieldNames);
                Field field = bank.getFieldByName(sellingField);
                bank.removeFieldOwner(field);
                viewController.showOwner(field.getTitle(), " ", Color.BLACK);
                if(field instanceof PropertyField){
                    player.addToBalance(((PropertyField) field).getPrice() / 2);
                } else if (field instanceof BreweryField){
                    player.addToBalance(((BreweryField) field).getPrice()/2);
                }
                //viewController.showOwner(sellingField, player.getName(), player.getPlayerColor());
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
            viewController.getUserButtonSelection(sellMessage, sellHouseOption);
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
            viewController.getUserButtonSelection(sellMessage, sellFieldOption);
            String[] fieldNames = new String[fieldsWithoutHouses.length];
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = fieldsWithoutHouses[i].getTitle();
            }
            String sellingField = viewController.getUserSelection(sellFieldOption, fieldNames);
            Field field = bank.getFieldByName(sellingField);
            bank.removeFieldOwner(field);
            viewController.showOwner(field.getTitle(), " ", Color.BLACK);

            if(field instanceof PropertyField){
                player.addToBalance(((PropertyField) field).getPrice() / 2);
            } else if (field instanceof BreweryField){
                player.addToBalance(((BreweryField) field).getPrice()/2);
            }
            //viewController.showOwner(sellingField, player.getName(), player.getPlayerColor());
        } else {
            String brokeMessage = String.format(languageStringCollection.getMenu()[22], player.getName());
            viewController.showMessage(brokeMessage);
            player.setBrokeStatus(true);
            viewController.vanishPlayer(player.getName(), player.getPosition());
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

        if(field instanceof FerryField){
            transferAssets(targetPlayer, -((FerryField)field).getPrice());
        }
    }

    public void askIfWantToBuy(Player player, Field field) {
        String message = languageStringCollection.getMenu()[15];
        String yes = languageStringCollection.getMenu()[16];
        String no = languageStringCollection.getMenu()[17];
        String choice = viewController.getUserSelection(message, yes, no);
        if(choice.equals(yes)){
            transferAssets(player, field);
        } else if (choice.equals(no)){
            //tradeController.auctionField(field);
        }
    }

    public void buyBuilding(Player player, PropertyField field){
        while(player.getBalance() < field.getBuildingPrice()){
            raiseMoney(player);
            if(player.getBrokeStatus())
                break;
        }
        transferAssets(player, -field.getBuildingPrice());
        field.addBuilding();
        viewController.addBuilding(field);
    }

    public void tradePropertyWithPlayer(Player sourcePlayer){
        String message = "Which player would you like to trade property with?";
        String[] names = bank.getPlayerNamesWithNoHouses();

        // update names so it doenst include the sourcePlayer
        String[] temp = new String[names.length-1];
        int counter = 0;
        for (String name : names) {
            if (!name.equals(sourcePlayer.getName())) {
                temp[counter] = name;
                counter++;
            }
        }
        names = temp;

        String playerChoice = viewController.getUserSelection(message, names);
        Player targetPlayer = bank.getPlayerByName(playerChoice);

        String message3 = sourcePlayer.getName() + " properties of source Player?";
        String sourceProperty = viewController.getUserSelection(message3, bank.getPropertyNamesWithNoHousesByPlayer(sourcePlayer));
        Field sField = bank.getFieldByName(sourceProperty);


        String message4 = sourcePlayer.getName() + " properties of selected player?";
        String targetProperty = viewController.getUserSelection(message4, bank.getPropertyNamesWithNoHousesByPlayer(targetPlayer));
        Field tField = bank.getFieldByName(targetProperty);


        String message2 = "Does targetPlayerName accept to trade tcfield for scfield ";
        String yes = languageStringCollection.getMenu()[16];
        String no = languageStringCollection.getMenu()[17];
        String[] yesNo = {yes, no};
        String answer = viewController.getUserButtonSelection(message2, yesNo);

        if (answer.equals(yes)){
            bank.removeFieldOwner(tField);
            bank.removeFieldOwner(sField);

            bank.addFieldToPlayer(sourcePlayer, tField);
            bank.addFieldToPlayer(targetPlayer, sField);

            viewController.showOwner(sourceProperty, targetPlayer.getName(), targetPlayer.getPlayerColor());
            viewController.showOwner(targetProperty, sourcePlayer.getName(), sourcePlayer.getPlayerColor());
        }



    }

}
