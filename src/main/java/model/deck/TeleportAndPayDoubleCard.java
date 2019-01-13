package model.deck;

import controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{

    //TODO: Add int "position" attribute and getter like with Teleport Card
    //TODO: Consider adding "Multiplier" attribute and getter

    public TeleportAndPayDoubleCard(String description) {
        super(description);
    }

    public void accept (Drawer drawer) {
        drawer.draw(this);
    }
}
