package model.text;

public class LanguageStringCollection {

    private static LanguageStringCollection singletonInstance = null;


    private FReader freader = new FReader();
    private String[][] ChanceCard;
    private String[][] fields;

    private LanguageStringCollection(){


    }

    public static LanguageStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            filepath = filepath+FilePath;
            singletonInstance = new LanguageStringCollection(filepath);

        }

        return singletonInstance;
    }


    private static String filepath = "language/";

    public LanguageStringCollection(String FilePath){

        super();

    }
    public String[][] getFieldsText() {
        this.fields = this.freader.getFieldInfo(filepath);
        return this.fields;
    }


    public String[][] getChanceCard() {
        this.ChanceCard= this.freader.getChanceCards(filepath);
        return ChanceCard;
    }
}
