package controller;


import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {

    private Player player;
    private Player[] otherPlayers;
    private ViewController viewController;
    private TradeController tradeController;



    DrawController(Player player, Player[] otherPlayers){
        this.player = player;
        this.otherPlayers = otherPlayers;

        this.viewController = ViewController.getSingleInstance();

        this.tradeController = TradeController.getSingleInstance();

    }


    @Override //CARD: 24 - 26
    public void draw(GetOutOfJailCard card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        player.setJailCardStatus(true);

    }


    @Override //CARD: 2
    public void draw(MonopolyJackpotCard card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        if (player.getBalance() > 750) {
            player.addToBalance(2000);
        }

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

        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition();
        int amount = card.getAmount();


        player.setPosition(position);
        viewController.movePlayer(player.getName(),position, amount);

    }

    @Override //CARD: 13 - 14 -
    public void draw(PayForBuildingsCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Få fat i spillerens buildings
        int bøde = multiply buildings med faktor fra kort
        anvend transaction metoden med argumentet bøde


         */

        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter

        int house = card.getHouse();
        int hotel = card.getHotel();

        //TODO: metode til at finde ownerable på house og hotel
        //player.addToBalance();


    }

    @Override //CARD: 17 - 27
    public void draw(TeleportAndPayDoubleCard card) {
        //viewController.showFieldMessage(playerName);

        /*

        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int newPosition = card.getPosition();

        player.setPosition(newPosition);
        viewController.teleportPlayer(player.getName(),oldPosition,newPosition);

        //TODO owner funktion

        if (newPosition == field.getOwner) owned
            player.addBalance(amount).getMultiplier
        tradeController.transferAssets(otherPlayer,player,amount);

        } else {
        tradeController.transferAssets(player,field)
        int amount = card.getAmount();

        tradeController.transferAssets(player,amount);
        */

    }

    @Override //CARD: 3 - 6 - 8 - 11
    public void draw(TeleportCard card) {

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


        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int newPosition = card.getPosition();


        player.setPosition(newPosition);
        viewController.teleportPlayer(player.getName(),oldPosition,newPosition);

    }

    @Override //CARD: 15
    public void draw(BirthdayCard card) {

        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter

        int amount = card.getAmount();

        for (int i = 0; i < otherPlayers.length; i++) {

            tradeController.transferAssets(otherPlayers[i],player,amount);

        }

    }

    @Override //CARD: 1 - 7 - 16 - 18 - 19 - 20 - 21 - 22 - 23 - 25 - 28 - 29 - 30 - 31 - 32
    public void draw(MoneyCard card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        int amount = card.getAmount();

        tradeController.transferAssets(player,amount);


    }

}