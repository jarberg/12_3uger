package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BirthdayCardTest {

    private BirthdayCard birthdayCard;

    @Before
    public void setup() {
        birthdayCard = new BirthdayCard("Test", 100);
    }

    @After
    public void tearDown(){
        birthdayCard = null;
    }

    @Test
    public void shouldReturnAmount(){
        assertEquals(birthdayCard.getAmount(),100);
    }


}
