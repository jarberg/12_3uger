package model.Board;

import java.awt.*;

public class ParkingField extends Field {

    public ParkingField(String ID, Color fieldColor){
        super(ID, fieldColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}