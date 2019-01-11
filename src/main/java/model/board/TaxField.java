package model.board;

import java.awt.*;

public class TaxField extends Field{

    private int flatAmount;
    private int percentage;

    public TaxField(String ID, Color fieldColor, int flatAmount, int percentage){
        super(ID, fieldColor);

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