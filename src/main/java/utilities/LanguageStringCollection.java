package utilities;

public class LanguageStringCollection {
    private static LanguageStringCollection singletonInstance = new LanguageStringCollection();
    private FileReader fileReader = FileReader.getSingleInstance();
    private String[][] fieldsTextHolder;
    private String[] directoriesTextHolder;
    private String[][] chanceCardTextHolder;
    private String[] menuTextHolder;

    private LanguageStringCollection(){
        directoriesTextHolder =fileReader.getDirectoriesStringArray();
    }

    public static LanguageStringCollection getSingleInstance(){
        return singletonInstance;
    }

    public String[][] getFieldsText() {
        return fieldsTextHolder;
    }

    public String[] getDirectories() { return directoriesTextHolder;    }

    public String[][] getChanceCard() {
        return chanceCardTextHolder;
    }

    public String[] getMenu(){ return menuTextHolder; }

    public void getTextFromFileReader(){
        fieldsTextHolder =fileReader.getFieldInfo();
        chanceCardTextHolder =fileReader.getChanceCardsText();
        menuTextHolder =fileReader.getMenuText();
    }
}