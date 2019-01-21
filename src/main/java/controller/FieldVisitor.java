package controller;

import model.board.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;
import utilities.LanguageStringCollection;

public class FieldVisitor implements Visitor {

    private static final int PROPERTY_MULTIPLIER = 2;

    private static PlayerFieldRelationController playerFieldRelationController = PlayerFieldRelationController.getSingleInstance();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private TradeController tradeController = TradeController.getSingleInstance();
    private ViewControllerInterface viewController;
    private Player[] otherPlayers;
    private Player player;
    private Board board;
    private Deck deck;

    public FieldVisitor(Player currentPlayer, Player[] otherPlayers,  Deck deck, Board board, ViewControllerInterface viewController){
        this.viewController = viewController;
        this.otherPlayers = otherPlayers;
        this.player = currentPlayer;
        this.board = board;
        this.deck = deck;
    }

    @Override
    public void landOnField(ChanceField field){

        Card card = deck.getTopCard();
        deck.putTopCardToBottom();
        DrawController drawer = new DrawController(player, otherPlayers, playerFieldRelationController, board, deck, viewController);
        card.accept(drawer);

    }

    @Override
    public void landOnField(GoToJailField field){

        viewController.showMessage(field.getMessage());
        viewController.movePlayer(player.getName(),player.getPosition(),20);
        player.setPositionWithoutStartMoney(10);
        player.setInJail(true);
        player.setDoubleTurnStatus(false);


    }

    @Override
    public void landOnField(JailField field) {

        if(player.isInJail() && player.getCurrentTurn()>2+player.getJailTurn()) {

        }else if(player.isInJail()){
            viewController.showMessage(String.format(languageStringCollection.getMenu()[49],player.getName()));
        }
        else{
            viewController.showMessage(field.getMessage());
        }


    }

    @Override
    public void landOnField(ParkingField field) {
        viewController.showMessage(field.getMessage());
    }

    @Override
    public void landOnField(Ownable field) {

        viewController.showMessage(field.getMessage());
        int diceRoll = player.getPosition() - player.getLastPosition();
        boolean playerIsOwner = playerFieldRelationController.isPlayerOwner(player, field);
        if(!playerIsOwner){

            boolean ownedByAnotherPlayer = playerFieldRelationController.fieldHasOwner(field.getID());
            if(ownedByAnotherPlayer){

                boolean fieldIsPawned = field.getPawnedStatus();
                if(fieldIsPawned){
                    String message = String.format(languageStringCollection.getMenu()[26], playerFieldRelationController.getOwnerOfField(field.getID()).getName());
                    viewController.showMessage(message);
                } else{
                    Player owner = playerFieldRelationController.getOwnerOfField(field.getID());

                    boolean ownerOwnsAllOfType = playerFieldRelationController.isOwnerOfAllFieldsOfType(owner, field);
                    if(field instanceof  PropertyField)
                        if(ownerOwnsAllOfType)
                            tradeController.transferAssets(player, owner, field.getRent(((PropertyField) field).getBuildingCount()) * PROPERTY_MULTIPLIER);
                        else
                            tradeController.transferAssets(player, owner, field.getRent(((PropertyField) field).getBuildingCount()));
                    else if(field instanceof BreweryField) {
                        if (ownerOwnsAllOfType)
                            tradeController.transferAssets(player, owner, diceRoll * ((BreweryField)field).getMultiplier2());
                        else
                            tradeController.transferAssets(player, owner, diceRoll * ((BreweryField)field).getMultiplier1());
                    }
                    else if(field instanceof  FerryField){
                        int amountOwned = playerFieldRelationController.getAmountOfTypeOwned(owner, field);
                        tradeController.transferAssets(player, owner, field.getRent(amountOwned));
                    }
                }

            }
            else{
                tradeController.askIfWantToBuy(player, field);
            }
        }

    }


    @Override
    public void landOnField(TaxField field) {

        viewController.showMessage(field.getMessage());

        if (field.getPercentage() == 0) {
            String flatAmount = languageStringCollection.getMenu()[23];
            viewController.getUserSelection("", flatAmount);
            tradeController.transferAssets(player, -field.getFlatAmount());
        } else {
            String message = languageStringCollection.getMenu()[12];
            String flatAmount = languageStringCollection.getMenu()[13];
            String percentage = languageStringCollection.getMenu()[14];
            String choice = viewController.getUserSelection(message, flatAmount, percentage);
            if(choice.equals(flatAmount)){
                tradeController.transferAssets(player, -field.getFlatAmount());
            } else if (choice.equals(percentage)){
                int netWorth = playerFieldRelationController.getNetWorth(player);
                int amount = (int)(netWorth * (field.getPercentage()/100.00));
                tradeController.transferAssets(player, -amount);
            }
        }

    }

    @Override
    public void landOnField(StartField field) {
        viewController.showMessage(field.getMessage());
    }

}