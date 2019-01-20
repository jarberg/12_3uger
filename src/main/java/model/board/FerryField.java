package model.board;

import controller.Visitor;

import java.awt.*;

public class FerryField extends Ownable {



    public FerryField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int... rents ) {
        super(ID, title, subtitle, message, fieldColor,type,price,rents);

    }

    @Override
    public int getRent(int amountOwned) {
        return rents[amountOwned-1];
    }

    @Override
    public int getRent() {
        return 0;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }







}
