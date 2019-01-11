package model.deck;

import controller.Drawer;

public class TeleportCard extends Card {
    public TeleportCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
