package controller;

import controller.ViewController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class ViewControllerTest {

    private ViewControllerInterface viewController = ViewController.getSingleInstance();

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void shouldAddPlayerToGUI_Players() {
        for (int i = 1; i <= 6; i++) {
            viewController.addPlayer("name "+i, Color.BLACK, 1500);
            assertEquals(viewController.getGUI_Players().length, i);
        }
    }
}