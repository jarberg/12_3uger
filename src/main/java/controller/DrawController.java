package controller;

import model.board.*;
import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {


    private Player player;
    private Player[] otherPlayers;
    private ViewControllerInterface viewController;
    private TradeController tradeController;
    private Bank bank;
    private Board board;
    private Deck deck;

    DrawController(Player player, Player[] otherPlayers,  Bank bank, Board board, Deck deck){
        this.player = player;
        this.otherPlayers = otherPlayers;
        this.viewController = ViewController.getSingleInstance();
        this.tradeController = TradeController.getSingleInstance();
        this.bank = bank;
        this.board = board;
        this.deck = deck;
    }

    //CARD: 24 - 26
    DrawController(Player player, Player[] otherPlayers,  Bank bank, Board board, Deck deck, ViewControllerInterface viewController){
        this.player = player;
        this.otherPlayers = otherPlayers;
        this.viewController = ViewController.getSingleInstance();
        this.tradeController = TradeController.getSingleInstance();
        this.bank = bank;
        this.board = board;
        this.deck = deck;
        this.viewController = viewController;
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

        int jackpot = card.getJackpot();
        int amount = card.getAmount();

        if (bank.getNetWorth(player) <= amount) {
            tradeController.transferAssets(player,jackpot);
        }
    }

    @Override //CARD: 3 - 4 - 5 - 12
    public void draw(MoveToFieldCard card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition(); //spillers position
        int destination = card.getDestination(); //Det felt nummer man skal rykke frem til
        player.setPosition(destination);

        int amount = (destination - position + board.getFields().length) % board.getFields().length;
        viewController.movePlayer(player.getName(),position,amount);

        String newFieldId = String.valueOf(destination);
        Field newField = bank.getFieldById(newFieldId);

        FieldVisitor fieldVisitor = new FieldVisitor(player,otherPlayers,deck,board, viewController);
        newField.accept(fieldVisitor);

    }
    //felt 8, felt 5

    @Override //CARD: 13 - 14
    public void draw(PayForBuildingsCard card) {
        String message = card.getDescription();
        viewController.showMessage(message); //(message) = parameter

        Field[] fieldsWithHouses = bank.getFieldsWithHousesByPlayer(player);

        int amountOfHouses = 0;
        int amountOfHotels = 0;

        for (Field field : fieldsWithHouses){
            PropertyField currentField = (PropertyField) field;
            if(currentField.getBuildingCount() == 5)
                amountOfHotels++;
            else
                amountOfHouses += currentField.getBuildingCount();
        }

        int multiplierHotel =  card.getHotel();
        int multiplierHouse =  card.getHouse();

        tradeController.transferAssets(player, -(amountOfHotels * multiplierHotel));
        tradeController.transferAssets(player, -(amountOfHouses * multiplierHouse));
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());
    }

    @Override //CARD: 17 - 27
    public void draw(TeleportAndPayDoubleCard card) {
        String message = card.getDescription();
        viewController.showMessage(message);

        int amount = 25*card.getMultiplier();

        int oldPosition = player.getPosition();

        int firFerry = 5;
        int secFerry = 15;
        int thiFerry = 25;
        int fouFerry = 35;

        if (player.getPosition() < 5 || player.getPosition() > 35) {
            player.setPosition(firFerry);
        } else if (player.getPosition() > 5 && player.getPosition() < 15) {
            player.setPosition(secFerry);
        } else if (player.getPosition() > 15 && player.getPosition() < 25) {
            player.setPosition(thiFerry);
        } else if (player.getPosition() > 25 && player.getPosition() < 35) {
            player.setPosition(fouFerry);
        }

        int newPosition = player.getPosition();

        String positionAsString = String.valueOf(newPosition);
        Field disbutedField = bank.getFieldById(positionAsString);

        viewController.teleportPlayer(player.getName(), oldPosition, newPosition);

        if(bank.hasOwner(disbutedField.getID())){
            Player otherPlayer =  bank.getOwner(positionAsString);
            tradeController.transferAssets(player, otherPlayer, amount);
        } else{
            tradeController.askIfWantToBuy(player, disbutedField);
        }
    }

    @Override //CARD: 6 - 8 - 11
    public void draw(GoToJail card) {

        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int newPosition = card.getPosition();

        player.setInJail(true);

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
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());


    }

    @Override //CARD: 9 - 10
    public void draw(MoveAmountCard card) {
        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition(); //spillers position
        int amount = card.getAmount(); //antal ryk der står på kortet

        int newPosition =  (board.getFields().length + oldPosition + amount) % board.getFields().length;

        player.setPosition(newPosition);
        viewController.teleportPlayer(player.getName(), oldPosition, newPosition);

        String newField = String.valueOf(newPosition);
        Field newFieldId = bank.getFieldById(newField);

        FieldVisitor fieldVisitor = new FieldVisitor(player,otherPlayers,deck,board,viewController);
        newFieldId.accept(fieldVisitor);
    }

}