package model.text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private static final String LANGUAGE_DIRECTORY_PATH = "language/";
    private static final String LOGIC_DIRECTORY_PATH = "logic/";
    private static final String CHANCECARD_FILENAME = "/ChanceCards.txt";
    private static final String MENU_FILENAME = "/Menu.txt";
    private static final String FIELDS_FILENAME ="/Fields.txt";
    private static final String EMPTY_STRING = "";

    private static FileReader singletonInstance = new FileReader();

    private static final String DEFAULT_LANGUAGE = "english";
    private static String language = DEFAULT_LANGUAGE;

    public static FileReader getSingleInstance(){
        return singletonInstance;
    }

    private FileReader(){
    }

    public static void setLanguage(String newLanguage){
        language = newLanguage;
    }
    //reads from given filepath

    private String[] read1DFromFile(String filePath, String directory){

        //TODO:convert to array

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(directory + filePath);
        Scanner scanner = new Scanner(resourceAsStream);
        List<String> stringList = new ArrayList<>();

        while (scanner.hasNext()) {
            stringList.add(scanner.nextLine());
        }
        return stringList.toArray(new String[0]);
    }

    //Uses reader method to build a 2d stringArray from a given filepath+filename

    private String[][] twoDStringArray(String fileName, String directory){

        //TODO: split at ~ instead of :

        String newFilepath = language+fileName;
        String[] stringArray = read1DFromFile(newFilepath, directory);
        String[][] finalStringArray = new String[stringArray.length][];

        for (int i = 0; i < finalStringArray.length; i++) {
            finalStringArray[i] = stringArray[i].split("~");
        }
        return finalStringArray;
    }

    private int[][] twoDIntArray(String fileName, String directory){

        String[] stringArray = read1DFromFile(language+fileName, directory);
        int[][] finalStringArray = new int[stringArray.length][];

        for (int i = 0; i < finalStringArray.length; i++) {
            String[] temp1 = stringArray[i].split("~");
            int[] temp2 = new int[temp1.length];
            for (int j = 0; j < temp1.length; j++) {
                temp2[j] = Integer.parseInt(temp1[j]);
            }
            finalStringArray[i] = temp2;
        }
        return finalStringArray;
    }

    //Uses reader method to build a stringArray from a given filepath+filename

    private String[] oneDStringArray(String fileName, String directory){
        String newFilepath = language+fileName;
        //String[] finalFields = new String[fields.length];
        //System.arraycopy(fields, 0, finalFields, 0, finalFields.length);
        return read1DFromFile(newFilepath, directory);
    }

    // each method uses either 1d or 2d StringBuilder to return a specific file's content in an array

    //TODO: make filename final static variables

    public String[][] getChanceCards(){ return twoDStringArray(CHANCECARD_FILENAME, LOGIC_DIRECTORY_PATH); }

    public String[] getMenuText(){return oneDStringArray(MENU_FILENAME, LANGUAGE_DIRECTORY_PATH); }

    public String[] getDirectoriesStringArray(){ return read1DFromFile(EMPTY_STRING, LANGUAGE_DIRECTORY_PATH);}

    public String[][] getFieldInfo() {
        return twoDStringArray(FIELDS_FILENAME, LANGUAGE_DIRECTORY_PATH);
    }

    public int[][] getFieldsInt() {
        return twoDIntArray(FIELDS_FILENAME, LOGIC_DIRECTORY_PATH);
    }
}