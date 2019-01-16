package model.text;

public class LanguageStringCollection {

    private static LanguageStringCollection singletonInstance = new LanguageStringCollection();

    private FileReader fileReader = FileReader.getSingleInstance();

    private LanguageStringCollection(){

    }

    public static LanguageStringCollection getSingleInstance(){
        return singletonInstance;
    }

    public String[][] getFieldsText() {
        return fileReader.getFieldInfo();
    }


    public String[] getDirectories() {
        return fileReader.getDirectoriesStringArray();
    }

    public String[][] getChanceCard() {
        return fileReader.getChanceCardsText();
    }

    public String[] getMenu(){
        return fileReader.getMenuText();
    }

}
