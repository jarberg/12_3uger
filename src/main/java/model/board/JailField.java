package model.board;

import controller.Visitor;

import java.awt.*;

public class JailField extends Field {
    private int bailAmount;
    private int maxSuccessiveAttempts;

    public JailField(String ID, String title, String subtitle, String message, Color fieldColor, int releaseCharge, int maxSuccessiveAttempts){
        super(ID, title, subtitle, message, fieldColor);

        this.bailAmount = releaseCharge;
        this.maxSuccessiveAttempts = maxSuccessiveAttempts;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.landOnField(this);
    }

    public int getBailAmount(){
        return bailAmount;
    }

    public int getMaxSuccesiveAttempts(){
        return maxSuccessiveAttempts;
    }
}