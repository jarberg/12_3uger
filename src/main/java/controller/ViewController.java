package controller;

import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;
import model.board.Field;
import model.board.PropertyField;
import model.text.LanguageStringCollection;

import java.awt.*;

public class ViewController {


    private final LanguageStringCollection languageStringCollection;
    private GUI gui;
    private GUI_Field[] gui_board;
    private GUI_Player[] gui_players;

    private String[] colorChoices;

    private static ViewController singleInstance = null;
    private String languageFilepath;

    public static ViewController getSingleInstance(){
        if(singleInstance == null)
            singleInstance = new ViewController();

        return singleInstance;
    }

    private ViewController() {
        this.gui_board = new GUI_Field[40];
        this.languageStringCollection = LanguageStringCollection.getInstance("");
        this.colorChoices = new String[6];
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

    public void closeGUI(){
        this.gui.close();
    }


    public void createBoard() {

    }


    public void addPlayer(String name, int balance) {

    }

    public void showGUI(){
        if(this.gui != null)
            gui.close();

        this.gui = new GUI(gui_board);
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

    public void changePlayerBalance(String playerName, int amount){
        GUI_Player player = getPlayerByName(playerName);
        int currentBalance = player.getBalance();
        player.setBalance(currentBalance + amount);
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
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        else{
            if(amount>0){
            for (int i = 0; i > amount; i++) {
                if(amount>0) {
                    gui_board[position].setCar(movingPlayer, false);
                    position--;
                    position = position % gui_board.length;
                    gui_board[position].setCar(movingPlayer, true);
                }
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                for (int i = 0; i >= amount; i--) {
                    gui_board[position].setCar(movingPlayer, false);
                    position--;
                    position = (position + 40)%40;
                    System.out.println(position);
                    gui_board[position].setCar(movingPlayer, true);
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public GUI_Player getPlayerByName(String playerName){
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

    public String getUserLanguage() {
        // default language is english otherwise change the string argument below :))))
        String[] languageChoices = languageStringCollection.getDirectories();
        String userChoice = gui.getUserSelection("Choose a language", languageChoices);
        languageFilepath = userChoice;
        return userChoice;
    }

    public void setFilepath(String languageFilepath) {
        this.languageFilepath = languageFilepath;
    }

    public int getPLayerAmount() {
        String[] playerOptions = {"3", "4", "5", "6"};
        String message = languageStringCollection.getMenu()[0];
        String userChoise = gui.getUserSelection(message, playerOptions);
        return Integer.parseInt(userChoise);
    }

    public String getPlayerName() {
        String message = languageStringCollection.getMenu()[1];
        String name = gui.getUserString(message);
        return name;
    }

    public int getPlayerAge() {
        String message = languageStringCollection.getMenu()[2];
        int age = gui.getUserInteger(message);
        return age;
    }

    public void setUpColors(){
        for (int i = 0; i < colorChoices.length; i++) {
            this.colorChoices[i] = languageStringCollection.getMenu()[i+5];
        }
    }

    public Color getUserColor(String name) {
        if (colorChoices[0] == null)
            setUpColors();

        String message = languageStringCollection.getMenu()[4] + " " + name;
        String colorString = gui.getUserSelection(message, colorChoices);
        Color colorChosen = Color.BLACK;
        int number =0;

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
        String[] updatedColorChoices = new String[colorChoices.length-1];
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

    public void showFieldMessage(String name, String fieldMessasge) {
        gui.showMessage(name + " " + fieldMessasge);
    }

    public void addBuilding(PropertyField field){
        if(field.getBuildingCount()==5){

            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHotel(true);
        }
        else{
            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHouses(field.getBuildingCount());
        }
    }

    public GUI_Player getGui_playerByName(String name){
        GUI_Player player = null;
        for (GUI_Player p:getGUI_Players()) {
            if(p.getName()==name){
                player = p;
            }

        }
        return player;
    }

    public String getUserSelection(String message, String[]choiceOptions) {

        return gui.getUserSelection(message, choiceOptions);
    }

    public void teleportPlayer(String name, int oldPosition, int newPosition) {
        GUI_Player player = getGui_playerByName(name);

        gui_board[oldPosition].setCar(player,false);
        gui_board[newPosition].setCar(player,true);
    }
}
