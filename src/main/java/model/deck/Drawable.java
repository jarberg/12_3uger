package model.deck;

import controller.Drawer;

public interface Drawable {

    //TODO: In the different Card classes, indicate with @Override when this method is implemented.
    void accept(Drawer drawer);

}
