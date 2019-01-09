public class ChanceField extends Field {



    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
