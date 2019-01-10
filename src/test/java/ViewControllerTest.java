import gui_fields.GUI_Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewControllerTest {

    private ViewController viewController = ViewController.getSingleInstance();

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void addPlayer() {
        for (int i = 1; i <= 6; i++) {
            viewController.addPlayer("name "+i, 1500);
            assertEquals(viewController.getGui_players().length, i);
        }

        /*
        for (int i = 0; i < gui_players.length; i++) {
            gui_players[i] = new GUI_Player("name "+i, i);
        }

        singleInstance.addPlayer("name 4", 4);
        assertEquals(4, gui_players.length);
        */
    }
}