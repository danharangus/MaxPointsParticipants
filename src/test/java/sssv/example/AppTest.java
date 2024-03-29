package sssv.example;

import domain.Student;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;

import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private StudentValidator studentValidator;

    @Mock
    private StudentXMLRepo studentFileRepository;

    private Service service;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        studentValidator = new StudentValidator();
        service = new Service(studentFileRepository,
                studentValidator,
                null,
                null,
                null,
                null); // Adjust constructor as necessary
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Test adding a student with id 1
     */
    public void testAddStudentWithId1() {
        Student student = new Student("1", "Ghita", 933, "ghita@ghita.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding a student with null id
     * */
    public void testAddStudentWithNullId() {
        Student student = new Student(null, "Ghita", 933, "ghita@ghita.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert(true);
        }
    }

    /**
     * Test adding a student with empty id
     * */
    public void testAddStudentWithEmptyId() {
        Student student = new Student("", "Ghita", 933, "ghita@ghita.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert(true);
        }
    }

    /**
     * Test adding a student with valid name
     * */
    public void testAddStudentWithValidName() {
        Student student = new Student("1", "Mihai", 933, "mihai@mihai.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding a student with null name
     * */
    public void testAddStudentWithNullName() {
        Student student = new Student("1", null, 933, "mihai@mihai.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert (true);
        }
    }

    /**
     * Test adding a student with empty name
     * */
    public void testAddStudentWithEmptyName() {
        Student student = new Student("1", "", 933, "mihai@mihai.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert(true);
        }
    }


    /**
     * Test adding a student with valid email
     * */
    public void testAddStudentWithValidEmail() {
        Student student = new Student("1", "Mihai", 933, "mihai@mihai.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding a student with null name
     * */
    public void testAddStudentWithNullEmail() {
        Student student = new Student("1", "Mihai", 933, null);
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert (true);
        }
    }

    /**
     * Test adding a student with empty name
     * */
    public void testAddStudentWithEmptyEmail() {
        Student student = new Student("1", "Mihai", 933, "");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert(true);
        }
    }

    /**
     * Test adding student with valid group
     */
    public void testAddStudentWithValidGroup() {
        Student student = new Student("1", "Mihai", 1, "mihai@mihai.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding student with negative group
     */
    public void testAddStudentWithNegativeGroup() {
        Student student = new Student("1", "Mihai", -1, "mihai@mihai.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert (true);
        }
    }

    /**
     * Test adding student with group 0
     */
    public void testAddStudentWithGroup0() {
        Student student = new Student("1", "Mihai", 0, "mihai@mihai.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding student with group MAXINT
     */
    public void testAddStudentWithGroupMAXINT() {
        Student student = new Student("1", "Mihai", Integer.MAX_VALUE, "mihai@mihai.com");
        assertEquals(null, service.addStudent(student));
    }

    /**
     * Test adding student with group MAXINT+1
     */
    public void testAddStudentWithGroupMAXINTPlus1() {
        Student student = new Student("1", "Mihai", Integer.MAX_VALUE + 1, "mihai@mihai.com");
        try {
            // Act
            service.addStudent(student);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert (true);
        }
    }

    /**
     * Test adding two students with different ids
     */
    public void testAddTwoStudentsWithDifferentIds() {
        Student student1 = new Student("1", "Mihai", 933, "mihai@mihai.com");
        Student student2 = new Student("2", "Ovidiu", 933, "ovidiu@ovidiu.com");
        assertEquals(null, service.addStudent(student1));
        assertEquals(null, service.addStudent(student2));
    }

    /**
     * Test adding two students with the same id
     */
    public void testAddTwoStudentsWithTheSameId() {
        Student student1 = new Student("1", "Mihai", 933, "mihai@mihai.com");
        Student student2 = new Student("1", "Ovidiu", 933, "ovidiu@ovidiu.com");
        assertEquals(null, service.addStudent(student1));
        when(studentFileRepository.save(student2)).thenReturn(student1);
        try {
            // Act
            service.addStudent(student2);
            // Assert failure if the above method call does not throw the exception
            fail("Expected a ValidationException to be thrown");
        } catch (ValidationException e) {
            // Assert that the thrown exception is what we expect
            assert (true);
        }
    }
}
