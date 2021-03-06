package model.board;

import controller.Visitor;

import java.awt.*;

public class FerryField extends Ownable {

    public FerryField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int... rents ) {
        super(ID, title, subtitle, message, fieldColor,type,price,rents);

    }

    @Override
    public int getRent(int amountOwned) {
        return super.getRent(amountOwned);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.landOnField(this);
    }

}
