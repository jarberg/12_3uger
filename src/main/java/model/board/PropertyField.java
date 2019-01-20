package model.board;

import controller.Visitor;

import java.awt.*;

public class PropertyField extends Ownable {


    private int buildingPrice;
    private int buildingCount;



    public PropertyField(String ID, String title, String subtitle, String message, Color fieldColor, String type, int price, int buildingPrice, int... rents){
        super(ID, title, subtitle, message, fieldColor,type,price,rents);

        this.buildingPrice = buildingPrice;

    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
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

    @Override
    public int getRent(int buildingCount) {
        return rents[buildingCount];
    }
    @Override
    public int getRent() { return rents[buildingCount]; }
}