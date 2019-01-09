import java.awt.*;

public class BreweryField extends Field {

    private int multiplier1;
    private int multiplier2;


    public BreweryField(String title, String subtitle, String description, String message, Color fillColor, int multiplier1, int multiplier2){
        super(title, subtitle, description, message, fillColor);

        this.multiplier1 = multiplier1;
        this.multiplier2 = multiplier2;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getMultiplier1() {
        return multiplier1;
    }

    public int getMultiplier2(){
        return multiplier2;
    }

}
