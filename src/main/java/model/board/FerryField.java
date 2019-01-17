package model.board;

import controller.Visitor;

import java.awt.*;

public class FerryField extends Field {
    private String type;
    private int price;
    private int[] rents;
    private boolean pawned;

    public FerryField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int... rents) {
        super(ID, title, subtitle, message, fieldColor);
        this.type = type;
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

    public String getType() {
        return type;
    }

    public int getRent(int amountOwned) {
        return rents[amountOwned-1];
    }

    public boolean getPawnedStatus(){
        return this.pawned;
    }

    public void setPawnedStatus(boolean status){
        this.pawned = status;
    }

}
