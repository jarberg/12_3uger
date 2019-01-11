package model.board;

import java.awt.*;

public class Board {
    private Field[] fields;


    public Board(){
        fields = new Field[40];

    }

    public void setupBoard(int[][] fieldsLogic, String[][] fieldsInfo){
        for (int i = 0; i < fields.length; i++) {
            int[] fieldLogic = fieldsLogic[i];
            Color fieldColor = decideFieldColor(fieldLogic);
            String[] fieldInfo = fieldsInfo[i];
            this.fields[i] = makeField(fieldLogic, fieldColor, fieldInfo);
        }
    }

    private Color decideFieldColor(int[] fieldLogic) {
        Color fieldColor = new Color(0,0,0);
        int fieldGroup = fieldLogic[2];
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

    private Field makeField(int[] fieldLogic , Color color, String[] fieldInfo) {
        String ID = Integer.toString(fieldLogic[0]);
        String name = fieldInfo[1];
        String subtitle = fieldInfo[2];
        String message = fieldInfo[3];
        int fieldType = fieldLogic[1];
        Field field = null;
        switch (fieldType){
            case 1:
                field = new StartField(ID, name, subtitle, message, color, fieldLogic[3]);
                break;
            case 2:
                field = new PropertyField(ID, name, subtitle, message, color, Integer.toString(fieldLogic[2]), fieldLogic[3], fieldLogic[4], fieldLogic[5], fieldLogic[6], fieldLogic[7], fieldLogic[8], fieldLogic[9], fieldLogic[10]);
                break;
            case 3:
                field = new ChanceField(ID, name, subtitle, message, color);
                break;
            case 4:
                field = new TaxField(ID, name, subtitle, message, color, fieldLogic[3], fieldLogic[4]);
                break;
            case 5:
                field = new JailField(ID, name, subtitle, message,  color, fieldLogic[3],0);
                break;
            case 6:
                field = new GoToJailField(ID, name, subtitle, message, color);
                break;
            case 7:
                field = new ParkingField(ID, name, subtitle, message, color);
                break;
            case 8:
                field = new BreweryField(ID, name, subtitle, message, color, fieldLogic[4], fieldLogic[5]);
                break;
        }
        return field;
    }

    public Field[] getFields() {
        return fields;
    }
}
