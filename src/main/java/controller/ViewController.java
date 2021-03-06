package controller;

import gui_fields.*;
import gui_main.GUI;
import model.board.Field;
import model.board.PropertyField;
import utilities.LanguageStringCollection;

import java.awt.*;

public class ViewController implements ViewControllerInterface {

    private static ViewControllerInterface singleInstance = new ViewController();

    private final LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private GUI_Player[] gui_players;
    private String[] colorChoices;
    private GUI_Field[] gui_board;
    private GUI gui;


    private ViewController() {
        this.gui_board = new GUI_Field[40];
        this.colorChoices = new String[6];
    }

    public static ViewControllerInterface getSingleInstance(){
        return singleInstance;
    }

    public void showMessage(String message){
        gui.showMessage(message);
    }

    public void showGameGUI(Field[] fields){
        int boardLength = fields.length;
        GUI_Street[] gui_street = new GUI_Street[boardLength];
        for (int i = 0; i < boardLength; i++) {
            Field field = fields[i];
            gui_street[i] = new GUI_Street();
            gui_street[i].setTitle(field.getTitle());
            gui_street[i].setSubText(field.getSubtitle());
            gui_street[i].setDescription(field.getMessage());
            gui_street[i].setForeGroundColor(Color.BLACK);
            gui_street[i].setBackGroundColor(field.getFillColor());
        }
        this.gui_board = gui_street;
        showGUI();
    }

    public void showEmptyGUI(){
        this.gui = new GUI(new GUI_Field[0]);
    }

    public void showGUI(){
        if(this.gui != null)
            gui.close();

        this.gui = new GUI(gui_board, new Color(234, 234, 227));
    }

    public void addPlayer(String name, Color color, int balance){

        int length;
        try{
            length = gui_players.length;
        } catch (NullPointerException e){
            length = 0;
        }

        GUI_Player[] temp = new GUI_Player[length+1];
        for (int i = 0; i < length; i++) {
            temp[i] = gui_players[i];
        }

        gui_players = temp;
        GUI_Car car = makePlayerCar(color);
        GUI_Player newPlayer = new GUI_Player(name, balance, car);

        gui_players[length] = newPlayer;

    }

    public GUI_Car makePlayerCar(Color color) {

        GUI_Car playerCar = new GUI_Car();
        if (color == Color.cyan)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.UFO, GUI_Car.Pattern.FILL);
        else if (color == Color.orange)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.TRACTOR, GUI_Car.Pattern.FILL);
        else if (color == Color.red)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.RACECAR, GUI_Car.Pattern.FILL);
        else if (color == Color.green)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
        else if (color == Color.blue)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.UFO, GUI_Car.Pattern.FILL);
        else if (color == Color.pink)
            playerCar = new GUI_Car(color, color, GUI_Car.Type.RACECAR, GUI_Car.Pattern.FILL);

        return playerCar;

    }

    public void showPlayerScores(){

        for(GUI_Player player : gui_players){
            this.gui.addPlayer(player);
        }

    }


    public void spawnPlayers(){

        if(gui_board[0] != null){
            for(GUI_Player player : gui_players){
                gui_board[0].setCar(player, true);
            }
        }

    }

    public void movePlayer(String playerName, int position, int amount){

        GUI_Player movingPlayer = getPlayerByName(playerName);
        if(amount > 0){
            for (int i = 0; i < amount; i++) {
                gui_board[position].setCar(movingPlayer, false);
                position++;
                position = position % gui_board.length;
                gui_board[position].setCar(movingPlayer, true);
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void teleportPlayer(String playerName, int oldposition, int newposition){

        GUI_Player teleportPlayer = getPlayerByName(playerName);
             gui_board[oldposition].setCar(teleportPlayer,false);
             gui_board[newposition%40].setCar(teleportPlayer, true);

    }

    private GUI_Player getPlayerByName(String playerName){

        GUI_Player player = null;
        for(GUI_Player p : gui_players){
            if(p.getName().equals(playerName))
                player = p;
        }

        return player;

    }

    public GUI_Player[] getGUI_Players() {
        return gui_players;
    }

    public String getUserLanguage(){

        String[] languageChoices = languageStringCollection.getDirectories();
        return gui.getUserSelection("Choose a language", languageChoices);

    }


    public int getPlayerAmount(String[] options){

        String message = languageStringCollection.getMenu()[0];
        String userChoise = gui.getUserSelection(message, options);

        return Integer.parseInt(userChoise);

    }

    public String getPlayerName(){

        GameController gameController = GameController.getSingleInstance();
        String playerCount = gameController.getPlayerCount();
        String message = String.format(languageStringCollection.getMenu()[1],String.valueOf(playerCount));
        String name;

        while (true){
            name = gui.getUserString(message);
            if (name.length() < 3)
                showMessage(languageStringCollection.getMenu()[42]);
            else{
                break;
            }
        }

        return name;

    }

    public void setUpColors(){
        for (int i = 0; i < colorChoices.length; i++) {
            this.colorChoices[i] = languageStringCollection.getMenu()[i + 5];
        }
    }

    public Color getUserColor(String name) {

        if (colorChoices[0] == null)
            setUpColors();

        String message = String.format(languageStringCollection.getMenu()[4],name);
        String colorString = gui.getUserSelection(message, colorChoices);
        Color colorChosen = Color.BLACK;
        int number = 0;

        for (int i = 5; i <languageStringCollection.getMenu().length ; i++) {
            if (colorString.equals(languageStringCollection.getMenu()[i]))
                number = i;
        }

        switch(number){
            case 5: colorChosen = Color.cyan;break;
            case 6: colorChosen = Color.red;break;
            case 7: colorChosen = Color.orange;break;
            case 8: colorChosen = Color.green;break;
            case 9: colorChosen = Color.blue;break;
            case 10: colorChosen = Color.pink;break;
            case 11: colorChosen = Color.BLACK;break;
        }

        this.colorChoices = removeColor(colorString, colorChoices);
        return  colorChosen;

    }

    public String[] removeColor(String colorChosen, String[] colorChoices){

        String[] updatedColorChoices = new String[colorChoices.length - 1];
        int idx = 0;

        for (String colorChoice : colorChoices) {
            if (!colorChosen.equals(colorChoice)) {
                updatedColorChoices[idx] = colorChoice;
                idx++;
            }
        }

        return updatedColorChoices;

    }

    public void showDice(int dieOneValue, int dieTwoValue) {
        gui.setDice(dieOneValue, dieTwoValue);
    }

    public void showFieldMessage(String name, String fieldMessasge){
        gui.showMessage(name + " " + fieldMessasge);
    }

    public void addBuilding(PropertyField field){

        if(field.getBuildingCount() == 5){

            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHotel(true);
        }
        else{
            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHouses(field.getBuildingCount());

        }

    }

    public GUI_Player getGui_playerByName(String name){

        GUI_Player player = null;
        for (GUI_Player p:getGUI_Players()) {
            if(p.getName().equals(name)){
                player = p;
            }

        }
        return player;

    }


    public String getUserSelection(String message, String... choiceOptions){
        return gui.getUserSelection(message, choiceOptions);
    }

    public String getUserButtonSelection(String message, String... options){
        return gui.getUserButtonPressed(message, options);
    }

    public void showOwner(String fieldName, Color playerColor){
        GUI_Field field = getGUIFieldByName(fieldName);
        ((GUI_Street) field).setBorder(playerColor);
    }

    public void pawn(String fieldName, Color playerColor, Color playerColor2){
        GUI_Field field = getGUIFieldByName(fieldName);
        ((GUI_Street) field).setBorder(playerColor, playerColor2);
    }

    public void updateFieldBuildings(String fieldName, int buildingCount){

        GUI_Street field = (GUI_Street)getGUIFieldByName(fieldName);
        field.setHotel(false);

        if(buildingCount == 5)
            field.setHotel(true);
        else
            field.setHouses(buildingCount);

    }

    public void setGUI_PlayerBalance(String playerName, int amount){

        GUI_Player player = getGui_playerByName(playerName);
        player.setBalance(amount);

    }

    private GUI_Field getGUIFieldByName(String fieldName){

        for(GUI_Field field : gui_board){
            if(field.getTitle().equals(fieldName)){
                return field;
            }
        }
        return null;

    }

    public void vanishPlayer(String name, int positon) {

        GUI_Player player = getGui_playerByName(name);
        gui_board[positon].setCar(player, false);

    }

}
