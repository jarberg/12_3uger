import java.awt.*;
public class Board {
    Field[] fields;

    public Board(){
        fields = new Field[40];
    }

    public void setupBoard(){
        for (int i = 0; i < fields.length; i++) {
            Color fillColor = new Color(0,0,0);
            switch (fieldsTXT[i][1]){
                case 1: fillColor = Color.blue; break;
                case 2: fillColor = Color.pink; break;
                case 3: fillColor = Color.green.darker(); break;
                case 4: fillColor = Color.gray; break;
                case 5: fillColor = Color.red; break;
                case 6: fillColor = Color.white; break;
                case 7: fillColor = Color.yellow; break;
                case 8: fillColor = Color.magenta; break;
                default: break;
            }
            switch (fieldsTXT[i][0]){
                case 1: this.fields[i] = new StartField(); break;
                case 2: this.fields[i] = new PropertyField(); break;
                case 3: this.fields[i] =  new ChanceField(); break;
                case 4: this.fields[i] = new TaxField(); break;
                case 5: this.fields[i] = new JailField(); break;
                case 6: this.fields[i] = new GoToJailField(); break;
                case 7: this.fields[i] = new ParkingField(); break;
            }


        }
    }


    public Field[] getFields() {
        return fields;
    }
}
