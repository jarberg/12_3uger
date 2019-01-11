package model.text;

public class StringCollection {

    FileReader freader = FileReader.getInstance(filepath);
    static String filepath;

    private String[][] ChanceCard = freader.getChanceCards(filepath);
    private String[][] fields = freader.getChanceCards(filepath);

    public StringCollection(){

    }

    public String getChancecard(int row, int column){
        return ChanceCard[row][column];
    }

    public String getField(int row, int column){   return fields[row][column];  }

    public int[][] getFieldsText() {
        int[][] fields = freader.getFieldsText(filepath);
        return fields;
    }
}
