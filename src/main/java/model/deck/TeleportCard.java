package model.deck;

import controller.Drawer;

public class TeleportCard extends Card {

    private int position;

    public TeleportCard(String description, int position) {
        super(description);

        this.position = position;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPosition() {
        return position;
    }
}
