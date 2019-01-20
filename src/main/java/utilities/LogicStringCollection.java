package utilities;

public class LogicStringCollection{

    private static LogicStringCollection singletonInstance = new LogicStringCollection();

    private FileReader filereader = FileReader.getSingleInstance();
    private String[][] ChanceCardTextHolder;
    private int[][] fieldsTexHolder;
    private String[] playerAmount;


    private LogicStringCollection(){

        ChanceCardTextHolder = filereader.getChanceCardsLogic();
        this.fieldsTexHolder = filereader.getFieldsInt();
        playerAmount = filereader.getPlayerAmount()[0];
    }

    public static LogicStringCollection getSingleInstance(){
        return singletonInstance;
    }

    public int[][] getFieldsText() {
        return this.fieldsTexHolder;
    }

    public String[][] getChanceCard() { return ChanceCardTextHolder; }

    public String[] getPlayerAmount(){return playerAmount;}
}
