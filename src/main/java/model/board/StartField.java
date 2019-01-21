package model.board;

import controller.Visitor;

import java.awt.*;

public class StartField extends Field{

    private int amount;

    public StartField(String ID, String title, String subtitle, String message, Color fieldColor, int amount){
        super(ID, title, subtitle, message, fieldColor);

        this.amount = amount;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.landOnField(this);
    }

    public int getAmount(){
        return amount;
    }

}