package controller;

import model.deck.*;

public class DrawController implements Drawer {
    //Player player;


    /*
    DrawController(Player player){
        this.player = player;
    }*/

    @Override //CARD: 24 - 26
    public void draw(GetOutOfJailCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Set spillerens getOutOfJailCard til true
         */

    }

    @Override //CARD: 2
    public void draw(MonoplyJackpotCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        Hvis player total assets >= 750 kr.
            Giv transaction metode argumentet 2000

         */

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

    }

    @Override //CARD: 13 - 14 -
    public void draw(PayForBuildingsCard card) {
        //viewController.showFieldMessage(playerName);
        /*
        F� fat i spillerens buildings
        int b�de = multiply buildings med faktor fra kort
        anvend transaction metoden med argumentet b�de


         */

    }

    @Override //CARD: 17 - 27
    public void draw(TeleportAndPayDoubleCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 3 - 6 - 8 - 11
    public void draw(TeleportCard card) {

        //viewController.showFieldMessage(playerName);


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
    }

    @Override //CARD: 15
    public void draw(BirthdayCard card) {
        //viewController.showFieldMessage(playerName);

    }

    @Override //CARD: 1 - 7 - 16 - 18 - 19 - 20 - 21 - 22 - 25 - 28 - 29 - 30 - 31 - 32
    public void draw(MoneyCard card) {
        //viewController.showFieldMessage(playerName);
        //

    }
}
