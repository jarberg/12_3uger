import model.LogicStringCollection;

import java.awt.*;

public class Board {
    Field[] fields;
    LogicStringCollection logicCollection;
    String[][] fieldsLogic;

    public Board(){
        fields = new Field[40];
        logicCollection = LogicStringCollection.getInstance("logic");

    }

    public void setupBoard(){
        fieldsLogic = logicCollection.getFieldsText();

        for (int i = 0; i < fields.length; i++) {
            String[] field = fieldsLogic[i];
            Color fieldColor = chooseFieldColor(field);
            makeField(i, fieldColor);
        }
    }

    private Color chooseFieldColor(String[] fieldLogic) {
        Color fieldColor = new Color(0,0,0);
        int fieldGroup = Integer.parseInt(fieldLogic[2]);
        switch (fieldGroup){
            case 1:
                fieldColor = Color.blue;
                break;
            case 2:
                fieldColor = Color.pink;
                break;
            case 3:
                fieldColor = Color.green.darker();
                break;
            case 4:
                fieldColor = Color.gray;
                break;
            case 5:
                fieldColor = Color.red;
                break;
            case 6:
                fieldColor = Color.white;
                break;
            case 7:
                fieldColor = Color.yellow;
                break;
            case 8:
                fieldColor = Color.magenta;
                break;
            default:
                break;
        }
        return fieldColor;
    }

    private void makeField(int i, Color color) {
        int fieldType = Integer.parseInt(fieldsLogic[i][1]);
        switch (fieldType){
            case 1:
                this.fields[i] = new StartField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldsLogic[i][3]));
                break;
            case 2:
                this.fields[i] = new PropertyField("title", "subtitle", "description", "message", color, fieldsLogic[i][2], Integer.valueOf(fieldsLogic[i][3]), Integer.valueOf(fieldsLogic[i][4]), Integer.valueOf(fieldsLogic[i][5]), Integer.valueOf(fieldsLogic[i][6]), Integer.valueOf(fieldsLogic[i][7]), Integer.valueOf(fieldsLogic[i][8]), Integer.valueOf(fieldsLogic[i][9]));
                break;
            case 3:
                this.fields[i] = new ChanceField("title", "subtitle", "description", "message", color);
                break;
            case 4:
                this.fields[i] = new TaxField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldsLogic[i][3]), Integer.valueOf(fieldsLogic[i][4]));
                break;
            case 5:
                this.fields[i] = new JailField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldsLogic[i][3]),0);
                break;
            case 6:
                this.fields[i] = new GoToJailField("title", "subtitle", "description", "message", color);
                break;
            case 7:
                this.fields[i] = new ParkingField("title", "subtitle", "description", "message", color);
                break;
            case 8:
                this.fields[i] = new BreweryField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldsLogic[i][4]), Integer.valueOf(fieldsLogic[i][5]));
                break;
        }
    }

    public Field[] getFields() {
        return fields;
    }
}
