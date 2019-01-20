package model.deck;

import controller.Drawer;

public class GoToJail extends Card {
    private int position;

    public GoToJail(String description, int position) {
        super(description);

        this.position = position;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPosition() {
        return position;
    }
}


