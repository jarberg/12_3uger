package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveAmountCardTest {

    MoveAmountCard moveAmountCard;

    @Before
    public void setUp(){
        moveAmountCard = new MoveAmountCard("test",3);
    }

    @After
    public void tearDown(){
        moveAmountCard=null;
    }

    @Test
    public void shouldGetAmount() {
        assertEquals(moveAmountCard.getAmount(),3);
    }
}