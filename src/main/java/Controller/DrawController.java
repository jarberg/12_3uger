package Controller;

import model.Deck.*;

public class DrawController implements Drawer {
    //Player player;


    /*
    DrawController(Player player){
        this.player = player;
    }*/

    @Override //CARD: 24 - 26
    public void draw(GetOutOfJailCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 2
    public void draw(MonoplyJackpotCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 3 - 4 - 5 - 9 - 10 - 12 - !!17!! - !!27!!
    public void draw(MoveCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: !!17!! - !!27!!
    public void draw(PayForBuildingsCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD:
    public void draw(TeleportAndPayDoubleCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 6 - 8 - 11
    public void draw(TeleportCard card) {

        //viewController.showFieldMessage(playerName);


        /* FLYT TIL FÆNGSEL METODE
        Field[] fields = get fields
        int position = player.getPosition
        field = fields[position]
        while(!(field instanceOf JailField)){
            position = position++ % fields.length();
            field = fields[position]
            viewController.movePlayer 1 felt
            set spillerens position
        }
        set spillerens fængsels boolean til true.


        */
    }

    @Override //CARD: 15
    public void draw(TransactionToAllPlayersCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 1 - 7 - 13 - 14 - 16 - 18 - 19 - 20 - 21 - 22 - 25 - 28 - 29 - 30 - 31 - 32
    public void draw(TransactionWithBankCard card) {
        //viewController.showFieldMessage(playerName);

    }
}
