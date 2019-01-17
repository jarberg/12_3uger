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

public class ViewController implements ViewControllerInterface {


    private final LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
    private GUI gui;
    private GUI_Field[] gui_board;
    private GUI_Player[] gui_players;

    private String[] colorChoices;

    private static ViewControllerInterface singleInstance = new ViewController();

    private ViewController() {
        this.gui_board = new GUI_Field[40];
        this.colorChoices = new String[6];
    }

    public static ViewControllerInterface getSingleInstance(){
        return singleInstance;
    }

    @Override
    public void showMessage(String message){
        gui.showMessage(message);
    }

    @Override
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
    @Override
    public void showEmptyGUI(){
        this.gui = new GUI(new GUI_Field[0]);
    }
    @Override
    public void closeGUI(){
        this.gui.close();
    }

    @Override
    public void createBoard() {

    }

    @Override
    public void addPlayer(String name, int balance) {

    }

    @Override
    public void showGUI(){
        if(this.gui != null)
            gui.close();

        this.gui = new GUI(gui_board);
    }


    @Override
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

    @Override
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
    @Override
    public void showPlayerScores(){
        for(GUI_Player player : gui_players){
            this.gui.addPlayer(player);
        }
    }
    @Override
    public void changePlayerBalance(String playerName, int amount){
        GUI_Player player = getPlayerByName(playerName);
        int currentBalance = player.getBalance();
        player.setBalance(currentBalance + amount);
    }
    @Override
    public void spawnPlayers(){
        if(gui_board[0] != null){
            for(GUI_Player player : gui_players){
                gui_board[0].setCar(player, true);
            }
        }
    }
    @Override
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
    }


    @Override
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
    @Override
    public GUI_Player[] getGUI_Players() {
        return gui_players;
    }

    @Override
    public String getUserLanguage() {
        // default language is english otherwise change the string argument below :))))
        String[] languageChoices = languageStringCollection.getDirectories();
        String userChoice = gui.getUserSelection("Choose a language", languageChoices);
        return userChoice;
    }

    @Override
    public int getPLayerAmount() {
        String[] playerOptions = {"3", "4", "5", "6"};
        String message = languageStringCollection.getMenu()[0];
        String userChoise = gui.getUserSelection(message, playerOptions);
        return Integer.parseInt(userChoise);
    }

    @Override
    public String getPlayerName() {
        String message = languageStringCollection.getMenu()[1];
        String name = "";
        while (true){
            name = gui.getUserString(message);
            if (name.length() < 1)
                showMessage(languageStringCollection.getMenu()[42]);
            else{
                break;
            }
        }
        return name;
    }

    @Override
    public int getPlayerAge() {
        String message = languageStringCollection.getMenu()[2];
        int age = gui.getUserInteger(message);
        return age;
    }

    @Override
    public void setUpColors(){
        for (int i = 0; i < colorChoices.length; i++) {
            this.colorChoices[i] = languageStringCollection.getMenu()[i+5];
        }
    }

    @Override
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

    @Override
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

    @Override
    public void showDice(int dieOneValue, int dieTwoValue) {
        gui.setDice(dieOneValue, dieTwoValue);
    }

    @Override
    public void showFieldMessage(String name, String fieldMessasge) {
        gui.showMessage(name + " " + fieldMessasge);
    }

    @Override
    public void addBuilding(PropertyField field){
        if(field.getBuildingCount()==5){

            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHotel(true);
        }
        else{
            ((GUI_Street)gui_board[Integer.parseInt(((Field)field).getID())]).setHouses(field.getBuildingCount());

        }


    }

    @Override
    public GUI_Player getGui_playerByName(String name){
        GUI_Player player = null;
        for (GUI_Player p:getGUI_Players()) {
            if(p.getName()==name){
                player = p;
            }

        }
        return player;
    }


    @Override
    public String getUserSelection(String message, String... choiceOptions) {

        return gui.getUserSelection(message, choiceOptions);
    }

    @Override
    public String getUserButtonSelection(String message, String... options) {
        return gui.getUserButtonPressed(message, options);
    }

    @Override
    public void showOwner(String fieldName, String name, Color playerColor) {
        GUI_Field field = getGUIFieldByName(fieldName);
        while(!field.getDescription().equals(name)){
            field.setDescription(name);
        }
        ((GUI_Street) field).setBorder(playerColor);
    }

    @Override
    public void updateFieldBuildings(String fieldName, int buildingCount) {
        GUI_Street field = (GUI_Street)getGUIFieldByName(fieldName);
        field.setHotel(false);
        if(buildingCount == 5){
            field.setHotel(true);
        }
        else
            field.setHouses(buildingCount);
    }

    @Override
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

    @Override
    public void vanishPlayer(String name, int positon) {
        GUI_Player player = getGui_playerByName(name);
        gui_board[positon].setCar(player, false);
    }
}
