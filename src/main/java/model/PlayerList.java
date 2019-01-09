package model;

public class PlayerList {

    private int playerAmount =0;
    private int NextPlayerIndex;

    //constructor

    PlayerList(int amount){

        this.playerAmount = amount;
    }

    Player[] playerList= new Player[playerAmount];


    //getters

    public Player getPlayer(int index){ return playerList[index]; }

    public Player[] getAllPlayers(){ return playerList; }

    public Player getCurrentPlayer(){ return playerList[0]; }

    //setters

    public void addPlayer(int index, Player player){
        playerList[index] = player;
    }

    public void setNextPlayer(){

        if(!getCurrentPlayer().getDoubbleTurnStatus()){
            Player currPlayer = playerList[0];
            for (int i = 1; i <playerList.length ; i++) {
                playerList[i]=playerList[i-1];
            }
            playerList[playerList.length-1]= currPlayer;


        }
    }
}

