package controller;

import model.misc.DieSet;
import model.player.Player;
import model.player.PlayerList;

public class GameLogic {

    private PlayerList playerlist;
    private DieSet dice = new DieSet();

    public GameLogic(int amount){
        createPlayerList(amount);
    }

    public int rollDice(Player player){
        dice.roll();
        player.setDoubleTurnStatus(checkdiceForDoubleRoll());
        return dice.getValue();
    }

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    public void movePlayer(Player player, int position, int amount, int length){
        player.setPosition((position+amount)%length);
    }

    public int getValue(){
        return dice.getValue();
    }

    public Player[] getAllPlayers(){
        return playerlist.getAllPlayers();
    }

    public Player getCurrentPlayer(){
        return playerlist.getCurrentPlayer();
    }
    public void setNextPlayer(){
        playerlist.setNextPlayer();
    }

    void createPlayerList(int amount){
        playerlist = new PlayerList(amount);

    }

    public void addPlayer(int index, Player player) {
        playerlist.addPlayer(index, player);
    }

    public Player getPlayer(int index) {

        return playerlist.getPlayer(index);
    }
}
