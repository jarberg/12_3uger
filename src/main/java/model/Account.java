package model;

public class Account {

    private int balance=0;


    public void setBalance(int amount){

        if(balance+amount >= 0){
            this.balance += amount;
        }
        if(balance >0){
            this.balance = 0;
        }
     }

    public int getBalance(){ return this.balance; }



}
