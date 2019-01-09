package model;

public class LogicStringCollection {

    private static LogicStringCollection singletonInstance = null;

    public static LogicStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            filepath = FilePath;
            singletonInstance = new LogicStringCollection();

        }

        return singletonInstance;
    }

    FReader freader = FReader.getInstance(filepath);
    static String filepath;

    private String[][] ChanceCard = freader.getChanceCards(filepath);

    public String getChancecard(int a, int b){
        return ChanceCard[a][b];
    }
}
