package controller;

import controller.ViewController;
import gui_fields.GUI_Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static java.awt.Color.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ViewControllerTest {

    private ViewControllerInterface viewController;

    @Before
    public void setUp(){
        viewController = ViewController.getSingleInstance();
    }

    @After
    public void tearDown(){
        viewController = null;
    }

    @Test
    public void shouldAddPlayerToGUI_Players() {
        for (int i = 1; i <= 6; i++) {
            viewController.addPlayer("name "+i, Color.BLACK, 1500);
            assertEquals(viewController.getGUI_Players().length, i);
        }
    }

    @Test
    public void shouldAllowForSixCars(){
        GUI_Car[] cars = {
                viewController.makePlayerCar(cyan),
                viewController.makePlayerCar(orange),
                viewController.makePlayerCar(red),
                viewController.makePlayerCar(green),
                viewController.makePlayerCar(blue),
                viewController.makePlayerCar(pink)
        };
        for(GUI_Car car : cars){
            assertNotNull(car);
        }
    }
}