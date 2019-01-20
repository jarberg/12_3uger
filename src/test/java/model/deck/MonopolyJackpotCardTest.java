package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonopolyJackpotCardTest {


    MonopolyJackpotCard monopolyJackpotCard;

    @Before
    public void setUp(){
        monopolyJackpotCard = new MonopolyJackpotCard("test",100,1000);
    }

    @After
    public void tearDown(){
        monopolyJackpotCard = null;
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(monopolyJackpotCard.getAmount(),100);
    }

    @Test
    public void shouldGetJackpot() {
        assertEquals(monopolyJackpotCard.getJackpot(),1000);
    }
}