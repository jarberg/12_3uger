package model.player;

public class PlayerList {

    private Player[] playerList;

    public PlayerList(int amount){
        playerList = new Player[amount];
    }

    public Player getPlayer(int index){ return playerList[index]; }

    public Player[] getAllPlayers(){ return playerList; }

    public Player getCurrentPlayer(){ return playerList[0]; }

    public Player getPlayerByName(String name){
        Player player = null;

        for (int i = 0; i <playerList.length ; i++) {
            if(name != null && name.equals(getPlayer(i).getName())){
                player = getPlayer(i);
            }
        }
        return player;
    }


    public void addPlayer(int index, Player player){
        this.playerList[index] = player;
    }

    public void setNextPlayer(){

            if (!getCurrentPlayer().getDoubleTurnStatus()) {
                Player currPlayer = playerList[0];
                for (int i = 0; i < playerList.length - 1; i++) {
                    playerList[i] = playerList[i + 1];
                }
                playerList[playerList.length - 1] = currPlayer;
            } else {
                getCurrentPlayer().setDoubleTurnStatus(false);
            }
            if(getCurrentPlayer().getBrokeStatus()){
                setNextPlayer();
        }
    }

    public Player[] getPlayersButPlayer(Player notThisOneToo){
        Player[] playersInGame = getAllPlayers();
        int length = playersInGame.length;
        Player[] otherPlayers = new Player[length - 1];
        int counter = 0;
        for (Player aPlayersInGame : playersInGame) {
            if (aPlayersInGame != notThisOneToo) {
                otherPlayers[counter] = aPlayersInGame;
                counter++;
            }
        }
        return otherPlayers;
    }
}

