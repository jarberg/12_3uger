package model.deck;

import controller.Drawer;

public class GetOutOfJailCard extends Card{



    public GetOutOfJailCard(String description) {
        super(description);
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
