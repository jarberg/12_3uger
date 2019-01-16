package model.deck;

import controller.Drawer;

public class MoveToFieldCard extends Card{

    private int destination;

    public MoveToFieldCard(String description, int destination) {
        super(description);

        this.destination = destination;
    }

    @Override
    public void accept (Drawer drawer){
        drawer.draw(this);
    }

    public int getDestination() {
        return destination;
    }
}
