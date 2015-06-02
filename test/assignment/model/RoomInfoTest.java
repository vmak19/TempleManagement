/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
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
public class RoomInfoTest {
    
    public RoomInfoTest() {
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
     * Test of getRoomID method, of class RoomInfo.
     */
    @Test
    public void testGetRoomID() {
        System.out.println("getRoomID");
        RoomInfo instance = null;
        int expResult = 0;
        int result = instance.getRoomID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of roomIDProperty method, of class RoomInfo.
     */
    @Test
    public void testRoomIDProperty() {
        System.out.println("roomIDProperty");
        RoomInfo instance = null;
        IntegerProperty expResult = null;
        IntegerProperty result = instance.roomIDProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomIDList method, of class RoomInfo.
     */
    @Test
    public void testGetRoomIDList() {
        System.out.println("getRoomIDList");
        RoomInfo instance = null;
        List<Integer> expResult = null;
        List<Integer> result = instance.getRoomIDList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoomTypeID method, of class RoomInfo.
     */
    @Test
    public void testGetRoomTypeID() {
        System.out.println("getRoomTypeID");
        RoomInfo instance = null;
        String expResult = "";
        String result = instance.getRoomTypeID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRoomTypeID method, of class RoomInfo.
     */
    @Test
    public void testSetRoomTypeID() {
        System.out.println("setRoomTypeID");
        String roomTypeID = "";
        RoomInfo instance = null;
        instance.setRoomTypeID(roomTypeID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of roomTypeIDProperty method, of class RoomInfo.
     */
    @Test
    public void testRoomTypeIDProperty() {
        System.out.println("roomTypeIDProperty");
        RoomInfo instance = null;
        StringProperty expResult = null;
        StringProperty result = instance.roomTypeIDProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescription method, of class RoomInfo.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        RoomInfo instance = null;
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of descriptionProperty method, of class RoomInfo.
     */
    @Test
    public void testDescriptionProperty() {
        System.out.println("descriptionProperty");
        RoomInfo instance = null;
        StringProperty expResult = null;
        StringProperty result = instance.descriptionProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseRate method, of class RoomInfo.
     */
    @Test
    public void testGetBaseRate() {
        System.out.println("getBaseRate");
        RoomInfo instance = null;
        double expResult = 0.0;
        double result = instance.getBaseRate();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBaseRate method, of class RoomInfo.
     */
    @Test
    public void testSetBaseRate() {
        System.out.println("setBaseRate");
        double baseRate = 0.0;
        RoomInfo instance = null;
        instance.setBaseRate(baseRate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of baseRateProperty method, of class RoomInfo.
     */
    @Test
    public void testBaseRateProperty() {
        System.out.println("baseRateProperty");
        RoomInfo instance = null;
        DoubleProperty expResult = null;
        DoubleProperty result = instance.baseRateProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCapacity method, of class RoomInfo.
     */
    @Test
    public void testGetCapacity() {
        System.out.println("getCapacity");
        RoomInfo instance = null;
        int expResult = 0;
        int result = instance.getCapacity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCapacity method, of class RoomInfo.
     */
    @Test
    public void testSetCapacity() {
        System.out.println("setCapacity");
        int capacity = 0;
        RoomInfo instance = null;
        instance.setCapacity(capacity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of capacityProperty method, of class RoomInfo.
     */
    @Test
    public void testCapacityProperty() {
        System.out.println("capacityProperty");
        RoomInfo instance = null;
        IntegerProperty expResult = null;
        IntegerProperty result = instance.capacityProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNoOfBeds method, of class RoomInfo.
     */
    @Test
    public void testGetNoOfBeds() {
        System.out.println("getNoOfBeds");
        RoomInfo instance = null;
        int expResult = 0;
        int result = instance.getNoOfBeds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNoOfBeds method, of class RoomInfo.
     */
    @Test
    public void testSetNoOfBeds() {
        System.out.println("setNoOfBeds");
        int noOfBeds = 0;
        RoomInfo instance = null;
        instance.setNoOfBeds(noOfBeds);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of noOfBedsProperty method, of class RoomInfo.
     */
    @Test
    public void testNoOfBedsProperty() {
        System.out.println("noOfBedsProperty");
        RoomInfo instance = null;
        IntegerProperty expResult = null;
        IntegerProperty result = instance.noOfBedsProperty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class RoomInfo.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        RoomInfo instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
