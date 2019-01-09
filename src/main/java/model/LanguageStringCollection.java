package model;

public class LanguageStringCollection {

    private static LanguageStringCollection singletonInstance = null;

    public static LanguageStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            filepath = FilePath;
            singletonInstance = new LanguageStringCollection();

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
