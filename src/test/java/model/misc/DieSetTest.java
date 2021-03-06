package model.misc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieSetTest {

    private DieSet dieSet;

    @Before
    public void setUp() {
        dieSet = new DieSet();
    }

    @After
    public void tearDown() {
        dieSet = null;
    }

    @Test
    public void shouldRollBetweenOneAndTwelve() {
        dieSet.roll();
        int returnValue = dieSet.getValue();
        assertTrue(returnValue <= 12 && returnValue >= 1);
    }

    @Test
    public void shouldHaveRightDistribution(){
        int testAmount = 100000;
        int testAcceptance = 100;
        int[] count;



        for (int j = 0; j < 500; j++) {

            count = new int[13];

            for (int i = 0; i < testAmount; i++) {
                dieSet.roll();
                int value = dieSet.getValue();

                if (value == 2) {
                    count[2]++;
                }
                if (value == 3) {
                    count[3]++;
                }
                if (value == 4) {
                    count[4]++;
                }
                if (value == 5) {
                    count[5]++;
                }
                if (value == 6) {
                    count[6]++;
                }
                if (value == 7) {
                    count[7]++;
                }
                if (value == 8) {
                    count[8]++;
                }
                if (value == 9) {
                    count[9]++;
                }
                if (value == 10) {
                    count[10]++;
                }
                if (value == 11) {
                    count[11]++;
                }
                if (value == 12) {
                    count[12]++;
                }
            }


            assertTrue(count[2] <= (testAmount / 36) + (testAmount / testAcceptance) && count[2] >= (testAmount / 36) - (testAmount / testAcceptance));
            assertTrue(count[3] <= (testAmount * 2 / 36) + (testAmount / testAcceptance) && count[3] >= (testAmount * 2 / 36) - (testAmount / testAcceptance));
            assertTrue(count[4] <= (testAmount * 3 / 36) + (testAmount / testAcceptance) && count[4] >= (testAmount * 3 / 36) - (testAmount / testAcceptance));
            assertTrue(count[5] <= (testAmount * 4 / 36) + (testAmount / testAcceptance) && count[5] >= (testAmount * 4 / 36) - (testAmount / testAcceptance));
            assertTrue(count[6] <= (testAmount * 5 / 36) + (testAmount / testAcceptance) && count[6] >= (testAmount * 5 / 36) - (testAmount / testAcceptance));
            assertTrue(count[7] <= (testAmount * 6 / 36) + (testAmount / testAcceptance) && count[7] >= (testAmount * 6 / 36) - (testAmount / testAcceptance));
            assertTrue(count[8] <= (testAmount * 5 / 36) + (testAmount / testAcceptance) && count[8] >= (testAmount * 5 / 36) - (testAmount / testAcceptance));
            assertTrue(count[9] <= (testAmount * 4 / 36) + (testAmount / testAcceptance) && count[9] >= (testAmount * 4 / 36) - (testAmount / testAcceptance));
            assertTrue(count[10] <= (testAmount * 3 / 36) + (testAmount / testAcceptance) && count[10] >= (testAmount * 3 / 36) - (testAmount / testAcceptance));
            assertTrue(count[11] <= (testAmount * 2 / 36) + (testAmount / testAcceptance) && count[11] >= (testAmount * 2 / 36) - (testAmount / testAcceptance));
            assertTrue(count[12] <= (testAmount / 36) + (testAmount / testAcceptance) && count[12] >= (testAmount / 36) - (testAmount / testAcceptance));

        }
    }
}
