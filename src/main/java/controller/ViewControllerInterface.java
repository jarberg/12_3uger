package controller;

import gui_fields.GUI_Car;
import gui_fields.GUI_Player;
import model.board.Field;
import model.board.PropertyField;

import java.awt.*;

public interface ViewControllerInterface {
    void showMessage(String message);

    void showGameGUI(Field[] fields);

    void showEmptyGUI();

    void closeGUI();

    void createBoard();

    void addPlayer(String name, int balance);

    void showGUI();

    void addPlayer(String name, Color color, int balance);

    GUI_Car makePlayerCar(Color color);

    void showPlayerScores();

    void changePlayerBalance(String playerName, int amount);

    void spawnPlayers();

    void movePlayer(String playerName, int position, int amount);

    void teleportPlayer(String playerName, int oldposition, int newposition);

    GUI_Player[] getGUI_Players();

    String getUserLanguage();

    int getPLayerAmount();

    String getPlayerName();

    int getPlayerAge();

    void setUpColors();

    Color getUserColor(String name);

    String[] removeColor(String colorChosen, String[] colorChoices);

    void showDice(int dieOneValue, int dieTwoValue);

    void showFieldMessage(String name, String fieldMessasge);

    void addBuilding(PropertyField field);

    GUI_Player getGui_playerByName(String name);

    String getUserSelection(String message, String... choiceOptions);

    String getUserButtonSelection(String message, String... options);

    void showOwner(String fieldName, String name, Color playerColor);

    void updateFieldBuildings(String fieldName, int buildingCount);

    void setGUI_PlayerBalance(String playerName, int amount);

    void vanishPlayer(String name, int positon);

    void pawn(String fieldName, String name, Color playerColor);
}
