import java.awt.*;

public class ParkingField extends Field {

    public ParkingField(String title, String subtitle, String description, String message, Color fillColor){
        super(title, subtitle, description, message, fillColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}