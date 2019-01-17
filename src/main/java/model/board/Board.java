package model.board;

import java.awt.Color;

public class Board {
    private Field[] fields;
    private int[] fieldLogic;
    private String[] fieldInfo;
    private int[][] logic;
    private String[][] info;

    public Board(int[][] fieldsLogic, String[][] fieldsInfo) {
        this.logic = fieldsLogic;
        this.info = fieldsInfo;
        fields = new Field[fieldsLogic.length];
    }

    public void setupBoard(){
        for (int i = 0; i < fields.length; i++) {
            fieldLogic = logic[i];
            fieldInfo = info[i];
            Color fieldColor     = decideFieldColor(fieldLogic);
            this.fields[i]       = makeField(fieldLogic, fieldColor, fieldInfo);
        }
    }

    public int getBoardSize(){
        return fields.length;
    }

    private Color decideFieldColor(int[] fieldLogic) {
        Color fieldColor = new Color(0,0,0);
        int fieldGroup = fieldLogic[2];
        switch (fieldGroup){
            case 0: // Skat, Helle
                fieldColor = Color.getHSBColor(60, 5, 100);
                break;
            case 1: // R�dovre, Hvidovre
                fieldColor = Color.blue;
                break;
            case 2: // Roskildevej, ValbyLanggade, All�gade
                fieldColor = Color.pink;
                break;
            case 3: //FrederiksbergAll�, B�lowsvej, GamleKongevej
                fieldColor = Color.green.darker();
                break;
            case 4: // Bernstrffsvej, Hellerupvej, Strandvej
                fieldColor = Color.LIGHT_GRAY;
                break;
            case 5: // Trianglen, Isterbrogade, Gr�nningen
                fieldColor = new Color(222, 102, 21);
                break;
            case 6: //Bredgade, Nytorv, �stergade
                fieldColor = Color.white;
                break;
            case 7: // Amagertorv, VimmelSkaft, Nygade
                fieldColor = Color.yellow.darker();
                break;
            case 8: // Frederiksberg, R�dhuspladsen
                fieldColor = Color.magenta;
                break;
            case 9: // Dampskib
                fieldColor = Color.cyan;
                break;
            case 10: // Bryggeri
                fieldColor = Color.orange;
                break;
            case 11: // F�ngsel
                fieldColor = Color.GRAY.darker();
                break;
            case 12: // Chance
                fieldColor = Color.YELLOW;
                break;
            case 13: //Start
                fieldColor = Color.red;
        }
        return fieldColor;
    }

    private Field makeField(int[] fieldLogic , Color color, String[] fieldInfo) {
        String ID       = Integer.toString(fieldLogic[0]);
        String name     = fieldInfo[1];
        String subtitle = fieldInfo[2];
        String message  = fieldInfo[3];
        int fieldType   = fieldLogic[1];
        Field field     = null;
        switch (fieldType){
            case 1:
                field = new StartField(ID, name, subtitle, message, color, fieldLogic[3]);
                break;
            case 2:
                field = new PropertyField(
                        ID,
                        name,
                        subtitle,
                        message,
                        color,
                        Integer.toString(fieldLogic[2]),
                        fieldLogic[3],
                        fieldLogic[4],
                        fieldLogic[5], fieldLogic[6], fieldLogic[7], fieldLogic[8], fieldLogic[9], fieldLogic[10]);
                break;
            case 3:
                field = new ChanceField(ID, name, subtitle, message, color);
                break;
            case 4:
                field = new TaxField(ID, name, subtitle, message, color, fieldLogic[3], fieldLogic[4]);
                break;
            case 5:
                field = new JailField(ID, name, subtitle, message,  color, fieldLogic[3],fieldLogic[4]);
                break;
            case 6:
                field = new GoToJailField(ID, name, subtitle, message, color);
                break;
            case 7:
                field = new ParkingField(ID, name, subtitle, message, color);
                break;
            case 8:
                field = new BreweryField(ID, name, subtitle, message, color, fieldLogic[4], fieldLogic[5], fieldLogic[3]);
                break;
            case 9:
                field = new FerryField(
                        ID,
                        name,
                        subtitle,
                        message,
                        color,
                        Integer.toString(fieldLogic[2]),
                        fieldLogic[3],
                        fieldLogic[4], fieldLogic[5], fieldLogic[6], fieldLogic[7]);
                break;
        }
        return field;
    }

    public Field[] getFields() {
        return fields;
    }
}
