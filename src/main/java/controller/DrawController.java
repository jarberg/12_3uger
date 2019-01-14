package controller;

import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {

    private Player player;
    private Player[] otherPlayers;
    private ViewController viewController;


    DrawController(Player player, Player[] otherPlayers){
        this.player = player;
        this.otherPlayers = otherPlayers;

        this.viewController = ViewController.getSingleInstance();
    }




    @Override //CARD: 24 - 26
    public void draw(GetOutOfJailCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Set spillerens getOutOfJailCard til true

        boolean

         */

    }

    @Override //CARD: 2
    public void draw(MonopolyJackpotCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Hvis player total assets >= 750 kr.
            Giv transaction metode argumentet 2000

            if

         */

    }

    @Override //CARD: 4 - 5 - 9 - 10 - 12
    public void draw(MoveCard card) {
        //viewController.showFieldMessage(playerName);

        /* MOVE TIL ØNSKET FELT METODE
        @param ønsketFieldID

        Field[] fields = get fields                 // få fat i alle fields
        int position = player.getPosition           // få fat i spillerens current position
        String fieldID = fields[position].getID     // få fat i spillerens current position på board
        while(!(fieldID.equals ØnsketFieldID)){     // bliv ved med at rykke spilleren indtil ønsket felt er fundet
            position = position++ % fields.length;
            fieldID = fields[position].getID
            viewContoller movePlayer 1 step
            set spillerens position på board
        }

         */

    }

    @Override //CARD: 13 - 14 -
    public void draw(PayForBuildingsCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Få fat i spillerens buildings
        int bøde = multiply buildings med faktor fra kort
        anvend transaction metoden med argumentet bøde


         */

    }

    @Override //CARD: 17 - 27
    public void draw(TeleportAndPayDoubleCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 3 - 6 - 8 - 11
    public void draw(TeleportCard card) {

        //viewController.showFieldMessage(playerName);


        /* FLYT TIL FÆNGSEL METODE
        Field[] fields = get fields
        int position = player.getPosition
        Field curfield = fields[position]
        while(!(field instanceOf JailField)){
            position = position++ % fields.length();
            curfield = fields[position]
            viewController.movePlayer 1 felt
            set spillerens position på board
        }
        set spillerens fængsels boolean til true.


        */
    }

    @Override //CARD: 15
    public void draw(BirthdayCard card) {
        //viewController.showFieldMessage(playerName);

        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter

        int amount = card.getAmount();

        for (int i = 0; i < otherPlayers.length; i++) {


        }









        this.balance = balance;
        this.amount = amount;
        this.otherPlayer = otherPlayer;
        this.player = player;

        transferAsserts(player,otherPlayer,amount);

        balance = getBalance();

        if (balance >= amount){
            addToBalence(-amount); //player
            addToBalence(amount); //otherplayer

            showTransferMessage(player.getName(),otherPlayer.getName(),amount);

        } else {


        }



    }

    @Override //CARD: 1 - 7 - 16 - 18 - 19 - 20 - 21 - 22 - 23 - 25 - 28 - 29 - 30 - 31 - 32
    public void draw(MoneyCard card) {
        //viewController.showFieldMessage(playerName);
        //

    }

}