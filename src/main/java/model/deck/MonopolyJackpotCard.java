package model.deck;

import controller.Drawer;

public class MonopolyJackpotCard extends Card {

    public MonopolyJackpotCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}

