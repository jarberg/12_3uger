package model;

import java.awt.*;

public class Player {

    Account account = new Account();

    private String name="";
    private Color playerColor;
    private int position;
    private boolean broke=false;
    private boolean hasGetOutOfJailCard=false;

    //constructor
    public Player(String name){
        this.name = name;
    }


    //getters

    public String getName(){ return this.name;    }

    public boolean getJailCardStatus(){   return this.hasGetOutOfJailCard; }

    public int getBalance(){ return this.account.getBalance(); }

    public Color getPlayerColor(){ return this.playerColor}

    public int getPosition(){ return this.position; }

    public boolean getBrokeStatus(){ return this.broke; }

    //Setters

    public void setName(){ this.name = name;    }

    public void setJailCardStatus(boolean status){   this.hasGetOutOfJailCard = status; }

    public void addBalance(int amount){ this.account.addBalance(amount); }

    public void setPlayerColor(Color setColor){ this.playerColor = setColor;}

    public void setPosition(int setPosition){ this.position = setPosition; }

    public void setBrokeStatus(boolean status){ this.broke = status; }
}
