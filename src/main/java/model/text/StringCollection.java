package model.text;

public abstract class StringCollection {

    FReader freader = FReader.getInstance(filepath);
    static String filepath;

    private String[][] ChanceCard = freader.getChanceCards(filepath);
    private String[][] fields = freader.getChanceCards(filepath);


    public String getChancecard(int a, int b){
        return ChanceCard[a][b];
    }

    public String getField(int a, int b){   return fields[a][b];  }
}
