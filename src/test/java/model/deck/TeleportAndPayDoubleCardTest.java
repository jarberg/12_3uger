package model.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeleportAndPayDoubleCardTest {

    TeleportAndPayDoubleCard teleportAndPayDoubleCard;

    @Before
    public void setUp(){
        teleportAndPayDoubleCard=new TeleportAndPayDoubleCard("test",2,25,50,100,200);
    }

    @After
    public void tearDown(){
        teleportAndPayDoubleCard=null;
    }

    @Test
    public void shouldGetMultiplier() {
        assertEquals(teleportAndPayDoubleCard.getMultiplier(),2);
    }
}