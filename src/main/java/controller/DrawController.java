package controller;


import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {

    private Player player;
    private Player[] otherPlayers;
    private ViewController viewController;
    private TradeController tradeController;
    private Bank bank;
    //private FieldOwnerBank fieldOwnerBank;



    DrawController(Player player, Player[] otherPlayers,  Bank bank){
        this.player = player;
        this.otherPlayers = otherPlayers;

        this.viewController = ViewController.getSingleInstance();

        this.tradeController = TradeController.getSingleInstance();
        this.bank = bank;
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

        /* MOVE TIL �NSKET FELT METODE
        @param �nsketFieldID

        Field[] fields = get fields                 // f� fat i alle fields
        int position = player.getPosition           // f� fat i spillerens current position
        String fieldID = fields[position].getID     // f� fat i spillerens current position p� board
        while(!(fieldID.equals �nsketFieldID)){     // bliv ved med at rykke spilleren indtil �nsket felt er fundet
            position = position++ % fields.length;
            fieldID = fields[position].getID
            viewContoller movePlayer 1 step
            set spillerens position p� board
        }

         */

        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition();
        int amount = card.getAmount();


    }

    @Override //CARD: 13 - 14 -
    public void draw(PayForBuildingsCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        F� fat i spillerens buildings
        int b�de = multiply buildings med faktor fra kort
        anvend transaction metoden med argumentet b�de


         */

        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter
/*
        int house = player.getHouse();
        int hotel = player.getHotel();

        fieldOwnerBank.getOwner(getHouse);
        player.addToBalance(amount);
        fieldOwnerBank.getOwner(getHotel);
        player.addToBalance(amount);
*/
        //TODO: metode til at finde ownerable p� house og hotel
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

        fieldOwnerBank.getOwner(newPosition)

        //TODO owner funktion

        if (field.getOwner = true) owned
            player.addBalance(amount).getMultiplier
        tradeController.transferAssets(otherPlayer,player,amount);

        } else if ( field.getOwner = false) {

        int amount = card.getAmount();

        tradeController.transferAssets(player,amount);
        tradeController.transferAssets(player,field);


        }
        */

    }

    @Override //CARD: 3 - 6 - 8 - 11
    public void draw(TeleportCard card) {

        /* FLYT TIL F�NGSEL METODE
        Field[] fields = get fields
        int position = player.getPosition
        Field curfield = fields[position]
        while(!(field instanceOf JailField)){
            position = position++ % fields.length();
            curfield = fields[position]
            viewController.movePlayer 1 felt
            set spillerens position p� board
        }
        set spillerens f�ngsels boolean til true.
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
        viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance());


    }

}