import java.awt.*;

public class StartField extends Field{

    private int amount;

    public StartField(String title, String subtitle, String description, String message, Color fillColor, int amount){
        super(title, subtitle, description, message, fillColor);
        this.amount = amount;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getAmount(){
        return amount;
    }

}