package model.board;

import model.board.PropertyField;
import model.board.RentNotSpecifiedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PropertyFieldTest {
/*
    private static final String ID = "Property";
    private static final String subtitle = "Real Estate Property";
    private static final String description = "A magnificent property.";
    private static final String message = "You've seen the pinnacle of property existence";
    private static final String type = "Mansion";
    private static final Color fillColor = Color.blue;
    private static final int price = 100;
    private static final int buildingPrice = 50;
    private static final int[] rents = {1,2,3,4,5,6};

    private PropertyField field;

    @Before
    public void setUp(){
        field = new PropertyField(ID, fillColor, type, price, buildingPrice,rents);
    }

    @After
    public void tearDown(){
        field = null;
    }

    @Test
    public void shouldHaveAppropriateRentAtCreation(){
        try {
            assertEquals(rents[0], field.getRent());
        } catch (RentNotSpecifiedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldIncreaseRentWithMoreHouses(){
        int increase = 3;
        for (int i = 0; i < increase; i++) {
            field.addBuilding();
        }

        try {
            assertEquals(rents[increase], field.getRent());
        } catch (RentNotSpecifiedException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = RentNotSpecifiedException.class)
    public void shouldThrowRentNotSpecifiedException() throws RentNotSpecifiedException {
        for (int i = 0; i < 100; i++) {
            field.addBuilding();
        }
        field.getRent();
    }
*/
}