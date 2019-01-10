import java.awt.*;

public class PropertyField extends Field {

    private String type;
    private int price;
    private int buildingPrice;
    private int buildingCount;
    private int[] rents;

    public PropertyField(String ID, Color fieldColor, String type, int price, int buildingPrice, int... rents){
        super(ID, fieldColor);

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

    public int getRent() throws RentNotSpecifiedException {
        try{
            return rents[buildingCount];
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RentNotSpecifiedException("Rent for this amount of buildings is not specified.");
        }
    }

}