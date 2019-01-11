package model.board;

import java.awt.*;

public class StartField extends Field{

    private int amount;

    public StartField(String ID, Color fieldColor, int amount){
        super(ID, fieldColor);
        this.amount = amount;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getAmount(){
        return amount;
    }

}