package model.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.*;

public class DeckTest {

    private Deck deck;

    @Before
    public void setup(){
        deck = new Deck(1);
    }

    @After
    public void tearDown(){
        deck = null;
    }

    @Test
    public void shouldShuffleDeck() {

        Card[] cards = {
                new TeleportCard("Title","Description"),
                new PayForBuildingsCard("Title","Description"),
                new TeleportAndPayDoubleCard("Title","Description")
        };

        Card theCard = cards[0];

        /*
        for (int i = 0; i < cards.length ; i++) {
            Card ca

        }*/

        deck.shuffleDeck(cards);




        /*
        int testposition = 1;
        int decksize = 32;

        for (int i = 0; i < decksize; i++) {
            Random random = null;
            assertNotSame(testposition,random.nextInt(decksize));
        }
        */
    }
}
