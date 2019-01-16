package model.deck;

import controller.Drawer;

public class MoveToFieldCard extends Card{

    private int amount;

    public MoveToFieldCard(String description, int amount) {
        super(description);

        this.amount = amount;
    }

    @Override
    public void accept (Drawer drawer){
        drawer.draw(this);
    }

    public int getAmount() {
        return amount;
    }
}
