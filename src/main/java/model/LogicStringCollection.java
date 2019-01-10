package model;

public class LogicStringCollection {

    FReader freader = FReader.getInstance();
    String filepath="";

    private static LogicStringCollection singletonInstance = null;
    //private String[][] ChanceCard = freader.getChanceCards(filepath);


    public LogicStringCollection(String filepath){
        this.filepath = filepath;
    }

    public static LogicStringCollection getInstance(String filepath){
        if(singletonInstance ==null){
            singletonInstance = new LogicStringCollection(filepath);
        }
        return singletonInstance;
    }
    /*
    public String getChancecard(int a, int b){
        return ChanceCard[a][b];
    }*/


    public int[][] getFieldsText() {
        int[][] fields = freader.getFieldsText(filepath);
        return fields;
    }
}
