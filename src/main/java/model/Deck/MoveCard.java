package model.Deck;

import Controller.Drawer;

public class MoveCard extends Card{
    public MoveCard(String title, String description) {
        super(title, description);
    }

    public void accept (Drawer drawer){
        drawer.draw(this);
    }
}
