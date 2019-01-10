package controller;

import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

public class ViewControllerStub extends ViewControllerAbs {

    private static final ViewControllerStub singleInstance = new ViewControllerStub();

    private ViewControllerStub() {
        super();
    }

    public static ViewControllerStub getSingleInstance(){
        return singleInstance;
    }

    @Override
    public void showEmptyGUI(){
    }

    @Override
    public void closeGUI(){
    }

    @Override
    public void createBoard(){
    }

    @Override
    public void addPlayer(String name, int balance){ }

    @Override
    public void showPlayerScores(){
    }

    @Override
    public void changePlayerBalance(String playerName, int amount){
    }

    @Override
    public void spawnPlayers(){
    }

    @Override
    public void movePlayer(String playerName, int position, int amount){
    }

    @Override
    public GUI_Player[] getGUI_Players() {
        return null;
    }

}
