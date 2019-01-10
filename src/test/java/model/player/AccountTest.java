package model.player;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    Account account = new Account();


    @Test
    public void getBalance() {
        //check if account starts with 0 balance
        assertEquals(account.getBalance(),0);

    }

    @Test
    public void addBalance() {
        //check if adding to balance works
        int newBalance = 5;

        account.addBalance(newBalance);
        assertEquals(newBalance,account.getBalance());

        account.addBalance(-20);
        assertEquals(0,account.getBalance());

    }


}