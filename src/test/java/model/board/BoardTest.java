package model.board;

import model.board.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @After
    public void tearDown() throws Exception {
        board = null;
    }
/*
    @Test
    public void setupBoard() {
        int[] numOfFieldType = new int[8];

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
        }

        assertEquals(1, numOfFieldType[0]);
        assertEquals(26, numOfFieldType[1]); // including the Ferry fields
        assertEquals(2, numOfFieldType[2]);
        assertEquals(6, numOfFieldType[3]);
        assertEquals(2, numOfFieldType[4]);
        assertEquals(1, numOfFieldType[5]);
        assertEquals(1, numOfFieldType[6]);
        assertEquals(1, numOfFieldType[7]);
        assertEquals(40, board.getFields().length);
    }
    */
}