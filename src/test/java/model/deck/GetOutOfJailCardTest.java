package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetOutOfJailCardTest {

    GetOutOfJailCard getOutOfJailCard;


    @Before
    public void setUp(){
        getOutOfJailCard = new GetOutOfJailCard("test",100);
    }

    @After
    public void tearDown(){
        getOutOfJailCard = null;
    }

    @Test
    public void shouldReturnPrice() {
        assertEquals(getOutOfJailCard.getPrice(),100);
    }
}