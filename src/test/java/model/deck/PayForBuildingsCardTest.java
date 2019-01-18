package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PayForBuildingsCardTest {

    PayForBuildingsCard payForBuildingsCard;

    @Before
    public void setUp(){
        payForBuildingsCard = new PayForBuildingsCard("test",50,100);
    }
    @After
    public void tearDown(){
        payForBuildingsCard=null;
    }

    @Test
    public void shouldGetHotel() {
        assertEquals(payForBuildingsCard.getHouse(),50);
    }

    @Test
    public void shouldGetHouse() {
        assertEquals(payForBuildingsCard.getHotel(),100);
    }
}