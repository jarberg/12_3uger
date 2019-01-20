package model.deck;

import controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{

    private int multiplier;

    public TeleportAndPayDoubleCard(String description, int multiplier) {
        super(description);

        this.multiplier = multiplier;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getMultiplier() {
        return multiplier;
    }
}
