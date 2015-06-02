/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public class RoomTypeTest {

    public RoomTypeTest() {
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
     * Test of first constructor method, of class RoomType.
     */
    @Test
    public void testConstructor1() {
        RoomType roomType = new RoomType(2);
        double expResult = 2;
        assertEquals(expResult, roomType.getBaseRate(), 0.001);
    }

    /**
     * Test of second constructor method, of class RoomType.
     */
    @Test
    public void testConstructor2() {
        RoomType roomType = new RoomType("description", 2.2);
        double expResult = 2.2;
        assertEquals(expResult, roomType.getBaseRate(), 0.001);
        assertEquals("description", roomType.getDescription());
    }

    /**
     * Test of third constructor method, of class RoomType.
     */
    @Test
    public void testConstructor3() {
        RoomType roomType = new RoomType("description", 2.2, 2);
        double expResult = 2.2;
        assertEquals(expResult, roomType.getBaseRate(), 0.001);
        assertEquals("description", roomType.getDescription());
        assertEquals(2, roomType.getCapacity());
    }

    /**
     * Test of fourth constructor method, of class RoomType.
     */
    @Test
    public void testConstructor4() {
        RoomType roomType = new RoomType("Single", "description", 2.2, 2);
        double expResult = 2.2;
        assertEquals("Single", roomType.getRoomTypeID());
        assertEquals(expResult, roomType.getBaseRate(), 0.001);
        assertEquals("description", roomType.getDescription());
        assertEquals(2, roomType.getCapacity());
    }

    /**
     * Test of getRoomTypeID method, of class RoomType.
     */
    @Test
    public void testGetRoomTypeID() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        String expResult = "Single";
        String result = instance.getRoomTypeID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRoomTypeID method, of class RoomType.
     */
    @Test
    public void testSetRoomTypeID() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        String expResult = "Double";
        instance.setRoomTypeID("Double");
        String result = instance.getRoomTypeID();
        assertEquals(expResult, result);
    }

    /**
     * Test of roomTypeIDProperty method, of class RoomType.
     *//*
     @Test
     public void testRoomTypeIDProperty() {
     RoomType instance = new RoomType("Single", "description", 2.2, 2);
     String roomTypeID = "Single";
     StringProperty expResult = new SimpleStringProperty(roomTypeID);
     StringProperty result = new SimpleStringProperty(instance.getRoomTypeID());
     assertEquals(expResult, result);
     }*/

    /**
     * Test of getDescription method, of class RoomType.
     */
    @Test
    public void testGetDescription() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        String expResult = "description";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class RoomType.
     */
    @Test
    public void testSetDescription() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        String expResult = "description2";
        instance.setDescription("description2");
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of descriptionProperty method, of class RoomType.
     *//*
    @Test
    public void testDescriptionProperty() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        String description = "description";
        StringProperty expResult = new SimpleStringProperty(description);
        StringProperty result = new SimpleStringProperty(instance.getDescription());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getBaseRate method, of class RoomType.
     */
    @Test
    public void testGetBaseRate() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        double expResult = 2.2;
        double result = instance.getBaseRate();
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of setBaseRate method, of class RoomType.
     */
    @Test
    public void testSetBaseRate() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        double expResult = 10.5;
        instance.setBaseRate(10.5);
        double result = instance.getBaseRate();
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of baseRateProperty method, of class RoomType.
     *//*
    @Test
    public void testBaseRateProperty() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        double baseRate = 2.2;
        DoubleProperty expResult = new SimpleDoubleProperty(baseRate);
        DoubleProperty result = new SimpleDoubleProperty(instance.getBaseRate());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getCapacity method, of class RoomType.
     */
    @Test
    public void testGetCapacity() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        int expResult = 2;
        int result = instance.getCapacity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCapacity method, of class RoomType.
     */
    @Test
    public void testSetCapacity() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        int expResult = 3;
        instance.setCapacity(3);
        int result = instance.getCapacity();
        assertEquals(expResult, result);
    }

    /**
     * Test of capacityProperty method, of class RoomType.
     *//*
    @Test
    public void testCapacityProperty() {
        RoomType instance = new RoomType("Single", "description", 2.2, 2);
        int capacity = 2;
        StringProperty expResult = new SimpleStringProperty(capacity);
        StringProperty result = new SimpleStringProperty(instance.getRoomTypeID());
        assertEquals(expResult, result);
    }*/
}
