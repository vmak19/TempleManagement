/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class EmployeeTest {

    public EmployeeTest() {
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
     * Test of first constructor method, of class Employee.
     */
    @Test
    public void testConstructor1() {
        Employee employee = new Employee(false);
        assertEquals(false, employee.getIsAdministrator());
    }

    /**
     * Test of second constructor method, of class Employee.
     */
    @Test
    public void testConstructor2() {
        Employee employee = new Employee(1, "1");
        assertEquals(1, employee.getUserID());
        assertEquals("1", employee.getPassword());
    }

    /**
     * Test of third constructor method, of class Employee.
     */
    @Test
    public void testConstructor3() {
        Employee employee = new Employee(1, "1", "Bob", "Smith", false);
        assertEquals(1, employee.getUserID());
        assertEquals("1", employee.getPassword());
        //assertEquals("Bob", employee.empFirstNameProperty());
        //assertEquals("Smith", employee.empLastNameProperty());
        assertEquals(false, employee.getIsAdministrator());
    }

    /**
     * Test of fourth constructor method, of class Employee.
     */
    @Test
    public void testConstructor4() {
        Employee employee = new Employee("1", "Bob", "Smith", false);
        assertEquals("1", employee.getPassword());
        //assertEquals("Bob", employee.empFirstNameProperty());
        //assertEquals("Smith", employee.empLastNameProperty());
        assertEquals(false, employee.getIsAdministrator());
    }

    /**
     * Test of reading employee data from csv, for class Employee.
     *//*
     @Test
     public void testRoomIDFromCsvRead() {
     DatabaseSetup main = new DatabaseSetup();
     Employee employee = main.getRoomsFromFile().get(0);
     Employee employee1 = new Employee(101, "Single", 1);
     int expectedUserID = employee1.getUserID();
     assertEquals(expectedUserID, employee.getUserID());
     }*/

    /**
     * Test of expected FileNotFoundException from employee room data from csv,
     * for class Employee.
     *//*
     @Test
     public void testExpectedExceptionFromCsvRead() throws FileNotFoundException {
     try {
     Scanner scanner = new Scanner(new File("resources/wrongemployees.csv"));
     fail("FileNotFoundException was expected");
     } catch (FileNotFoundException ex) {
     }
     }*/

    /**
     * Test of getUserID method, of class Employee.
     */
    @Test
    public void testGetUserID() {
        Employee instance = new Employee(1, "password");
        int expResult = 1;
        int result = instance.getUserID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUserID method, of class Employee.
     */
    @Test
    public void testSetUserID() {
        Employee instance = new Employee(1, "password");
        int expResult = 2;
        instance.setUserID(2);
        int result = instance.getUserID();
        assertEquals(expResult, result);
    }

    /**
     * Test of userIDProperty method, of class Employee.
     *//*
     @Test
     public void testUserIDProperty() {
     Employee instance = new Employee(1, "Single");
     int roomID = 1;
     IntegerProperty expResult = new SimpleIntegerProperty(roomID);
     IntegerProperty result = new SimpleIntegerProperty(instance.getUserID());
     assertEquals(expResult, result);
     }*/

    /**
     * Test of getPassword method, of class Employee.
     */
    @Test
    public void testGetPassword() {
        Employee instance = new Employee(1, "password");
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Employee.
     */
    @Test
    public void testSetPassword() {
        Employee instance = new Employee(1, "password");
        instance.setPassword("myNewPassword");
        assertEquals("myNewPassword", instance.getPassword());
    }

    /**
     * Test of passwordProperty method, of class Employee.
     *//*
     @Test
     public void testPasswordProperty() {
     Employee instance = new Employee(1, "password");
     String password = "password";
     StringProperty expResult = new SimpleStringProperty(password);
     StringProperty result = new SimpleStringProperty(instance.getPassword());
     assertEquals(expResult, result);
     }*/

    /**
     * Test of getEmpFirstName method, of class Employee.
     */
    @Test
    public void testGetEmpFirstName() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        String expResult = "Bob";
        String result = instance.getEmpFirstName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmpFirstName method, of class Employee.
     */
    @Test
    public void testSetEmpFirstName() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        instance.setEmpFirstName("Mob");
        assertEquals("Mob", instance.getEmpFirstName());
    }

    /**
     * Test of empFirstNameProperty method, of class Employee.
     *//*
    @Test
    public void testEmpFirstNameProperty() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        String name = "Bob";
        StringProperty expResult = new SimpleStringProperty(name);
        StringProperty result = new SimpleStringProperty(instance.getEmpFirstName());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getEmpLastName method, of class Employee.
     */
    @Test
    public void testGetEmpLastName() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        String expResult = "Smith";
        String result = instance.getEmpLastName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmpLastName method, of class Employee.
     */
    @Test
    public void testSetEmpLastName() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        instance.setEmpLastName("Flint");
        assertEquals("Flint", instance.getEmpLastName());
    }

    /**
     * Test of empLastNameProperty method, of class Employee.
     *//*
    @Test
    public void testEmpLastNameProperty() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        String name = "Flint";
        StringProperty expResult = new SimpleStringProperty(name);
        StringProperty result = new SimpleStringProperty(instance.getEmpFirstName());
        assertEquals(expResult, result);
    }*/

    /**
     * Test of getIsAdministrator method, of class Employee.
     */
    @Test
    public void testGetIsAdministrator() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        Boolean expResult = false;
        Boolean result = instance.getIsAdministrator();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIsAdministrator method, of class Employee.
     */
    @Test
    public void testSetIsAdministrator() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        instance.setIsAdministrator(true);
        assertEquals(true, instance.getIsAdministrator());
    }

    /**
     * Test of isAdministratorProperty method, of class Employee.
     *//*
    @Test
    public void testIsAdministratorProperty() {
        Employee instance = new Employee("password", "Bob", "Smith", false);
        Boolean isAdmin = false;
        BooleanProperty expResult = new SimpleBooleanProperty(isAdmin);
        BooleanProperty result = new SimpleBooleanProperty(instance.getIsAdministrator());
        assertEquals(expResult, result);
    }*/

}
