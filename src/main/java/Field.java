import java.awt.*;

public abstract class Field implements Visitable {
    private String ID;
    private Color fillColor;
    private Color borderColor;

    public Field(String ID, Color fieldColor){
        this.ID = ID;
        this.fillColor = fieldColor;
        this.borderColor = fieldColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }
}
