package model.deck;

import controller.Drawer;

public class MoneyCard extends Card {

    //TODO: add int "amount" attribute and appropriate getter
    //Note: This will require small changes in the test class.
    public MoneyCard(String description, int amount) {
        super(description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
