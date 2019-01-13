package model.deck;

import controller.Drawer;

public class BirthdayCard extends Card {

    //TODO: Add "amount" attribute and getter
    //Note: This will require a small change in the test class.

    public BirthdayCard(String description) {
        super(description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
