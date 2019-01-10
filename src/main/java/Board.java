import model.LogicStringCollection;

import java.awt.*;

public class Board {
    private LogicStringCollection logicCollection;
    private String[][] fieldsLogic;
    private Field[] fields;


    public Board(){
        fields = new Field[40];
        logicCollection = LogicStringCollection.getInstance("logic");

    }

    public void setupBoard(){
        fieldsLogic = logicCollection.getFieldsText();
        int i = 0;
        for (String[] field : fieldsLogic){
            Color fieldColor = decideFieldColor(field);
            Field newField = makeField(field, fieldColor);
            this.fields[i++] = newField;
        }

        /*for (int i = 0; i < fields.length; i++) {
            String[] field = fieldsLogic[i];
            Color fieldColor = decideFieldColor(field);
            this.fields[i] = makeField(field, fieldColor);
        }*/
    }

    private Color decideFieldColor(String[] fieldLogic) {
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

    private Field makeField(String[] fieldLogic , Color color) {
        int fieldType = Integer.parseInt(fieldLogic[1]);
        Field field = null;
        switch (fieldType){
            case 1:
                field = new StartField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldLogic[3]));
                break;
            case 2:
                field = new PropertyField("title", "subtitle", "description", "message", color, fieldLogic[2], Integer.valueOf(fieldLogic[3]), Integer.valueOf(fieldLogic[4]), Integer.valueOf(fieldLogic[5]), Integer.valueOf(fieldLogic[6]), Integer.valueOf(fieldLogic[7]), Integer.valueOf(fieldLogic[8]), Integer.valueOf(fieldLogic[9]));
                break;
            case 3:
                field = new ChanceField("title", "subtitle", "description", "message", color);
                break;
            case 4:
                field = new TaxField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldLogic[3]), Integer.valueOf(fieldLogic[4]));
                break;
            case 5:
                field = new JailField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldLogic[3]),0);
                break;
            case 6:
                field = new GoToJailField("title", "subtitle", "description", "message", color);
                break;
            case 7:
                field = new ParkingField("title", "subtitle", "description", "message", color);
                break;
            case 8:
                field = new BreweryField("title", "subtitle", "description", "message", color, Integer.valueOf(fieldLogic[4]), Integer.valueOf(fieldLogic[5]));
                break;
        }
        return field;
    }

    public Field[] getFields() {
        return fields;
    }
}
