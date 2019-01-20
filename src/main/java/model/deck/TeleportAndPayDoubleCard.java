package model.deck;

import controller.Drawer;

public class TeleportAndPayDoubleCard extends Card{

    private int multiplier;
    private int price1field;
    private int price2field;
    private int price3field;
    private int price4field;

    public TeleportAndPayDoubleCard(String description, int multiplier,int price1field,int price2field,int price3field, int price4field) {
        super(description);

        this.multiplier = multiplier;
        this.price1field = price1field;
        this.price2field = price2field;
        this.price3field = price3field;
        this.price4field = price4field;
    }

    @Override
    public void accept (Drawer drawer) {
        drawer.draw(this);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getPrice1field() {
        return price1field;
    }

    public int getPrice2field() {
        return price2field;
    }

    public int getPrice3field() {
        return price3field;
    }

    public int getPrice4field() {
        return price4field;
    }
}
