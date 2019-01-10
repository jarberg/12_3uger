package model.player;

public class Account {

    private int balance=0;


    public void addBalance(int amount){

        this.balance += amount;

        if(balance <0){
            this.balance = 0;
        }
     }

    public int getBalance(){ return this.balance; }

}
