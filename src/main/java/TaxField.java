import java.awt.*;

public class TaxField extends Field{

    private int flatAmount;
    private int percentage;

    public TaxField(String title, String subtitle, String description, String message, Color fillColor, int flatAmount, int percentage){
        super(title, subtitle, description, message, fillColor);

        this.flatAmount = flatAmount;
        this.percentage = percentage;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getFlatAmount(){
        return flatAmount;
    }

    public int getPercentage(){
        return percentage;
    }

}