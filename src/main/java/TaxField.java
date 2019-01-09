public class TaxField extends Field{



    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}