package model.text;

public class LogicStringCollection extends StringCollection{

    private static LogicStringCollection singletonInstance = null;

    public static LogicStringCollection getInstance(String FilePath){
        if(singletonInstance ==null){
            filepath = FilePath;
            singletonInstance = new LogicStringCollection(filepath);

        }

        return singletonInstance;
    }

    public LogicStringCollection(String FilePath){

        super();

    }
    public int[][] getFieldsText() {
        int[][] fields = freader.getFieldsText(filepath);
        return fields;
    }

}
