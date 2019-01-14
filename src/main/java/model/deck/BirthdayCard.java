package model.deck;

import controller.Drawer;

public class BirthdayCard extends Card {

    private int amount;

    public BirthdayCard(String description) {
        super(description);

        this.amount = amount;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getAmount() {
        return amount;
    }
}
