package model.board;

import controller.Visitor;

import java.awt.*;

public class ChanceField extends Field {

    public ChanceField(String ID, String title, String subtitle, String message, Color fieldColor){
        super(ID, title, subtitle, message, fieldColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.landOnField(this);
    }
}