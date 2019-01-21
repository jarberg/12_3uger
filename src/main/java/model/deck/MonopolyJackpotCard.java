package model.deck;

import controller.Drawer;

public class MonopolyJackpotCard extends Card {

    private int amount;
    private int jackpot;

    public MonopolyJackpotCard(String description, int amount, int jackpot) {
        super(description);

        this.amount = amount;
        this.jackpot = jackpot;

    }

    @Override
    public void accept(Drawer drawer) {
        drawer.draw(this);
    }


    public int getAmount() {
        return amount;
    }

    public int getJackpot() {
        return jackpot;
    }

}