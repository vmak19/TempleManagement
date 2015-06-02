/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import assignment.database.DatabaseSetup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mak
 */
public class RoomTest {

    public RoomTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of first constructor method, of class Room.
     */
    @Test
    public void testConstructor1() {
        Room room = new Room(101, "Single", 1);
        assertEquals(101, room.getRoomID());
        assertEquals("Single", room.getRoomTypeID());
        assertEquals(1, room.getNoOfBeds());
    }

    /**
     * Test of second constructor method, of class Room.
     */
    @Test
    public void testConstructor2() {
        Room room = new Room(101, "Single");
        assertEquals(101, room.getRoomID());
        assertEquals("Single", room.getRoomTypeID());
    }

    /**
     * Test of third constructor method, of class Room.
     */
    @Test
    public void testConstructor3() {
        Room room = new Room("Single");
        assertEquals("Single", room.getRoomTypeID());
    }

    /**
     * Test of reading room data from csv, for class Room.
     *//*
    @Test
    public void testRoomIDFromCsvRead() {
        DatabaseSetup main = new DatabaseSetup();
        Room room = main.getRoomsFromFile().get(0);
        Room room1 = new Room(101, "Single", 1);
        int expectedRoomID = room1.getRoomID();
        assertEquals(expectedRoomID, room.getRoomID());
    }*/

    /**
     * Test of expected FileNotFoundException from reading room data from csv, for class
     * Room.
     *//*
    @Test
    public void testExpectedExceptionFromCsvRead() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File("resources/wrongrooms.csv"));
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException ex) {
        }
    }*/

    /**
     * Test of getRoomID method, of class Room.
     */
    @Test
    public void testGetRoomID() {
        Room instance = new Room(1, "Single", 1);
        int expResult = 1;
        int result = instance.getRoomID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoomID method, of class Room.
     */
    @Test
    public void testSetRoomID() {
        Room instance = new Room(1, "Single", 1);
        int expResult = 2;
        instance.setRoomID(2);
        int result = instance.getRoomID();
        assertEquals(expResult, result);
    }

    /**
     * Test of roomIDProperty method, of class Room.
     */
    /*
    @Test
    public void testRoomIDProperty() {
        Room instance = new Room(1, "Single", 1);
        int roomID = 1;
        IntegerProperty expResult = new SimpleIntegerProperty(roomID);
        IntegerProperty result = new SimpleIntegerProperty(instance.getRoomID());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getRoomTypeID method, of class Room.
     */
    @Test
    public void testGetRoomTypeID() {
        Room instance = new Room(1, "Single", 1);
        String expResult = "Single";
        String result = instance.getRoomTypeID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoomTypeID method, of class Room.
     */
    @Test
    public void testSetRoomTypeID() {
        Room instance = new Room(1, "Single", 1);
        instance.setRoomTypeID("Double");
        assertEquals("Double", instance.getRoomTypeID());
    }

    /**
     * Test of roomTypeIDProperty method, of class Room.
     */
    /*
    @Test
    public void testRoomTypeIDProperty() {
        Room instance = new Room(1, "Single", 1);
        String roomTypeID = "Single";
        StringProperty expResult = new SimpleStringProperty(roomTypeID);
        StringProperty result = new SimpleStringProperty(instance.getRoomTypeID());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getNoOfBeds method, of class Room.
     */
    @Test
    public void testGetNoOfBeds() {
        Room instance = new Room(1, "Single", 1);
        int expResult = 1;
        int result = instance.getNoOfBeds();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNoOfBeds method, of class Room.
     */
    @Test
    public void testSetNoOfBeds() {
        Room instance = new Room(1, "Single", 1);
        int expResult = 2;
        instance.setNoOfBeds(2);
        int result = instance.getNoOfBeds();
        assertEquals(expResult, result);
    }

    /**
     * Test of noOfBedsProperty method, of class Room.
     */
    /*
    @Test
    public void testNoOfBedsProperty() {
        Room instance = new Room(1, "Single", 1);
        int noOfBeds = 1;
        IntegerProperty expResult = new SimpleIntegerProperty(noOfBeds);
        IntegerProperty result = new SimpleIntegerProperty(instance.getNoOfBeds());
        assertEquals(expResult, result);
    }*/

}
