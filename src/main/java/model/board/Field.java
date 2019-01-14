package model.board;

import java.awt.*;

public abstract class Field implements Visitable {

    private String ID;
    private String title;
    private String subtitle;
    private String message;
    private Color fillColor;
    private Color borderColor;

    public Field(String ID, String title, String subtitle, String message, Color fieldColor){
        this.ID = ID;
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;
        this.fillColor = fieldColor;
        this.borderColor = fieldColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getMessage() {
        return message;
    }
}
