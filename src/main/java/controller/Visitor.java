package controller;

import model.board.*;

public interface Visitor {

    void landOnField(ChanceField field);
    void landOnField(GoToJailField field);
    void landOnField(JailField field);
    void landOnField(ParkingField field);
    void landOnField(Ownable field);
    void landOnField(TaxField field);
    void landOnField(StartField field);

}
