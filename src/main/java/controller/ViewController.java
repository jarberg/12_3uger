package controller;

import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;
import model.board.Board;
import model.board.Field;
import model.text.FileReader;
import model.text.LanguageStringCollection;

import java.awt.*;

public class ViewController {


    private final LanguageStringCollection languageStringCollection;
    private GUI gui;
    private GUI_Field[] gui_board;
    private GUI_Player[] gui_players;
    private Board board;

    private static ViewController singleInstance = null;
    private FileReader fileReader;
    private String languageFilepath;

    public static ViewController getSingleInstance(){
        if(singleInstance == null)
            singleInstance = new ViewController();

        return singleInstance;
    }

    private ViewController() {
        this.gui_board = new GUI_Field[40];
        this.languageStringCollection = LanguageStringCollection.getInstance("");
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
        this.gui = new GUI(gui_board, Color.green);
    }

    public void showEmptyGUI(){
        this.gui = new GUI(new GUI_Field[0]);
    }

    public void closeGUI(){
        this.gui.close();
    }

    public void createBoard(){
        for (int i = 0; i < gui_board.length; i++) {
            gui_board[i] = new GUI_Street();
        }

        showGUI();
    }


    private void showGUI(){
        if(this.gui != null)
            gui.close();

        this.gui = new GUI(gui_board);
    }


    public void addPlayer(String name, int balance){
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
        GUI_Player newPlayer = new GUI_Player(name, balance);

        gui_players[length] = newPlayer;
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

    public String getUserLanguage() {
        // default language is engelsk otherwise chance the string arguement below :))))
        String[] languageChoices = languageStringCollection.getDirectories();
        String userChoice = gui.getUserSelection("Choose a language", languageChoices);
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

    public Color getUserColor(String name) {
        String message = languageStringCollection.getMenu()[4];
        String[] colorChoices = new String[gui_players.length];
        String cyan = languageStringCollection.getMenu()[5];
        String red = languageStringCollection.getMenu()[6];
        String orange = languageStringCollection.getMenu()[7];
        String green = languageStringCollection.getMenu()[8];

        for (int i = 0; i < gui_players.length; i++) {
            String colorMessage = null;
            Color color = Color.BLACK;
        }
        return  Color.black;
    }
}
