package controller;

import gui_fields.GUI_Player;

public class ViewControllerStub implements ViewControllerType {

    private static ViewControllerStub singleInstance = null;

    public static ViewControllerStub getSingleInstance(){
        if(singleInstance == null)
            singleInstance = new ViewControllerStub();

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
