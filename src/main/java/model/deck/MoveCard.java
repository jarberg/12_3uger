package model.deck;

import controller.Drawer;

public class MoveCard extends Card{

    private int amount;

    //TODO: Make sure the parameter "int amount" is used in constructor
    public MoveCard(String description,int amount) {
        super(description);
    }

    public void accept (Drawer drawer){
        drawer.draw(this);
    }

    public int getAmount() {
        return amount;
    }
}
