package controller;

import model.board.*;
import model.deck.Card;
import model.deck.Deck;
import model.player.Player;
import model.text.LogicStringCollection;

public class FieldVisitor implements Visitor  {

    private Player player;
    private Player[] otherPlayers;
    Deck deck = new Deck(LogicStringCollection.getSingleInstance().getChanceCard());
    ViewController viewController = ViewController.getSingleInstance();

    public FieldVisitor(Player currentPlayer, Player[] otherPlayers) {
        this.otherPlayers = otherPlayers;
        this.player = currentPlayer;
    }

    @Override
    public void visit(ChanceField field) {
        Card card =deck.getTopCard();
        deck.putTopCardToBottom();
        DrawController test = new DrawController(player, otherPlayers);
        card.accept(test);
    }

    @Override
    public void visit(GoToJailField field) {

        viewController.showMessage(player.getName()+": Oooops");
        player.setPosition(30);
        viewController.movePlayer(player.getName(),player.getPosition(),20);
        player.setPosition(10);

    }

    @Override
    public void visit(JailField field) {

    }

    @Override
    public void visit(ParkingField field) {

    }

    @Override
    public void visit(PropertyField field) {

    }

    @Override
    public void visit(TaxField field) {

    }

    @Override
    public void visit(StartField field) {

    }

    @Override
    public void visit(BreweryField field) {

    }
}
