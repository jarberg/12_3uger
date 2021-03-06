package controller;

import model.board.*;
import model.player.Player;
import model.player.PlayerList;
import utilities.LanguageStringCollection;

import java.awt.*;

import static java.awt.Color.black;

public class TradeController {

    private static TradeController singleInstance = new TradeController();
    private static final int PRICE_INCREMENT = 50;

    private static PlayerFieldRelationController playerFieldRelationController = PlayerFieldRelationController.getSingleInstance();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private ViewControllerInterface viewController = ViewController.getSingleInstance();
    private PlayerList playerList;

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

    public void raiseMoney(Player player) {

        Field[] fieldsWithHouses = playerFieldRelationController.getFieldsWithHousesByPlayer(player);
        Field[] fieldsWithoutHouses = playerFieldRelationController.getUnpawnedFieldsWithNoHousesByPlayer(player);

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
                Field field = playerFieldRelationController.getFieldByName(sellingField);
                playerFieldRelationController.removeFieldOwner(field);
                viewController.showOwner(field.getTitle(), Color.BLACK);
                if(field instanceof Ownable){
                    player.addToBalance(((Ownable) field).getPrice() / 2);
                }
            }

            else if(choice.equals(sellHouseOption)){
                String[] fieldNames = new String[fieldsWithHouses.length];
                for (int i = 0; i < fieldNames.length; i++) {
                    fieldNames[i] = fieldsWithHouses[i].getTitle();
                }
                String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
                PropertyField field = ((PropertyField) playerFieldRelationController.getFieldByName(sellingField));
                field.removeBuilding();
                viewController.updateFieldBuildings(sellingField, (field.getBuildingCount()));
                player.addToBalance(field.getBuildingPrice() / 2);
            }

        }

        else if (fieldsWithHouses.length > 0){
            viewController.getUserButtonSelection(sellMessage, sellHouseOption);
            String[] fieldNames = new String[fieldsWithHouses.length];
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = fieldsWithHouses[i].getTitle();
            }
            String sellingField = viewController.getUserSelection(sellHouseOption, fieldNames);
            PropertyField field = ((PropertyField) playerFieldRelationController.getFieldByName(sellingField));
            field.removeBuilding();
            viewController.updateFieldBuildings(sellingField, (field.getBuildingCount()));
            player.addToBalance(field.getBuildingPrice() / 2);

        }

        else if (fieldsWithoutHouses.length > 0){
            viewController.getUserButtonSelection(sellMessage, sellFieldOption);
            String[] fieldNames = new String[fieldsWithoutHouses.length];
            for (int i = 0; i < fieldNames.length; i++) {
                fieldNames[i] = fieldsWithoutHouses[i].getTitle();
            }
            String sellingField = viewController.getUserSelection(sellFieldOption, fieldNames);
            Field field = playerFieldRelationController.getFieldByName(sellingField);
            playerFieldRelationController.removeFieldOwner(field);
            viewController.showOwner(field.getTitle(), Color.BLACK);

            if(field instanceof Ownable){
                player.addToBalance(((Ownable) field).getPrice() / 2);
            }
        }

        else {
            String brokeMessage = String.format(languageStringCollection.getMenu()[22], player.getName());
            viewController.showMessage(brokeMessage);
            player.setBrokeStatus(true);
            viewController.vanishPlayer(player.getName(), player.getPosition());
        }
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());
        if(player.getBrokeStatus()){
            Field[] pawnedFields = playerFieldRelationController.getPawnedFieldsByPlayer(player);
            for(Field field : pawnedFields){
                auction(player, playerList.getPlayersButPlayer(player), field);
                if(playerFieldRelationController.getOwnerOfField(field.getID()) == player){
                    playerFieldRelationController.removeFieldOwner(field);
                    viewController.showOwner(field.getTitle(), black);
                }
            }
        }

    }

    public void setPlayerList(PlayerList playerList){
        this.playerList = playerList;
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

    public void transferAssets(Player sourcePlayer){

        String message = languageStringCollection.getMenu()[37];
        String[] names = playerFieldRelationController.getPlayerNamesWithFieldsWithNoHouses();

        // update names so it does not include the sourcePlayer
        String[] temp = new String[names.length - 1];
        int counter = 0;
        for (String name : names) {
            if (!name.equals(sourcePlayer.getName())){
                temp[counter] = name;
                counter++;
            }
        }
        names = temp;

        String playerChoice = viewController.getUserSelection(message, names);
        Player targetPlayer = playerList.getPlayerByName(playerChoice);

        String message3 = String.format(languageStringCollection.getMenu()[38], sourcePlayer.getName());
        String sourceProperty = viewController.getUserSelection(message3, playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(sourcePlayer));
        Field sField = playerFieldRelationController.getFieldByName(sourceProperty);


        String message4 = languageStringCollection.getMenu()[39];
        String targetProperty = viewController.getUserSelection(message4, playerFieldRelationController.getPropertyNamesWithNoHousesByPlayer(targetPlayer));
        Field tField = playerFieldRelationController.getFieldByName(targetProperty);


        String message2 = String.format(languageStringCollection.getMenu()[40], targetPlayer.getName(), tField.getTitle(), sField.getTitle());
        String yes = languageStringCollection.getMenu()[16];
        String no = languageStringCollection.getMenu()[17];
        String answer = viewController.getUserButtonSelection(message2, yes, no);

        if (answer.equals(yes)){
            playerFieldRelationController.removeFieldOwner(tField);
            playerFieldRelationController.removeFieldOwner(sField);

            playerFieldRelationController.addFieldToPlayer(sourcePlayer, tField);
            playerFieldRelationController.addFieldToPlayer(targetPlayer, sField);

            viewController.showOwner(sourceProperty, targetPlayer.getPlayerColor());
            viewController.showOwner(targetProperty, sourcePlayer.getPlayerColor());
        }

    }

    public void transferAssets(Player targetPlayer, Field field){

        if(field instanceof Ownable){
            transferAssets(targetPlayer, -((Ownable)field).getPrice());

            if(!targetPlayer.getBrokeStatus()){
                playerFieldRelationController.addFieldToPlayer(targetPlayer, field);
                viewController.showOwner(field.getTitle(), targetPlayer.getPlayerColor());
            }
        }

    }

    public void askIfWantToBuy(Player player, Field field){

        String message = languageStringCollection.getMenu()[15];
        String yes = languageStringCollection.getMenu()[16];
        String no = languageStringCollection.getMenu()[17];
        String choice = viewController.getUserSelection(message, yes, no);

        if(choice.equals(yes)){
            transferAssets(player, field);
        } else if (choice.equals(no)){
            auction(player, playerFieldRelationController.getPlayers(), field);
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

    public void buyBackPawnedProperty(Player player){

        String message = languageStringCollection.getMenu()[56];
        Field[] pawnNames = playerFieldRelationController.getPawnedFieldsByPlayer(player);
        String[] fieldNames = new String[pawnNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            fieldNames[i] = pawnNames[i].getTitle();
        }

        String choice = viewController.getUserSelection(message, fieldNames);
        Field field = playerFieldRelationController.getFieldByName(choice);

        if (field instanceof Ownable){
            int priceWithInterest = (int) (((Ownable) field).getPrice() * 1.10);
            transferAssets(player, -priceWithInterest);
            if (!player.getBrokeStatus()) {
                ((Ownable) field).setPawnedStatus(false);
                playerFieldRelationController.removeFieldOwner(field);
                playerFieldRelationController.addFieldToPlayer(player, field);
                viewController.pawn(field.getTitle(), player.getPlayerColor(), player.getPlayerColor());
            }
        }

    }


    public void auction(Player player, Player[] allPlayers, Field field) {

        for(Player possiblyBrokePlayer : allPlayers){
            if(possiblyBrokePlayer.getBrokeStatus())
                allPlayers = removePlayerFromArray(allPlayers, possiblyBrokePlayer);
        }

        String auctionMessage = String.format(languageStringCollection.getMenu()[44], field.getTitle(), player.getName());
        viewController.showMessage(auctionMessage);
        Player[] auctionPlayers = new Player[0];
        for(Player otherPlayer : allPlayers){
            String auctionParticipationQuestion = String.format((languageStringCollection.getMenu()[45]), otherPlayer.getName());
            String yes = languageStringCollection.getMenu()[16];
            String no = languageStringCollection.getMenu()[17];
            String answer = viewController.getUserButtonSelection(auctionParticipationQuestion, yes, no);
            if(answer.equals(yes)){
                Player[] newAuctionPlayers = new Player[auctionPlayers.length + 1];
                for (int i = 0; i < auctionPlayers.length; i++) {
                    newAuctionPlayers[i] = auctionPlayers[i];
                }
                newAuctionPlayers[newAuctionPlayers.length - 1] = otherPlayer;
                auctionPlayers = newAuctionPlayers;
            }
        }

        int rounds = 0;
        int price = getFieldPrice(field);

        while(auctionPlayers.length > 1){
            rounds++;
            price = getFieldPrice(field) + PRICE_INCREMENT * rounds;
            String priceMessage = String.format(languageStringCollection.getMenu()[46], field.getTitle(), price);
            viewController.showMessage(priceMessage);
            for(Player auctionPlayer : auctionPlayers){
                String auctionParticipationQuestion = String.format((languageStringCollection.getMenu()[45]), auctionPlayer.getName());
                String yes = languageStringCollection.getMenu()[16];
                String no = languageStringCollection.getMenu()[17];
                String answer = viewController.getUserButtonSelection(auctionParticipationQuestion, yes, no);
                if(answer.equals(no)){
                    auctionPlayers = removePlayerFromArray(auctionPlayers, auctionPlayer);
                }
            }
        }

        if(auctionPlayers.length > 0){
            player = auctionPlayers[0];
            while(player.getBalance() < price){
                raiseMoney(player);
                if(player.getBrokeStatus())
                    break;
            }
            if(!player.getBrokeStatus()){
                player.addToBalance(-price);
                playerFieldRelationController.addFieldToPlayer(auctionPlayers[0], field);
                viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());
                viewController.showOwner(field.getTitle(), player.getPlayerColor());
                String message = String.format(languageStringCollection.getMenu()[53], player.getName(), field.getTitle(), String.valueOf(price));
                viewController.showMessage(message);
            }
        } else if(field instanceof Ownable){
            if(((Ownable) field).getPawnedStatus()){
                ((Ownable) field).setPawnedStatus(false);
                playerFieldRelationController.removeFieldOwner(field);
                viewController.showOwner(field.getTitle(), black);
            }
        }

    }

    private Player[] removePlayerFromArray(Player[] array, Player toBeRemoved){

        Player[] newArray = new Player[array.length - 1];
        int counter = 0;
        for (Player anArray : array) {
            if (anArray != toBeRemoved) {
                newArray[counter] = anArray;
                counter++;
            }
        }
        return newArray;

    }

    private int getFieldPrice(Field field){

        int price = 0;
        if(field instanceof Ownable){
            price = ((Ownable) field).getPrice();
        }

        return price;

    }

    public void setViewController(ViewControllerInterface viewController) {
        this.viewController = viewController;
    }

}
