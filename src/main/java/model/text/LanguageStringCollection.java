package model.text;

public class LanguageStringCollection {

    private static LanguageStringCollection singletonInstance = null;


    private FileReader freader = new FileReader();
    private String[][] ChanceCard;
    private String[][] fields;
    private String[] menu;
    private String[] directories;

    private LanguageStringCollection(){


    }

    public static LanguageStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            singletonInstance = new LanguageStringCollection(filepath);

        }
        filepath = filepath+FilePath;

        return singletonInstance;
    }


    private static String filepath = "language/";

    public LanguageStringCollection(String FilePath)
    {
        super();
    }

    public String[][] getFieldsText() {
        this.fields = this.freader.getFieldInfo(filepath);
        return this.fields;
    }


    public String[] getDirectories() {
        this.directories = this.freader.getDirectoriesStringArray();
        return this.directories;
    }





    public String[][] getChanceCard() {
        this.ChanceCard= this.freader.getChanceCards(filepath);
        return ChanceCard;
    }

    public String[] getMenu(){
        this.menu = this.freader.getMenuText(filepath);
        return menu;
    }

}
