import java.awt.*;

public class PropertyField extends Field {

    private String type;
    private int price;
    private int buildingPrice;
    private int buildingCount;
    private int[] rents;

    public PropertyField(String title, String subtitle, String description, String message, Color fillColor, String type, int price, int buildingPrice, int buildingCount, int... rents){
        super(title, subtitle, description, message, fillColor);

        this.type = type;
        this.price = price;
        this.buildingPrice = buildingPrice;
        this.buildingCount = buildingCount;
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

    public String getType(){
        return type;
    }

    public int getRent(){
        return rents[buildingCount];
    }

}