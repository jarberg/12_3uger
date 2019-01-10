import java.awt.*;

public class ChanceField extends Field {

    public ChanceField(String ID, Color fieldColor){
        super(ID, fieldColor);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
