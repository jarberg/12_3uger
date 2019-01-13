package model.deck;

import controller.Drawer;

public class PayForBuildingsCard extends Card {
    private int house;
    private int hotel;

    //make int house, int hotel into attributes and get methods

    public PayForBuildingsCard(String description, int house, int hotel) {
        super(description);

        this.house = house;
        this.hotel = hotel;
    }


    public void accept(Drawer drawer) {
        drawer.draw(this);
    }


    public int getHotel() {
        return hotel;
    }

    public int getHouse() {
        return house;
    }

}