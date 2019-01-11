package model.Deck;

import Controller.Drawer;

public class TransactionToAllPlayersCard extends Card {
    public TransactionToAllPlayersCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
