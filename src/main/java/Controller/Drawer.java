package Controller;

import model.Deck.*;

public interface Drawer {

    void draw (GetOutOfJailCard card);
    void draw (MonoplyJackpotCard card);
    void draw (MoveCard card);
    void draw (PayForBuildingsCard card);
    void draw (TeleportAndPayDoubleCard card);
    void draw (TeleportCard card);
    void draw (BirthdayCard card);
    void draw (MoneyCard card);

}
