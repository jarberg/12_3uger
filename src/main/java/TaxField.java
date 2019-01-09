import java.awt.*;

public class TaxField extends Field{

    public TaxField(String title, String subtitle, String description, String message, Color fillColor){
        super(title, subtitle, description, message, fillColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}