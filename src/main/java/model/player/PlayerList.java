package model.player;

public class PlayerList {

    private int playerAmount;
    private int NextPlayerIndex;
    Player[] playerList;
    //constructor

    public PlayerList(int amount){
        this.playerAmount = amount;
        playerList= new Player[playerAmount];
    }


    //getters

    public Player getPlayer(int index){ return playerList[index]; }

    public Player[] getAllPlayers(){ return playerList; }

    public Player getCurrentPlayer(){ return playerList[0]; }

    //setters

    public void addPlayer(int index, Player player){
        this.playerList[index] = player;
    }

    public void setNextPlayer(){

        if(getCurrentPlayer().getDoubleTurnStatus()==false){
            Player currPlayer = playerList[0];
            for (int i = 0; i <playerList.length-1 ; i++) {
                playerList[i]=playerList[i+1];
            }
            playerList[playerList.length-1]= currPlayer;
        }
        else{
            getCurrentPlayer().setDoubleTurnStatus(false);
        }
    }
}

