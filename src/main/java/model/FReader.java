package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FReader {

    private static FReader singletonInstance = null;

    private static String Filepath;

    public static FReader getInstance(String filepath){

        if(singletonInstance ==null){
            Filepath = filepath;
            singletonInstance = new FReader();
        }
        return singletonInstance;
    }

    //reads from given filepath

    private String[] fileReader(String filePath){

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

        String newFilepath = filePath+fileName;
        String[] stringList = fileReader(newFilepath);
        String[][] finalStringList = new String[stringList.length][15];

        for (int i = 0; i < finalStringList.length; i++) {
            finalStringList[i] = stringList[i].split(":");
        }
        return finalStringList;
    }

    //Uses reader method to build a stringArray from a given filepath+filename

    private String[] oneDStringArray(String filePath, String fileName){
        String newFilepath = filePath+fileName;
        String[] fields = fileReader(newFilepath);
        String[] finalFields = new String[fields.length];
        System.arraycopy(fields, 0, finalFields, 0, finalFields.length);

        return finalFields;
    }

    // each method uses either 1d or 2d StringBuilder to return a specific file's content in an array

    public String[][] getFieldsText(String filePath){ return twoDStringArray(filePath,"/Fields.txt"); }

    public String[][] getChanceCards(String filePath){ return twoDStringArray(filePath,"/ChanceCards.txt"); }

    public String[][] getMenuText(String filePath){return twoDStringArray(filePath,"/Menu.txt"); }

    public String[] getFieldMessages(String filePath){return oneDStringArray(filePath,"/FieldMessages.txt"); }

    public String[] getFieldDescriptions(String filePath){ return oneDStringArray(filePath, "/FieldDescriptions.txt"); }

    public String[] getDirectoriesStringArray(){ return fileReader("TextFiles/"); }

}