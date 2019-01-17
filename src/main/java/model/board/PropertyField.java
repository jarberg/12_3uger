package model.board;

import controller.Visitor;

import java.awt.*;

public class PropertyField extends Field {

    private String type;
    private int price;
    private int buildingPrice;
    private int buildingCount;
    private int[] rents;
    private boolean owned=false ;
    private boolean pawned;

    public PropertyField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int buildingPrice, int... rents){
        super(ID, title, subtitle, message, fieldColor);

        this.type = type;
        this.price = price;
        this.buildingPrice = buildingPrice;
        this.rents = rents;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getPrice(){
        return price;
    }

    public int getBuildingPrice(){
        return buildingPrice;
    }

    public int getBuildingCount(){
        return buildingCount;
    }

    public void addBuilding(){
        buildingCount++;
    }

    public void removeBuilding(){
        buildingCount--;
    }

    public String getType(){
        return type;
    }

    public int getRent(){
            return rents[buildingCount];
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean getPawnedStatus(){
        return this.pawned;
    }

    public void setPawnedStatus(boolean status){
        this.pawned = status;
    }
}