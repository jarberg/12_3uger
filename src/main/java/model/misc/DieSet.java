package model.misc;

public class DieSet {
    private final int SIDES;
    private int value;
    private boolean identicalRoll;
    private int dieOneValue;
    private int dieTwoValue;

    public DieSet(){
        this.SIDES = 6;
    }

    public void roll(){
        this.dieOneValue = rollDie();
        this.dieTwoValue = rollDie();
        this.value = (this.dieOneValue + this.dieTwoValue);
        identicalRoll = (getDieOneValue()  == getDieTwoValue());
    }

    private int rollDie(){
        return (int) (Math.random() * SIDES) + 1;
    }

    public int getDieOneValue() { return this.dieOneValue; }

    public int getDieTwoValue() {
        return this.dieTwoValue;
    }

    public int getValue(){
        return dieOneValue + dieTwoValue;
    }

    public boolean getIdenticalRolls(){
        return identicalRoll;
    }
}