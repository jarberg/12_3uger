package model.misc;

import java.util.concurrent.ThreadLocalRandom;

public class DieSet {


    private int value;
    private boolean identicalRoll;
    private int dieOneValue;
    private int dieTwoValue;

    public DieSet(){
        roll();
    }

    public void roll(){
        this.dieOneValue = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        this.dieTwoValue = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        this.value = (this.dieOneValue+this.dieTwoValue);
        identicalRoll = (dieOneValue==dieTwoValue);
    }

    public int getValue(){
        return this.value;
    }

    public boolean getIdenticalRolls(){
        return (identicalRoll);
    }

}
