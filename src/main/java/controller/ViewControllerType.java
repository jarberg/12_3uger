package controller;

import gui_fields.GUI_Player;

public interface ViewControllerType {

    void showEmptyGUI();
    void closeGUI();
    void createBoard();
    void addPlayer(String name, int balance);
    void showPlayerScores();
    void changePlayerBalance(String playerName, int amount);
    void spawnPlayers();
    void movePlayer(String playerName, int position, int amount);
    void teleport(String playerName, int oldposition, int newposition);
    GUI_Player[] getGUI_Players();
}
