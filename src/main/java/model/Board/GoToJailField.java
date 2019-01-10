package model.Board;

import java.awt.*;

public class GoToJailField extends Field{

    public GoToJailField(String ID, Color fieldColor){
        super(ID, fieldColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}