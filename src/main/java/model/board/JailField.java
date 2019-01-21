package model.board;

import controller.Visitor;

import java.awt.*;

public class JailField extends Field {

    private int bailAmount;

    public JailField(String ID, String title, String subtitle, String message, Color fieldColor, int releaseCharge){
        super(ID, title, subtitle, message, fieldColor);

        this.bailAmount = releaseCharge;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.landOnField(this);
    }

    public int getBailAmount(){
        return bailAmount;
    }

}