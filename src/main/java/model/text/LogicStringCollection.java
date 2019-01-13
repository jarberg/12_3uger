package model.text;

public class LogicStringCollection{

    private static LogicStringCollection singletonInstance = null;

    private FileReader filereader = FileReader.getInstance(filepath);
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
        this.fields = this.filereader.getFieldsInt(filepath);
        return fields;
    }


    public String[][] getChanceCard() {
        this.ChanceCard = this.filereader.getChanceCards(filepath);
        return ChanceCard;
    }
}
