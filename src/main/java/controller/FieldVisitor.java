package controller;

import model.board.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

public class FieldVisitor implements Visitor  {

    private static final int PROPERTY_MULTIPLIER = 2;
    private Player player;
    private Player[] otherPlayers;
    private Deck deck;
    private Board board;
    private ViewControllerInterface viewController = ViewController.getSingleInstance();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private TradeController tradeController = TradeController.getSingleInstance();
    private static Bank bank = Bank.getSingleInstance();

    public FieldVisitor(Player currentPlayer, Player[] otherPlayers,  Deck deck, Board board) {
        this.otherPlayers = otherPlayers;
        this.player = currentPlayer;
        this.deck = deck;
        this.board = board;
    }

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
        player.setPosition(10);
        player.setInJail(true);
    }

    @Override
    public void visit(JailField field) {
        viewController.showMessage(field.getMessage());
    }

    @Override
    public void visit(ParkingField field) {
        viewController.showMessage(field.getMessage());
    }

    @Override
    public void visit(PropertyField field) {
        viewController.showMessage(field.getMessage());

        boolean playerIsOwner = bank.isOwner(player, field);
        if(!playerIsOwner){

            boolean ownedByAnotherPlayer = bank.hasOwner(field.getID());
            if(ownedByAnotherPlayer){
                boolean fieldIsPawned = field.getPawnedStatus();
                if(fieldIsPawned){
                    String message = String.format(languageStringCollection.getMenu()[26], bank.getOwner(field.getID()).getName());
                    viewController.showMessage(message);
                } else{
                    Player owner = bank.getOwner(field.getID());
                    boolean ownerOwnsAllOfType = bank.isOwnerOfAllFieldsOfType(owner, field);
                    if(ownerOwnsAllOfType)
                        tradeController.transferAssets(player, owner, field.getRent() * PROPERTY_MULTIPLIER);
                    else
                        tradeController.transferAssets(player, owner, field.getRent());
                }
            } else{
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

    @Override
    public void visit(BreweryField field) {
        viewController.showMessage(field.getMessage());

        boolean playerIsOwner = bank.isOwner(player, field);
        if(!playerIsOwner){
            int diceRoll = player.getPosition() - player.getLastPosition();

            boolean ownedByAnotherPlayer = bank.hasOwner(field.getID());
            if(ownedByAnotherPlayer){
                boolean fieldIsPawned = field.getPawnedStatus();
                if(fieldIsPawned){
                    String message = String.format(languageStringCollection.getMenu()[26], bank.getOwner(field.getID()).getName());
                    viewController.showMessage(message);
                } else{
                    Player owner = bank.getOwner(field.getID());
                    boolean ownerOwnsBoth = bank.isOwnerOfAllFieldsOfType(owner, field);
                    if(ownerOwnsBoth)
                        tradeController.transferAssets(player,owner, diceRoll * field.getMultiplier2());
                    else
                        tradeController.transferAssets(player, owner, diceRoll * field.getMultiplier1());
                }
            } else{
                tradeController.askIfWantToBuy(player, field);
            }
        }
    }

    @Override
    public void visit(FerryField field) {
        viewController.showMessage(field.getMessage());

        boolean playerIsOwner = bank.isOwner(player, field);
        if(!playerIsOwner){
            boolean ownedByAnotherPlayer = bank.hasOwner(field.getID());
            if(ownedByAnotherPlayer){
                boolean fieldIsPawned = field.getPawnedStatus();
                if(fieldIsPawned){
                    String message = String.format(languageStringCollection.getMenu()[26], bank.getOwner(field.getID()).getName());
                    viewController.showMessage(message);
                } else{
                    Player owner = bank.getOwner(field.getID());
                    int amountOwned = bank.getAmountOfTypeOwned(owner, field);
                    tradeController.transferAssets(player, owner, field.getRent(amountOwned));
                }
            }
            else{
                tradeController.askIfWantToBuy(player, field);
            }
        }
    }

}