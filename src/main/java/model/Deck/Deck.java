package model.Deck;

import java.util.Random;

public class Deck {
    Card[] cardArray;

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
            cardArray[i] = new TransactionToAllPlayersCard("name", desc);
            cardArray[i] = new TransactionWithBankCard("name", desc);

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