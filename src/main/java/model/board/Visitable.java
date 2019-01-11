package model.board;

import controller.Visitor;

public interface Visitable {

    void accept(Visitor visitor);

}
