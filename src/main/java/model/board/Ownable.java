package model.board;

import java.awt.*;

public abstract class Ownable extends Field {
    private boolean pawned;
    private String type;
    private int price;
    private int[] rents;

    public Ownable(String ID, String title, String subtitle, String message, Color fieldColor,String type, int price, int...rents) {
        super(ID,title,subtitle,message,fieldColor);
        this.type = type;
        this.rents = rents;
        this.price = price;
    }

    public boolean getPawnedStatus(){ return this.pawned; }

    public void setPawnedStatus(boolean status){
        this.pawned = status;
    }

    public String getType() {
        return type;
    }

    public int getRent(int amount){
        return rents[amount - 1];
    }

    public int getPrice(){
        return price;
    }
}