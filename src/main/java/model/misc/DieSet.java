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
        identicalRoll = (dieOneValue == dieTwoValue);
    }

    private int rollDie(){
        return (int) (Math.random() * SIDES) + 1;
    }

    public int getDieOneValue() {
        return dieOneValue;
    }

    public int getDieTwoValue() {
        return dieTwoValue;
    }

    public boolean getIdenticalRolls(){
        return identicalRoll;
    }

}
