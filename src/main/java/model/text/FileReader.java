package model.text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private static FileReader singletonInstance = null;

    private static String Filepath;

    public static FileReader getInstance(String filepath){

        if(singletonInstance ==null){
            Filepath = filepath;
            singletonInstance = new FileReader();
        }
        return singletonInstance;

    }

    public FileReader(){


    }

    //reads from given filepath

    private String[] read1DFromFile(String filePath){

        //TODO:convert to array

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(resourceAsStream);
        List<String> stringList = new ArrayList<>();

        while (scanner.hasNext()) {
            stringList.add(scanner.nextLine());
        }
        return stringList.toArray(new String[0]);
    }

    //Uses reader method to build a 2d stringArray from a given filepath+filename

    private String[][] twoDStringArray(String filePath, String fileName){

        //TODO: split at ~ instead of :

        String newFilepath = filePath+fileName;
        String[] stringArray = read1DFromFile(newFilepath);
        String[][] finalStringArray = new String[stringArray.length][];

        for (int i = 0; i < finalStringArray.length; i++) {
            finalStringArray[i] = stringArray[i].split(":");
        }
        return finalStringArray;
    }

    private int[][] twoDIntArray(String filePath, String fileName){

        String newFilepath = filePath+fileName;
        String[] stringArray = read1DFromFile(newFilepath);
        int[][] finalStringArray = new int[stringArray.length][];

        for (int i = 0; i < finalStringArray.length; i++) {
            String[] temp1 = stringArray[i].split(":");
            int[] temp2 = new int[temp1.length];
            for (int j = 0; j < temp1.length; j++) {
                temp2[j] = Integer.parseInt(temp1[j]);
            }
            finalStringArray[i] = temp2;
        }
        return finalStringArray;
    }

    //Uses reader method to build a stringArray from a given filepath+filename

    private String[] oneDStringArray(String filePath, String fileName){
        String newFilepath = filePath+fileName;
        String[] fields = read1DFromFile(newFilepath);
        //String[] finalFields = new String[fields.length];
        //System.arraycopy(fields, 0, finalFields, 0, finalFields.length);

        return fields;
    }

    // each method uses either 1d or 2d StringBuilder to return a specific file's content in an array

    //TODO: make filename final static variables

    public int[][] getFieldsText(String filePath){ return twoDIntArray(filePath,"/Fields.txt"); }

    public String[][] getChanceCards(String filePath){ return twoDStringArray(filePath,"/ChanceCards.txt"); }

    public String[][] getMenuText(String filePath){return twoDStringArray(filePath,"/Menu.txt"); }

    public String[] getFieldMessages(String filePath){return oneDStringArray(filePath,"/FieldMessages.txt"); }

    public String[] getFieldDescriptions(String filePath){ return oneDStringArray(filePath, "/FieldDescriptions.txt"); }

    public String[] getDirectoriesStringArray(){ return read1DFromFile("TextFiles/"); }

    public String[][] getFieldInfo(String filepath) {
        return twoDStringArray(filepath,"/Fields.txt");
    }

    public int[][] getFieldsInt(String filepath) {
        return twoDIntArray(filepath,"/Fields.txt");
    }
}