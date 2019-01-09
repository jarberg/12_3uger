package model;

import java.util.Random;

public class Deck {
    Card[] cardArray;

    public Deck(int length) {
        cardArray = new Card[length];
    }

    public void makeDeck(String[][] textFileData) {
        for (int i = 0; i < cardArray.length; i++) {
            String description = textFileData[i][1];
            cardArray[i] = new Card();
        }
    }

    public void ShuffleDeck(Card[] array) {

        Random random = new Random(); //generator random number

        for (int i = 0; i < array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            Card temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
    }
}