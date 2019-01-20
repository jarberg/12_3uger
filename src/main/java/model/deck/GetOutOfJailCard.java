package model.deck;

import controller.Drawer;

public class GetOutOfJailCard extends Card{
    private int price;

    public GetOutOfJailCard(String description, int price) {
        super(description);
        this.price = price;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getPrice() {
        return price;
    }
}