import java.awt.*;

public class StartField extends Field{

    public StartField(String title, String subtitle, String description, String message, Color fillColor){
        super(title, subtitle, description, message, fillColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}