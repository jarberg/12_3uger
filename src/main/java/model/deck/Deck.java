package model.deck;

import java.util.Random;

public class Deck {
    private Card[] cardArray;

    public Deck(int length) {
        cardArray = new Card[length];
    }

    public void makeDeck(String[][] textFileData) {
        for (int i = 0; i < cardArray.length; i++) {
            String desc = textFileData[i][1];
            cardArray[i] = new GetOutOfJailCard("name", desc);
            cardArray[i] = new MonoplyJackpotCard("name", desc);
            cardArray[i] = new MoveCard("name", desc);
            cardArray[i] = new PayForBuildingsCard("name", desc);
            cardArray[i] = new TeleportAndPayDoubleCard("name", desc);
            cardArray[i] = new TeleportCard("name", desc);
            cardArray[i] = new BirthdayCard("name", desc);
            cardArray[i] = new MoneyCard("name", desc);

        }
    }

    public void shuffleDeck(Card[] array) {

        Random random = new Random(); //generator random number

        for (int i = 0; i < array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            Card temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
    }
}