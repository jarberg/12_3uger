package model.deck;

import controller.Drawer;

public class BirthdayCard extends Card {
    public BirthdayCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
