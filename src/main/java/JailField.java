import java.awt.*;

public class JailField extends Field {

    private int releaseCharge;
    private int maxSuccessiveAttempts;


    public JailField(String title, String subtitle, String description, String message, Color fillColor, int releaseCharge, int maxSuccessiveAttempts){
        super(title, subtitle, description, message, fillColor);

        this.releaseCharge = releaseCharge;
        this.maxSuccessiveAttempts = maxSuccessiveAttempts;
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getReleaseCharge(){
        return releaseCharge;
    }

    public int getMaxSuccesiveAttempts(){
        return maxSuccessiveAttempts;
    }

}