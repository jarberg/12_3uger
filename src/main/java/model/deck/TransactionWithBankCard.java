package model.deck;

import controller.Drawer;

public class TransactionWithBankCard extends Card {
    public TransactionWithBankCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
