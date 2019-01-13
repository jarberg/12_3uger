package model.deck;

import controller.Drawer;

public class MonopolyJackpotCard extends Card {

    private int amount;
    private int ifOver;

    public MonopolyJackpotCard(String description, int amount, int ifOver) {
        super(description);

        this.amount = amount;
        this.ifOver = ifOver;

    }

    public void accept(Drawer drawer) {
        drawer.draw(this);
    }


    public int getAmount() {
        return amount;
    }

    public int getIfOver() {
        return ifOver;
    }
}