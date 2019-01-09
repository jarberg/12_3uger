package model;

public class LanguageStringCollection {

    FReader freader = FReader.getInstance();
    String filepath="";

    private static LanguageStringCollection singletonInstance = null;
    private String[][] ChanceCard = freader.getChanceCards(filepath);

    public LanguageStringCollection(String filepath){
        this.filepath = filepath;
    }

    public static LanguageStringCollection getInstance(String filepath){
        if(singletonInstance ==null){
            singletonInstance = new LanguageStringCollection(filepath);
        }
        return singletonInstance;
    }

    public String Chancecard(int a, int b){
        return ChanceCard[a][b];
    }
}
