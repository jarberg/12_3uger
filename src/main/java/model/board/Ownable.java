package model.board;

import java.awt.*;


public abstract class Ownable extends Field {
    private boolean pawned;
    private String type;
    int[] rents;
    private int price;

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

    public abstract int getRent(int amount);

    public int getPrice(){
        return price;
    }
}