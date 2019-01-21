package model.board;

import java.awt.*;

public abstract class Field implements Visitable {

    private Color fillColor;
    private String subtitle;
    private String message;
    private String title;
    private String ID;

    public Field(String ID, String title, String subtitle, String message, Color fieldColor){

        this.ID = ID;
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;
        this.fillColor = fieldColor;

    }

    public Color getFillColor() {
        return fillColor;
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
