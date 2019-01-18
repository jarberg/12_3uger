package controller;

import model.board.*;

public interface Visitor {

    void visit(ChanceField field);
    void visit(GoToJailField field);
    void visit(JailField field);
    void visit(ParkingField field);
    void visit(Ownable field);
    void visit(TaxField field);
    void visit(StartField field);

}
