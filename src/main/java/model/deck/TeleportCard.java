package model.deck;

import controller.Drawer;

public class TeleportCard extends Card {

    private int position;

    //TODO: Make sure parameter "int position" is used in constructor
    public TeleportCard(String description, int position) {
        super(description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPosition() {
        return position;
    }
}
