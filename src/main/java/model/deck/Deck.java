package model.deck;

import java.util.Random;

public class Deck {
    private Card[] cardArray;

    public Deck(String[][] deckLogic, String[][] deckText) {
        cardArray = new Card[deckLogic.length];
        makeDeck(deckLogic, deckText);
        shuffleDeck();
    }

    private void makeDeck(String[][] deckLogic, String[][] deckText) {
        for (int i = 0; i < cardArray.length; i++) {
            String desc = deckText[i][1];
            String info1 = deckLogic[i][2];
            String info2 = deckLogic[i][3];
            int info1int = Integer.parseInt(info1);
            int info2int = Integer.parseInt(info2);

            switch (deckLogic[i][0]){
                case "1":
                    cardArray[i] = new GetOutOfJailCard(desc, info1int);
                    break;
                case "2":
                    cardArray[i] = new MonopolyJackpotCard(desc,info1int,info2int);
                    break;
                case "3":
                    cardArray[i] = new MoveToFieldCard(desc,info1int);
                    break;
                case "4":
                    cardArray[i] = new PayForBuildingsCard(desc,info1int,info2int);
                    break;
                case "5":
                    cardArray[i] = new TeleportAndPayDoubleCard(desc,info1int);
                    break;
                case "6":
                    cardArray[i] = new GoToJail(desc,info1int);
                    break;
                case "7":
                    cardArray[i] = new BirthdayCard(desc,info1int);
                    break;
                case "8":
                    cardArray[i] = new MoneyCard(desc,info1int);
                    break;
                case "9":
                    cardArray[i] = new MoveAmountCard(desc,info1int);
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