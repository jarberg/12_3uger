package model.deck;

public abstract class Card implements Drawable {

    private String description;

    public Card(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
