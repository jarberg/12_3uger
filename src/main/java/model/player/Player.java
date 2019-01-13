package model.player;

import java.awt.*;

public class Player {

    private Account account;

    private String name;
    private Color playerColor;
    private int position;
    private boolean broke;
    private boolean hasGetOutOfJailCard;
    private boolean doubleTurn;

    //constructor
    public Player(String name){

        account = new Account();
        broke = false;
        hasGetOutOfJailCard = false;
        doubleTurn = false;
        this.name = name;

    }

    public void movePosition(int amount, int boardSize){
        this.position = (position + amount) % boardSize;
    }


    //getters

    public String getName(){ return this.name;    }

    public boolean getJailCardStatus(){   return this.hasGetOutOfJailCard; }

    public int getBalance(){ return this.account.getBalance(); }

    public Color getPlayerColor(){ return this.playerColor;}

    public int getPosition(){ return this.position; }

    public boolean getBrokeStatus(){ return this.broke; }

    public boolean getDoubleTurnStatus(){ return this.doubleTurn; }


    //Setters


    public void setJailCardStatus(boolean status){   this.hasGetOutOfJailCard = status; }

    public void addToBalance(int amount){ this.account.addToBalance(amount); }

    public void setPlayerColor(Color setColor){ this.playerColor = setColor;}

    public void setPosition(int setPosition){ this.position = setPosition; }

    public void setBrokeStatus(boolean status){ this.broke = status; }

    public void setDoubleTurnStatus(boolean status){ this.doubleTurn = status; }
}
