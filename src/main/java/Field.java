import java.awt.*;

public abstract class Field implements Visitable {
    private String title;
    private String subtitle;
    private String description;
    private String message;
    private Color fillColor;
    private Color borderColor;

    public Field(String title, String subtitle, String description, String message, Color fillColor){
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.message = message;
        this.fillColor = fillColor;
        borderColor = Color.black;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }
}
