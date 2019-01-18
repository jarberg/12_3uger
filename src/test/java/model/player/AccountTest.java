package model.player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private static final int STARTING_AMOUNT = 1500;
    private Account account;

    @Before
    public void setUp(){
        account = new Account();
    }

    @After
    public void tearDown(){
        account = null;
    }

    @Test
    public void shouldStartWithProperAmount() {
        assertEquals(account.getBalance(), STARTING_AMOUNT);
    }

    @Test
    public void shouldAddToBalance() {
        int amountAdded = 50;

        int balanceBefore = account.getBalance();
        account.addToBalance(amountAdded);

        assertEquals(balanceBefore + amountAdded, account.getBalance());
    }

    @Test
    public void shouldSubtract(){
        int amountSubtracted = -20;

        int balanceBefore = account.getBalance();
        account.addToBalance(amountSubtracted);

        assertEquals(balanceBefore + amountSubtracted,account.getBalance());
    }
}