package controller;

import gui_fields.GUI_Car;
import gui_fields.GUI_Player;
import model.board.Field;
import model.board.PropertyField;

import java.awt.*;

public class ViewControllerStub implements ViewControllerInterface {

    String choice="";

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showGameGUI(Field[] fields) {

    }

    @Override
    public void showEmptyGUI() {

    }

    @Override
    public void closeGUI() {

    }


    @Override
    public void showGUI() {
        System.out.println("GUI show");

    }

    @Override
    public void addPlayer(String name, Color color, int balance) {

    }

    @Override
    public GUI_Car makePlayerCar(Color color) {
           return null;
    }

    @Override
    public void showPlayerScores() {

    }

    @Override
    public void changePlayerBalance(String playerName, int amount) {

    }

    @Override
    public void spawnPlayers() {

    }

    @Override
    public void movePlayer(String playerName, int position, int amount) {

    }

    @Override
    public void teleportPlayer(String playerName, int oldposition, int newposition) {

    }

    @Override
    public GUI_Player[] getGUI_Players() {
        return new GUI_Player[0];
    }

    @Override
    public String getUserLanguage() {
        return "danish";
    }

    @Override
    public int getPlayerAmount(String[] options) {
        return 3;
    }

    @Override
    public String getPlayerName() {
        return "Test";
    }

    @Override
    public int getPlayerAge() {
        return 0;
    }

    @Override
    public void setUpColors() {

    }

    @Override
    public Color getUserColor(String name) {
        if(name.equals("1"))
            return Color.cyan;
        if(name.equals("2"))
            return Color.red;
        if(name.equals("3"))
            return Color.orange;
        if(name.equals("4"))
            return Color.green;
        if(name.equals("5"))
            return Color.blue;
        if(name.equals("6"))
            return Color.pink;
        return null;
    }

    @Override
    public String[] removeColor(String colorChosen, String[] colorChoices) {
        return new String[0];
    }

    @Override
    public void showDice(int dieOneValue, int dieTwoValue) {

    }

    @Override
    public void showFieldMessage(String name, String fieldMessasge) {

    }

    @Override
    public void addBuilding(PropertyField field) {
        //field.addBuilding();
    }

    @Override
    public GUI_Player getGui_playerByName(String name) {
        return null;
    }

    public void setChoice(String choice){
        this.choice = choice;
    }

    @Override
    public String getUserSelection(String message, String... choiceOptions) {

        return this.choice;
    }

    @Override
    public String getUserButtonSelection(String message, String... options) {
        return null;
    }

    @Override
    public void showOwner(String fieldName, String name, Color playerColor) {

    }

    @Override
    public void updateFieldBuildings(String fieldName, int buildingCount) {

    }

    @Override
    public void setGUI_PlayerBalance(String playerName, int amount) {

    }

    @Override
    public void vanishPlayer(String name, int positon) {

    }

    @Override
    public void pawn(String fieldName, String name, Color playerColor, Color playerColor2) {

    }
}
