package model.Board;

import Controller.Visitor;

public interface Visitable {

    void accept(Visitor visitor);

}
