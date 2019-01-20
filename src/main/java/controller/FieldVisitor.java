package controller;

import model.board.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;
import utilities.LanguageStringCollection;

public class FieldVisitor implements Visitor  {

    private static final int PROPERTY_MULTIPLIER = 2;
    private Player player;
    private Player[] otherPlayers;
    private Deck deck;
    private Board board;
    private ViewControllerInterface viewController;
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private TradeController tradeController = TradeController.getSingleInstance();
    private static Bank bank = Bank.getSingleInstance();

    public FieldVisitor(Player currentPlayer, Player[] otherPlayers,  Deck deck, Board board, ViewControllerInterface viewController) {
        this.otherPlayers = otherPlayers;
        this.player = currentPlayer;
        this.deck = deck;
        this.board = board;
        this.viewController = viewController;
    }
    @Override
    public void visit(ChanceField field) {
        Card card = deck.getTopCard();
        deck.putTopCardToBottom();
        DrawController drawer = new DrawController(player, otherPlayers, bank, board, deck, viewController);
        card.accept(drawer);
    }

    @Override
    public void visit(GoToJailField field) {
        viewController.showMessage(field.getMessage());
        viewController.movePlayer(player.getName(),player.getPosition(),20);
        player.setPositionWithoutStartMoney(10);
        player.setInJail(true);
        player.setDoubleTurnStatus(false);

    }

    @Override
    public void visit(JailField field) {
        if(player.isInJail()) {
            viewController.showMessage(String.format(languageStringCollection.getMenu()[49],player.getName()));
        }else{
            viewController.showMessage(field.getMessage());
        }
    }

    @Override
    public void visit(ParkingField field) {
        viewController.showMessage(field.getMessage());
    }

    @Override
    public void visit(Ownable field) {
        viewController.showMessage(field.getMessage());
        int diceRoll = player.getPosition() - player.getLastPosition();
        boolean playerIsOwner = bank.isPlayerOwner(player, field);
        if(!playerIsOwner){

            boolean ownedByAnotherPlayer = bank.fieldHasOwner(field.getID());
            if(ownedByAnotherPlayer){
                boolean fieldIsPawned = field.getPawnedStatus();
                if(fieldIsPawned){
                    String message = String.format(languageStringCollection.getMenu()[26], bank.getOwnerOfField(field.getID()).getName());
                    viewController.showMessage(message);
                } else{
                    Player owner = bank.getOwnerOfField(field.getID());
                    boolean ownerOwnsAllOfType = bank.isOwnerOfAllFieldsOfType(owner, field);
                    if(field instanceof  PropertyField)
                        if(ownerOwnsAllOfType)
                            tradeController.transferAssets(player, owner, field.getRent() * PROPERTY_MULTIPLIER);
                        else
                            tradeController.transferAssets(player, owner, field.getRent());
                    else if(field instanceof BreweryField) {
                        if (ownerOwnsAllOfType)
                            tradeController.transferAssets(player, owner, diceRoll * ((BreweryField)field).getMultiplier2());
                        else
                            tradeController.transferAssets(player, owner, diceRoll * ((BreweryField)field).getMultiplier1());
                    }
                    else if(field instanceof  FerryField){
                        int amountOwned = bank.getAmountOfTypeOwned(owner, field);
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
    public void visit(TaxField field) {
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
                int netWorth = bank.getNetWorth(player);
                int amount = (int)(netWorth * (field.getPercentage()/100.00));
                tradeController.transferAssets(player, -amount);
            }
        }

    }

    @Override
    public void visit(StartField field) {
        viewController.showMessage(field.getMessage());
    }


}