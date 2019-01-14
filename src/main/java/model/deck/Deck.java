package model.deck;

import java.util.Random;

public class Deck {
    private Card[] cardArray;

    public Deck(String[][] deckData) {
        cardArray = new Card[deckData.length];
        makeDeck(deckData);
        shuffleDeck();
    }

    private void makeDeck(String[][] deckData) {
        for (int i = 0; i < cardArray.length; i++) {
            String desc = deckData[i][4];
            String info1 = deckData[i][2];
            String info2 = deckData[i][3];
            int info1int = Integer.parseInt(info1);
            int info2int = Integer.parseInt(info2);

            switch (deckData[i][0]){
                case "1":
                    cardArray[i] = new GetOutOfJailCard(desc);
                    break;
                case "2":
                    cardArray[i] = new MonopolyJackpotCard(desc,info1int,info2int);
                    break;
                case "3":
                    cardArray[i] = new MoveCard(desc,info1int);
                    break;
                case "4":
                    cardArray[i] = new PayForBuildingsCard(desc,info1int,info2int);
                    break;
                case "5":
                    cardArray[i] = new TeleportAndPayDoubleCard(desc,info1int);
                    break;
                case "6":
                    cardArray[i] = new TeleportCard(desc,info1int);
                    break;
                case "7":
                    cardArray[i] = new BirthdayCard(desc, info1int);
                    break;
                case "8":
                    cardArray[i] = new MoneyCard(desc,info1int);
                    break;

            }
        }
    }

    public void shuffleDeck() {

        Random random = new Random(); //generator random number

        for (int i = 0; i < cardArray.length; i++) {
            int randomPosition = random.nextInt(cardArray.length);
            Card temp = cardArray[i];
            cardArray[i] = cardArray[randomPosition];
            cardArray[randomPosition] = temp;
        }
    }

    public Card[] getCardArray() {
        return cardArray;
    }

    public Card getTopCard(){
        return cardArray[0];
    }

    public void putTopCardToBottom(){

        int cardArrayLength = cardArray.length;

        Card[] temporaryArray = new Card[cardArrayLength];

        for (int i = 0; i < cardArrayLength-1; i++) {
            temporaryArray[i] = cardArray[(i+1)];

        }

        temporaryArray[cardArrayLength-1] = cardArray[0];

        for (int j = 0; j < cardArrayLength; j++) {
            cardArray[j] = temporaryArray[j];

        }

    }

}