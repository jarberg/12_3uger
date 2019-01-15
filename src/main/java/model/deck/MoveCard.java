package model.deck;

import controller.Drawer;

public class MoveCard extends Card{

    private int amount;

    public MoveCard(String description,int amount) {
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
