package model.player;

import java.awt.*;

public class Player {

    private Account account;

    private String name;
    private Color playerColor;
    private int position = 0;
    private int lastPosition;
    private boolean broke;
    private boolean hasGetOutOfJailCard;
    private boolean doubleTurn;
    private boolean inJail = false;
    private boolean hasPassedStart = false;
    private int doubleThrowNum =0;
    private int jailTurn=0;
    private int currentTurn;

    public Player(String name){

        account = new Account();
        broke = false;
        hasGetOutOfJailCard = false;
        doubleTurn = false;
        this.name = name;

    }


    public String getName(){ return this.name;    }

    public boolean getJailCardStatus(){   return this.hasGetOutOfJailCard; }

    public int getBalance(){ return this.account.getBalance(); }

    public Color getPlayerColor(){ return this.playerColor;}

    public int getPosition(){ return this.position; }

    public boolean getBrokeStatus(){ return this.broke; }

    public boolean getDoubleTurnStatus(){ return this.doubleTurn; }

    public int getLastPosition(){
        return lastPosition;
    }

    public boolean getPassedStartStatus(){
        return hasPassedStart;
    }


    public void setJailCardStatus(boolean status){   this.hasGetOutOfJailCard = status; }

    public void addToBalance(int amount){ this.account.addToBalance(amount); }

    public void setPlayerColor(Color setColor){ this.playerColor = setColor;}

    public void setPositionWithStartMoney(int setPosition){
        int beforePosition = position;
        this.lastPosition = this.position;
        this.position = setPosition;
        if(position < beforePosition){
            hasPassedStart = true;
        }
    }

    public void setPositionWithoutStartMoney(int setPosition){
        this.lastPosition = this.position;
        this.position = setPosition;
    }

    public void setPassedStartStatus(boolean status){
        hasPassedStart = status;
    }

    public void setBrokeStatus(boolean status){ this.broke = status; }

    public void setDoubleTurnStatus(boolean status){
        this.doubleTurn = status;

    }
    public void addDoubleThrowTimes(){
            this.doubleThrowNum++;
    }
    public void resetDoubleThrowTimes(){
        if(!inJail){
            this.doubleThrowNum =0;
        }

    }

    public boolean isInJail() {

        return inJail;
    }

    public void setInJail(boolean inJail) {
        if(inJail){
            this.inJail = inJail;
            setJailTurn();
        }
        else{
            this.inJail = inJail;
            this.doubleThrowNum =0;
        }
    }

    public int getDoubleThrowNum(){
        return this.doubleThrowNum;
    }

    public int getJailTurn() {
        return jailTurn;
    }

    public void setJailTurn() {
        this.jailTurn = currentTurn;
    }


    public void addCurrentTurn() {
        this.currentTurn++;
    }

    public int getCurrentTurn() {
        return this.currentTurn;
    }
}
