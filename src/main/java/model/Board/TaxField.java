package model.Board;

import Controller.Visitor;

import java.awt.*;

public class TaxField extends Field{

    private int flatAmount;
    private int percentage;

    public TaxField(String ID, String title, String subtitle, String message, Color fieldColor, int flatAmount, int percentage){
        super(ID, title, subtitle, message, fieldColor);

        this.flatAmount = flatAmount;
        this.percentage = percentage;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getFlatAmount(){
        return flatAmount;
    }

    public int getPercentage(){
        return percentage;
    }

}