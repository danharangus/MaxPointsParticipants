package sssv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import junit.framework.Assert;
import junit.framework.TestCase;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.time.LocalDate;
import static org.mockito.Mockito.*;

public class IITests extends TestCase {
    @Mock
    private StudentValidator studentValidator;

    @Mock
    private TemaValidator temaValidator;

    @Mock
    private NotaValidator notaValidator;

    @Mock
    private StudentXMLRepo studentXMLRepository;

    @Mock
    private TemaXMLRepo temaXMLRepository;

    @Mock
    private NotaXMLRepo notaXMLRepository;

    private Service service;


    @BeforeEach
    public void setup() throws Exception {
        studentValidator = mock(StudentValidator.class);
        temaValidator = mock(TemaValidator.class);
        notaValidator = mock(NotaValidator.class);
        studentXMLRepository = mock(StudentXMLRepo.class);
        temaXMLRepository = mock(TemaXMLRepo.class);
        notaXMLRepository = mock(NotaXMLRepo.class);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    public void tearDown() {
        studentValidator = null;
        temaValidator = null;
        notaValidator = null;
        studentXMLRepository = null;
        temaXMLRepository = null;
        notaXMLRepository = null;
        service = null;
    }

    @Test
    public void addStudent_Valid_StudentIsAdded() {
        Student testStudent = new Student("1", "Test Student", 933, "test_email@email.ro");
        try {
            when(studentXMLRepository.save(testStudent)).thenReturn(null);
            Student addedStudent = service.addStudent(testStudent);
            Assertions.assertNull(addedStudent);  // Assuming save returns null on success
        } catch (Exception e) {
            Assertions.fail("Exception should not be thrown");
        }
    }

    @Test
    public void addTema_Valid_TemaIsAdded() {
        Tema testTema = new Tema("1", "Test Tema", 1, 10);
        try {
            when(temaXMLRepository.save(testTema)).thenReturn(null);
            Tema addedTema = service.addTema(testTema);
            Assertions.assertNull(addedTema);
        } catch (Exception e) {
            Assertions.fail("Exception should not be thrown");
        }
    }

    @Test
    public void addNota_Valid_NotaIsAdded() {
        Nota testNota = new Nota("1", "1", "1", 10, LocalDate.of(2024, 4, 11));
        try {
            when(studentXMLRepository.findOne("1")).thenReturn(new Student("1", "Test Student", 933, "test"));
            when(temaXMLRepository.findOne("1")).thenReturn(new Tema("1", "Test Tema", 6, 10));
            when(notaXMLRepository.save(testNota)).thenReturn(null);
            double addedNota = service.addNota(testNota, "feedback");
            Assertions.assertEquals(addedNota, testNota.getNota());
        } catch (Exception e) {
            Assertions.fail("Exception should not be thrown");
        }
    }


    @Test
    public void addStudentAndTema_BothAddedCorrectly() {
        Student student = new Student("2", "New Student", 921, "new_email@email.com");
        Tema tema = new Tema("2", "New Tema", 3, 12);

        try {
            // Mocking student addition
            when(studentXMLRepository.save(student)).thenReturn(null);
            Student addedStudent = service.addStudent(student);
            Assertions.assertNull(addedStudent);

            // Mocking tema addition
            when(temaXMLRepository.save(tema)).thenReturn(null);
            Tema addedTema = service.addTema(tema);
            Assertions.assertNull(addedTema);
        } catch (Exception e) {
            Assertions.fail("Exception should not be thrown");
        }
    }

    @Test
    public void complexScenarioIntegrationTest() {
        Student student = new Student("3", "Dan", 933, "dan@yahoo.com");
        Tema tema = new Tema("3", "Detailed Description", 6, 2);
        Nota nota = new Nota("nt2", "3", "3", 9.5, LocalDate.of(2021, 10, 10));

        try {
            // Validate and add student
            doNothing().when(studentValidator).validate(student);
            when(studentXMLRepository.save(student)).thenReturn(null);
            Assertions.assertNull(service.addStudent(student));

            // Validate and add tema
            doNothing().when(temaValidator).validate(tema);
            when(temaXMLRepository.save(tema)).thenReturn(null);
            Assertions.assertNull(service.addTema(tema));

            // Expecting a failure when adding nota
            when(studentXMLRepository.findOne("3")).thenReturn(student);
            when(temaXMLRepository.findOne("3")).thenReturn(tema);
            Assertions.assertThrows(ValidationException.class, () -> service.addNota(nota, "feedback"));
        } catch (ValidationException ve) {
            Assertions.fail("ValidationException should not have been thrown here");
        }
    }

}