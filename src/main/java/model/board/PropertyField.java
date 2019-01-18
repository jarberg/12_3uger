package model.board;

import controller.Visitor;

import java.awt.*;

public class PropertyField extends Ownable {


    private int price;
    private int buildingPrice;
    private int buildingCount;
    private int[] rents;


    public PropertyField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int buildingPrice, int... rents){
        super(ID, title, subtitle, message, fieldColor,type);


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
        if(buildingCount == 4)
            return buildingPrice * 5;
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

    public int getRent(){
            return rents[buildingCount];
    }

}