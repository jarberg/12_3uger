package model.deck;

import controller.Drawer;

public class GetOutOfJailCard extends Card{
    public GetOutOfJailCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
