package model.text;

public class LanguageStringCollection extends StringCollection{

    private static LanguageStringCollection singletonInstance = null;

    public static LanguageStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            filepath = FilePath;
            singletonInstance = new LanguageStringCollection(filepath);

        }

        return singletonInstance;
    }

    public LanguageStringCollection(String FilePath){
        super();
    }

}
