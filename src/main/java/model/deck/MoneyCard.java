package model.deck;

import controller.Drawer;

public class MoneyCard extends Card {
    public MoneyCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
