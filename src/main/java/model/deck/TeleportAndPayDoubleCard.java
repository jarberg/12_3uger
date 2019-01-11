package model.Deck;

import Controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{
    public TeleportAndPayDoubleCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
