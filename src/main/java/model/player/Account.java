package model.player;

public class Account {

    private int balance = 1500;


    public void addToBalance(int amount){

        this.balance += amount;

        if(balance < 0){
            this.balance = 0;
        }
    }

    public int getBalance(){ return this.balance; }

}
