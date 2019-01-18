package model.board;

import controller.Visitor;

import java.awt.*;

public class FerryField extends Ownable {
    private int price;
    private int[] rents;


    public FerryField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int... rents ) {
        super(ID, title, subtitle, message, fieldColor,type);
        this.price = price;
        this.rents = rents;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getPrice() {
        return price;
    }



    public int getRent(int amountOwned) {
        return rents[amountOwned-1];
    }



}
