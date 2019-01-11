package model.Board;

import Controller.Visitor;

import java.awt.*;

public class ParkingField extends Field {

    public ParkingField(String ID, String title, String subtitle, String message, Color fieldColor){
        super(ID, title, subtitle, message, fieldColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}