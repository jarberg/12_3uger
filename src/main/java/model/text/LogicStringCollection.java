package model.text;

public class LogicStringCollection{

    private static LogicStringCollection singletonInstance = new LogicStringCollection();

    private FileReader filereader = FileReader.getSingleInstance();
    private String[][] ChanceCard;
    private int[][] fields;


    private LogicStringCollection(){

    }

    public static LogicStringCollection getSingleInstance(){
        return singletonInstance;
    }

    public int[][] getFieldsText() {
        return filereader.getFieldsInt();
    }

    public String[][] getChanceCard() {
        return filereader.getChanceCardsLogic();
    }

    public String[] getPlayerAmount(){return filereader.getPlayerAmount()[0];}
}
