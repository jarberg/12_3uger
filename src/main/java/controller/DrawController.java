package controller;

import model.board.*;
import model.deck.*;
import model.player.Player;

public class DrawController implements Drawer {


    private PlayerFieldRelationController playerFieldRelationController;
    private ViewControllerInterface viewController;
    private TradeController tradeController;
    private Player[] otherPlayers;
    private Player player;
    private Board board;
    private Deck deck;

    DrawController(Player player, Player[] otherPlayers, PlayerFieldRelationController playerFieldRelationController, Board board, Deck deck, ViewControllerInterface viewController){
        this.playerFieldRelationController = playerFieldRelationController;
        this.tradeController = TradeController.getSingleInstance();
        this.viewController = viewController;
        this.otherPlayers = otherPlayers;
        this.player = player;
        this.board = board;
        this.deck = deck;
    }

    @Override
    public void draw(GetOutOfJailCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        player.setJailCardStatus(true);

    }

    @Override
    public void draw(MonopolyJackpotCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int jackpot = card.getJackpot();
        int amount = card.getAmount();

        if(playerFieldRelationController.getNetWorth(player) <= amount){
            tradeController.transferAssets(player,jackpot);
        }

    }

    @Override
    public void draw(MoveToFieldCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int position = player.getPosition();
        int destination = card.getDestination();
        player.setPositionWithStartMoney(destination);

        int amount = (destination - position + board.getFields().length) % board.getFields().length;
        viewController.movePlayer(player.getName(),position,amount);

        String newFieldId = String.valueOf(destination);
        Field newField = playerFieldRelationController.getFieldById(newFieldId);

        FieldVisitor fieldVisitor = new FieldVisitor(player,otherPlayers,deck,board, viewController);
        newField.accept(fieldVisitor);

    }


    @Override
    public void draw(PayForBuildingsCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        Field[] fieldsWithHouses = playerFieldRelationController.getFieldsWithHousesByPlayer(player);

        int amountOfHouses = 0;
        int amountOfHotels = 0;

        for (Field field : fieldsWithHouses){
            PropertyField currentField = (PropertyField) field;
            if(currentField.getBuildingCount() == 5){
                amountOfHotels++;
            } else{
                amountOfHouses += currentField.getBuildingCount();
            }

        }

        int multiplierHotel =  card.getHotel();
        int multiplierHouse =  card.getHouse();

        tradeController.transferAssets(player, -(amountOfHotels * multiplierHotel));
        tradeController.transferAssets(player, -(amountOfHouses * multiplierHouse));
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());

    }

    @Override
    public void draw(TeleportAndPayDoubleCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();

        int firFerry = 5;
        int secFerry = 15;
        int thiFerry = 25;
        int fouFerry = 35;

        if (player.getPosition() < 5 || player.getPosition() > 35) {
            player.setPositionWithoutStartMoney(firFerry);
        } else if (player.getPosition() > 5 && player.getPosition() < 15) {
            player.setPositionWithoutStartMoney(secFerry);
        } else if (player.getPosition() > 15 && player.getPosition() < 25) {
            player.setPositionWithoutStartMoney(thiFerry);
        } else if (player.getPosition() > 25 && player.getPosition() < 35) {
            player.setPositionWithoutStartMoney(fouFerry);
        }

        int newPosition = player.getPosition();


        String positionAsString = String.valueOf(newPosition);
        Field disbutedField = playerFieldRelationController.getFieldById(positionAsString);

        viewController.teleportPlayer(player.getName(), oldPosition, newPosition);
        if(playerFieldRelationController.fieldHasOwner(positionAsString)){
            if(!playerFieldRelationController.isPlayerOwner(player, disbutedField)){
                Player fieldOwner = playerFieldRelationController.getOwnerOfField(positionAsString);
                int howManyOfTheSameFieldTypeIsOwnedByOwner = (playerFieldRelationController.getAmountOfTypeOwned(fieldOwner, (Ownable) board.getFields()[player.getPosition()]));

                if (playerFieldRelationController.fieldHasOwner(disbutedField.getID())) {
                    boolean fieldNotPawned = !((Ownable) disbutedField).getPawnedStatus();
                    if (fieldNotPawned){
                        int amount = ((Ownable)disbutedField).getRent(howManyOfTheSameFieldTypeIsOwnedByOwner) * card.getMultiplier();
                        tradeController.transferAssets(player, fieldOwner, amount);
                    } else {
                        tradeController.askIfWantToBuy(player, disbutedField);
                    }
                }
            }
        }

    }


    @Override
    public void draw(GoToJail card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int newPosition = card.getPosition();

        player.setInJail(true);

        player.setPositionWithoutStartMoney(newPosition);
        player.setDoubleTurnStatus(false);
        viewController.teleportPlayer(player.getName(),oldPosition,newPosition);

    }

    @Override
    public void draw(BirthdayCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int amount = card.getAmount();

        for (int i = 0; i < otherPlayers.length; i++) {
            tradeController.transferAssets(otherPlayers[i],player,amount);
        }

    }

    @Override
    public void draw(MoneyCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int amount = card.getAmount();

        tradeController.transferAssets(player,amount);
        viewController.setGUI_PlayerBalance(player.getName(), player.getBalance());

    }

    @Override
    public void draw(MoveAmountCard card){

        String message = card.getDescription();
        viewController.showMessage(message);

        int oldPosition = player.getPosition();
        int amount = card.getAmount();

        int newPosition =  (board.getFields().length + oldPosition + amount) % board.getFields().length;

        player.setPositionWithStartMoney(newPosition);
        if(amount < 3){
            player.setPassedStartStatus(false);
        }
        viewController.teleportPlayer(player.getName(), oldPosition, newPosition);

        String newField = String.valueOf(newPosition);
        Field newFieldId = playerFieldRelationController.getFieldById(newField);

        FieldVisitor fieldVisitor = new FieldVisitor(player,otherPlayers,deck,board, viewController);
        newFieldId.accept(fieldVisitor);

    }

}