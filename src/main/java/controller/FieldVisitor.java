package controller;

import model.board.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;
import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;

public class FieldVisitor implements Visitor  {

    private static final int JAIL_MOVEMENT = 20;
    private static final int PROPERTY_MULTIPLIER = 2;
    private Player player;
    private Player[] otherPlayers;
    private Deck deck = new Deck(LogicStringCollection.getSingleInstance().getChanceCard());
    private ViewController viewController = ViewController.getSingleInstance();
    private LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private TradeController tradeController = TradeController.getSingleInstance();

    public FieldVisitor(Player currentPlayer, Player[] otherPlayers) {
        this.otherPlayers = otherPlayers;
        this.player = currentPlayer;
    }

    @Override
    public void visit(ChanceField field) {
        Card card = deck.getTopCard();
        deck.putTopCardToBottom();
        DrawController drawer = new DrawController(player, otherPlayers);
        card.accept(drawer);
    }

    @Override
    public void visit(GoToJailField field) {
        viewController.showMessage(field.getMessage());
        viewController.movePlayer(player.getName(),player.getPosition(),20);
        player.setPosition(10);
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
        /*viewController.showMessage(field.getMessage());

        boolean playerIsOwner = bank.isOwner(player, field);
        if(!playerIsOwner){

            boolean ownedByAnotherPlayer = bank.hasOwner(field);
            if(ownedByAnotherPlayer){
                Player owner = bank.getOwner(field);
                boolean ownerOwnsAllOfType = bank.ownerOfAKind(owner, field);
                if(ownerOwnsAllOfType)
                    tradeController.transferAssets(player, owner, field.getRent() * PROPERTY_MULTIPLIER);
                else
                    tradeController.transferAssets(player, owner, field.getRent());

            } else{
                String message = languageStringCollection.getMenu()[15];
                String yes = languageStringCollection.getMenu()[16];
                String no = languageStringCollection.getMenu()[17];
                String choice = viewController.getUserSelection(message, yes, no);
                if(choice.equals(yes)){
                    tradeController.transferAssets(player, field);
                } else if (choice.equals(no)){
                    tradeController.auctionField(field);
                }
                }
            }
        }

        */
    }

    @Override
    public void visit(TaxField field) {
        viewController.showMessage(field.getMessage());
        String message = languageStringCollection.getMenu()[12];
        String flatAmount = languageStringCollection.getMenu()[13];
        String percentage = languageStringCollection.getMenu()[14];
        String choice = viewController.getUserSelection(message, flatAmount, percentage);
        if(choice.equals(flatAmount)){
            tradeController.transferAssets(player, -field.getFlatAmount());
        } else if (choice.equals(percentage)){
            //int netWorth = fieldOwnerBank.getNetWorth(player);
            //int amount = (int)(netWorth * (field.getPercentage()/100.00));
            //tradeController.transferAssets(player, -amount);
        }
    }

    @Override
    public void visit(StartField field) {
        viewController.showMessage(field.getMessage());
    }

    @Override
    public void visit(BreweryField field) {
        /*viewController.showMessage(field.getMessage());

        //boolean playerIsOwner = bank.isOwner(player, field);
        if(!playerIsOwner){
            int diceRoll = player.getPosition() - player.getLastPosition();

            boolean ownedByAnotherPlayer = bank.hasOwner(field);
            if(ownedByAnotherPlayer){

                Player owner = bank.getOwner(field);
                boolean ownerOwnsBoth = bank.ownerOfAKind(owner, field);
                if(ownerOwnsBoth)
                    tradeController.transferAssets(player,owner, diceRoll * field.getMultiplier2());
                else
                    tradeController.transferAssets(player, owner, diceRoll * field.getMultiplier1());
            } else{
                String message = languageStringCollection.getMenu()[15];
                String yes = languageStringCollection.getMenu()[16];
                String no = languageStringCollection.getMenu()[17];
                String choice = viewController.getUserSelection(message, yes, no);
                if(choice.equals(yes)){
                    tradeController.transferAssets(player, field);
                } else if (choice.equals(no)){
                    tradeController.auctionField(field);
                }
                }
            }
        }

        */
    }
}
