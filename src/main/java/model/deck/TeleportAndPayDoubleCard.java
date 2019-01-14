package model.deck;

import controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{

    private int position;
    private int multiplier;

    public TeleportAndPayDoubleCard(String description, int position, int multiplier) {
        super(description);

        this.position = position;
        this.multiplier = multiplier;
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPosition() {
        return position;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
