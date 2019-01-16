package controller;

import model.board.*;
import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {

    private Player player;
    private Player[] otherPlayers;
    private ViewController viewController;
    private TradeController tradeController;
    private Bank bank;
    private Board board;
    //private FieldOwnerBank fieldOwnerBank;



    DrawController(Player player, Player[] otherPlayers,  Bank bank){
        this.player = player;
        this.otherPlayers = otherPlayers;

        this.viewController = ViewController.getSingleInstance();

        this.tradeController = TradeController.getSingleInstance();
        this.bank = bank;
        this.board = board;
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
        int ifOver = card.getAmount();

        if (player.getBalance() > 750) {
            player.addToBalance(2000);

            tradeController.transferAssets(player,ifOver);
            viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance());
        }
    }


    @Override //CARD: 4 - 5 - 12

    public void draw(MoveToFieldCard card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition(); //spillers position
        int amount = card.getAmount(); //Det felt nummer man skal rykke frem til

        player.setPosition(amount);
        viewController.teleportPlayer(player.getName(),position,amount);

    }
    //felt 8, felt 5


    @Override //CARD: 13 - 14 -
    public void draw(PayForBuildingsCard card) {
        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter

        Field[] fieldsWithHouses = bank.getFieldsWithHousesByPlayer(player);

        int housesLength = fieldsWithHouses.length;

        int amount = 0;

        int multiplierHotel =  card.getHotel();
        int multiplierHouse =  card.getHouse();


        for (Field fieldsWithHouse : fieldsWithHouses) {

            int buildingcount = ((PropertyField) fieldsWithHouse).getBuildingCount();

            if (buildingcount > 0 && buildingcount < 5) {
                amount += ((PropertyField) fieldsWithHouse).getBuildingPrice() * multiplierHouse;
            } else if (buildingcount == 5) {
                amount += ((PropertyField) fieldsWithHouse).getBuildingPrice() * multiplierHotel;
            }
        }
        player.addToBalance(amount);
        viewController.getGui_playerByName(player.getName()).setBalance(player.getBalance());
    }

    @Override //CARD: 17 - 27
    public void draw(TeleportAndPayDoubleCard card) {
        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int newPosition = card.getPosition();
        int amount = card.getAmount()*2;

        player.setPosition(newPosition);
        viewController.teleportPlayer(player.getName(),oldPosition,newPosition);

        Player otherPlayer =  bank.getPlayerByName(String.valueOf(bank.getOwner(String.valueOf(player.getPosition()))));
        Field disbutedField = bank.getFieldById(String.valueOf(player.getPosition()));


        if(bank.hasOwner(String.valueOf(player.getPosition()))){

            if (bank.isOwner(player, disbutedField)){//owned
            }
            else{
                player.addToBalance(-amount);
                tradeController.transferAssets(otherPlayer,player,amount);
            }
        }
        else{
            tradeController.transferAssets(player,amount);
            tradeController.transferAssets(player, disbutedField);
        }
    }

    @Override //CARD: 3 - 6 - 8 - 11
    public void draw(TeleportCard card) {
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

    @Override //CARD: 9 - 10
    public void draw(MoveAmountCard card) {
        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition(); //spillers position
        int amount = card.getAmount(); //antal ryk der står på kortet

        int x =  (40 + position + amount) % 40;

        viewController.movePlayer(player.getName(),position, x);
    }

}