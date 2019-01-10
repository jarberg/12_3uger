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
    public void shouldAddPlayerToGUI_Players() {
        for (int i = 1; i <= 6; i++) {
            viewController.addPlayer("name "+i, 1500);
            assertEquals(viewController.getGUI_Players().length, i);
        }
    }
}