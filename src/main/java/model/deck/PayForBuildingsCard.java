package model.Deck;

import Controller.Drawer;

public class PayForBuildingsCard extends Card {
    public PayForBuildingsCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
