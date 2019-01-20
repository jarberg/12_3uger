package controller;

import model.deck.*;

public interface Drawer {
    void draw (GetOutOfJailCard card);
    void draw (MonopolyJackpotCard card);
    void draw (MoveToFieldCard card);
    void draw (PayForBuildingsCard card);
    void draw (TeleportAndPayDoubleCard card);
    void draw (GoToJail card);
    void draw (BirthdayCard card);
    void draw (MoneyCard card);
    void draw (MoveAmountCard card);
}

