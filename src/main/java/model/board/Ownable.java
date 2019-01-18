package model.board;

import java.awt.*;


public abstract class Ownable extends Field {

    private boolean pawned;
    private String type;

    public Ownable(String ID, String title, String subtitle, String message, Color fieldColor,String type) {
        super(ID,title,subtitle,message,fieldColor);
        this.type = type;
    }

    public boolean getPawnedStatus(){ return this.pawned; }

    public void setPawnedStatus(boolean status){
        this.pawned = status;
    }

    public String getType() {
        return type;
    }
}
