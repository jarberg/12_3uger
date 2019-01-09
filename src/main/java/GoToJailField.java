public class GoToJailField extends Field{



    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}