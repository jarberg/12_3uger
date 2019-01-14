package controller;

import model.board.Board;
import model.board.Field;
import model.misc.DieSet;
import model.player.Player;
import model.player.PlayerList;

public class GameLogic {

    private PlayerList playerlist;
    private DieSet dice = new DieSet();
    private Board board;


    public GameLogic(int amount){
        createPlayerList(amount);
    }

    void createPlayerList(int amount){
        playerlist = new PlayerList(amount);
    }

    public boolean hasPlayerWithName(String name){
        for (Player player : getAllPlayers()){
            if (player != null && player.getName().equals(name))
                return true;
        }
        return false;
    }

    public void rollDice(Player player){
        dice.roll();
        player.setDoubleTurnStatus(checkdiceForDoubleRoll());
    }

    public boolean checkdiceForDoubleRoll(){ return dice.getIdenticalRolls(); }

    public void movePlayer(Player player, int position, int amount, int length){
        player.setPosition((position+amount)%length);
    }

    public boolean checkIfAllBroke(){
        boolean foundWinner=false;
        int counter=0;

        for (Player player : getAllPlayers()){
            if (player.getBrokeStatus())
                counter++;
        }

        if(counter >=getAllPlayers().length-1){
            foundWinner=true;
        }
        return foundWinner;
    }

    public int getDieOneValue(){
        return dice.getDieOneValue();
    }

    public int getDieTwoValue(){
        return dice.getDieTwoValue();
    }

    public int getSumOfDice(){
        return dice.getDieOneValue() + dice.getDieTwoValue();
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

    public void addPlayer(int index, Player player) {
        playerlist.addPlayer(index, player);
    }

    public Player getPlayer(int index) {

        return playerlist.getPlayer(index);
    }

    public Board getBoard(){return this.board;}

    public void createBoard(int[][] fieldLogic, String[][] fieldInfo){
        this.board = new Board();
        this.board.setupBoard(fieldLogic, fieldInfo);
    }

    private Player getPlayerByName(String playerName){
        Player player = null;
        for (int i = 0; i <getAllPlayers().length ; i++) {
            if(getPlayer(i).getName().equals(playerName)){
                player =getPlayer(i);
            }
        }
        return player;
    }

    public void Auktion(Player player, Field field){

    }
}
