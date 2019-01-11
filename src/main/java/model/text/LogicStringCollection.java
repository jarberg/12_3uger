package model.text;

public class LogicStringCollection{

    private static LogicStringCollection singletonInstance = null;

    private FReader freader = FReader.getInstance(filepath);
    private String[][] ChanceCard;
    private int[][] fields;


    public static LogicStringCollection getInstance(){



        if(singletonInstance ==null){

            singletonInstance = new LogicStringCollection();

        }

        return singletonInstance;
    }

    private static String filepath = "logic";




    public LogicStringCollection(){

        super();

    }

    public int[][] getFieldsText() {
        this.fields = this.freader.getFieldsInt(filepath);
        return fields;
    }


    public String[][] getChanceCard() {
        this.ChanceCard = this.freader.getChanceCards(filepath);
        return ChanceCard;
    }
}
