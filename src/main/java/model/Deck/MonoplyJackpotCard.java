package model.Deck;

import Controller.Drawer;

public class MonoplyJackpotCard extends Card {

    public MonoplyJackpotCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}

