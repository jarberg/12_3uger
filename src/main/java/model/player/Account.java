package model.player;

public class Account {

    private int balance = 1500;

    public Account(){

    }


    public void addToBalance(int amount){

        this.balance += amount;
    }

    public int getBalance(){ return this.balance; }

}
