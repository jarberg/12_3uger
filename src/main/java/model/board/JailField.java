package model.board;

import controller.Visitor;

import java.awt.*;

public class JailField extends Field {

    private int releaseCharge;
    private int maxSuccessiveAttempts;


    public JailField(String ID, String title, String subtitle, String message, Color fieldColor, int releaseCharge, int maxSuccessiveAttempts){
        super(ID, title, subtitle, message, fieldColor);

        this.releaseCharge = releaseCharge;
        this.maxSuccessiveAttempts = maxSuccessiveAttempts;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getReleaseCharge(){
        return releaseCharge;
    }

    public int getMaxSuccesiveAttempts(){
        return maxSuccessiveAttempts;
    }

}