package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class DeckTest {

    private Deck deck;

    @Before
    public void setup(){


        String[][] testData = {
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"8", "Pay 20 kr", "20", "0", "0"},
                {"6", "Go to jail, no get 200", "0", "0", "0"},
                {"3", "Move 3 back", "-3", "0", "0"}
        };
        String[][] testDataText = {
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"},
                {"3", "Move"},
                {"6", "Go t"},
                {"8", "Pay "},
                {"6", "Go t"},
                {"3", "Move"}
        };

        deck = new Deck(testData, testDataText);
    }

    @After
    public void tearDown(){
        deck = null;
    }

    @Test
    public void shouldShuffleDeck() {

        int cardArrayLength = 0;
        int theSame = 0;
        int notTheSame = 0;
        int testAmount = 10000;

        for (int k = 0; k < testAmount; k++) {

            deck.shuffleDeck();

            Card[] cardArray = deck.getCardArray();

            Card[] copy1 = new Card[cardArray.length];

            cardArrayLength = cardArray.length;

            for (int i = 0; i < cardArray.length; i++) {
                copy1[i] = cardArray[i];
            }

            deck.shuffleDeck();

            Card[] copy2 = new Card[cardArray.length];

            for (int i = 0; i < cardArray.length; i++) {
                copy2[i] = cardArray[i];
            }


            for (int j = 0; j < copy1.length; j++) {
                if (copy1[j] == copy2[j]) {
                    theSame++;
                } else {
                    notTheSame++;
                }

            }
        }

        double averageNotTheSameCount = testAmount*cardArrayLength*(cardArrayLength-0.9)/cardArrayLength;
        double averageTheSameCount = testAmount*cardArrayLength*0.9/cardArrayLength;
        /*
        System.out.println("The same count: "+theSame);
        System.out.println("Average the same count: "+averageTheSameCount);
        */
        System.out.println("Not the same count: "+notTheSame);
        System.out.println("Average not the same count: "+averageNotTheSameCount);

        int testAcceptance = 4000;
        System.out.println("Accepted diversion: +-"+testAmount*cardArrayLength/testAcceptance);
        System.out.println("Diversion: "+(averageNotTheSameCount-notTheSame));
        assertTrue(theSame <= averageTheSameCount+(testAmount*cardArrayLength/testAcceptance) && theSame >= averageTheSameCount-(testAmount*cardArrayLength/testAcceptance));
    }

    @Test
    public void shouldPutTopCardToBottom(){

        Card[] cardArray = deck.getCardArray();

        Card topCard = cardArray[0];
        deck.putTopCardToBottom();


        assertSame(topCard,cardArray[cardArray.length-1]);

    }

    @Test
    public void topCardShouldBeSame(){
        Card[] cardArray = deck.getCardArray();

        Card topCard = cardArray[0];

        assertSame(topCard,cardArray[0]);
    }

    @Test
    public void shouldMakeDeck(){

    }
}

