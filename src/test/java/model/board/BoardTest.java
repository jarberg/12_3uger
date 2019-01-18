package model.board;

import model.text.LanguageStringCollection;
import model.text.LogicStringCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board;

    @Before
    public void setUp(){
        LanguageStringCollection languageStringCollection = LanguageStringCollection.getSingleInstance();
        LogicStringCollection logicStringCollection = LogicStringCollection.getSingleInstance();
        board = new Board(logicStringCollection.getFieldsText(), languageStringCollection.getFieldsText());
    }

    @After
    public void tearDown(){
        board = null;
    }

    @Test
    public void setupBoard() {
        int[] numOfFieldType = new int[9];

        board.setupBoard();

        for (Field field : board.getFields()){
            if (field instanceof StartField) numOfFieldType[0]++;
            else if (field instanceof PropertyField) numOfFieldType[1]++;
            else if (field instanceof BreweryField) numOfFieldType[2]++;
            else if (field instanceof ChanceField) numOfFieldType[3]++;
            else if (field instanceof TaxField) numOfFieldType[4]++;
            else if (field instanceof ParkingField) numOfFieldType[5]++;
            else if (field instanceof JailField) numOfFieldType[6]++;
            else if (field instanceof GoToJailField) numOfFieldType[7]++;
            else if (field instanceof FerryField) numOfFieldType[8]++;
        }

        assertEquals(1, numOfFieldType[0]);
        assertEquals(22, numOfFieldType[1]);
        assertEquals(2, numOfFieldType[2]);
        assertEquals(6, numOfFieldType[3]);
        assertEquals(2, numOfFieldType[4]);
        assertEquals(1, numOfFieldType[5]);
        assertEquals(1, numOfFieldType[6]);
        assertEquals(1, numOfFieldType[7]);
        assertEquals(4, numOfFieldType[8]);
        assertEquals(40, board.getFields().length);
    }
}