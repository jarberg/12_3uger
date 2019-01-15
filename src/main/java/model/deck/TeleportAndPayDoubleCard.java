package model.deck;

import controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{

    private int position;
    private int multiplier;
    private int amount;

    public TeleportAndPayDoubleCard(String description, int position, int multiplier, int amount) {
        super(description);

        this.position = position;
        this.multiplier = multiplier;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPosition() {
        return position;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getAmount() {
        return amount;
    }
}
